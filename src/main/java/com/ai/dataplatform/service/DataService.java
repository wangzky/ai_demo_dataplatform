package com.ai.dataplatform.service;

import com.ai.dataplatform.dao.DataItemRepository;
import com.ai.dataplatform.dao.ModularAttrRepository;
import com.ai.dataplatform.dto.*;
import com.ai.dataplatform.entity.DataItem;
import com.ai.dataplatform.entity.ModularAttr;
import com.ai.dataplatform.util.JdbcUtil;
import com.ai.dataplatform.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataService
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.service
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-11 09:55
 */
@Slf4j
@Service
public class DataService {

    @Autowired
    DataItemRepository dataItemRepository;

    @Autowired
    ModularAttrRepository modularAttrRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcUtil jdbcUtil;


    public MyResp hasDataListQry(DataItemListQry qry) {
        log.info("查询列表：【{}】", qry.toString());
        Page<DataItem> page = dataItemRepository.hasDataListQry(qry.getKeyWord(), new PageRequest(qry.getPage() - 1 < 0 ? 0 : qry.getPage() - 1, qry.getSize()));
        List<DataItem> list = page.getContent();
        Long totalPages = page.getTotalElements();
        return new MyResp().SuccessResp("查询成功", new PageResult(totalPages, list));
    }

    public MyResp dataListQryByModularId(DataListQry qry) {
        log.info("查询列表：【{}】", qry.toString());
        Long modularId = qry.getModularId();
        String keyWord = qry.getKeyWord();
        Long dataItemId = qry.getDataItemId();
        // 查询表头
        List<String> headerName = new ArrayList();
        List<Long> attrId = new ArrayList();
        List<Map> header = new ArrayList<>();
        List<ModularAttr> modularAttrList = modularAttrRepository.findAllByModularIdOrderBySort(modularId);
        for (ModularAttr modularAttr : modularAttrList){
            headerName.add(modularAttr.getModularAttrName());
            attrId.add(modularAttr.getModularAttrId());

            Map m = new HashMap();
            m.put("title",modularAttr.getModularAttrNotes());
            m.put("dataIndex",modularAttr.getModularAttrName());
//            m.put(modularAttr.getModularAttrName() , modularAttr.getModularAttrNotes());
            header.add(m);
        }

        // 组织sql 纵转横
        String sql = "select ";
        for (int i = 0 ; i < attrId.size() ; i ++){
            sql += "GROUP_CONCAT(case when modular_attr_id = "+ attrId.get(i) +" then data_val end ) as " + headerName.get(i) + ",";
        }
//        sql = sql.substring(0 , sql.length()-1);
        sql += " data_item_id as dataItemId";
        sql += " from data_item where 1=1";
        sql += " and modular_id = ?";
        sql += " and data_val like CONCAT('%', ? ,'%')";
        if (dataItemId != null && dataItemId != 0){
            sql += " and data_item_id = " + dataItemId;
        }
        sql += " GROUP BY data_item_id ";

        String countSql = "select COUNT(DISTINCT data_item_id ) from data_item where 1=1 and modular_id = ? and data_val like CONCAT('%', ? ,'%') ";

        if (dataItemId != 0){
            countSql += " and data_item_id = " + dataItemId;
        }

        log.info("===数据列表查询=== \n sql 【{}】 \n countsql【{}】" , sql , countSql);

        PageBean pageDto = jdbcUtil.getCustomerPageDto(
                sql,
                new Object[]{modularId , keyWord},
                countSql,
                new Object[]{modularId , keyWord},
                qry.getPage(),
                qry.getSize());

        Map result = new HashMap();
        result.put("pageResult",new PageResult(Long.valueOf(pageDto.getTotalSize()), pageDto.getContent()));
        result.put("headerResult",header);

        return new MyResp().SuccessResp("查询成功", result);
    }

    public MyResp dataInfoQryByDataItemId(Long dataItemId) {
        log.info("查询详情：【{}】", dataItemId);
        List<Long> list = dataItemRepository.qryModularIdByDataItemId(dataItemId);
        if (list.size()< 1){
            return new MyResp().FailResp("数据ID有误");
        }
        Long modularId = list.get(0);
        DataListQry dataListQry = new DataListQry();
        dataListQry.setDataItemId(dataItemId);
        dataListQry.setModularId(modularId);
        dataListQry.setKeyWord("");

        MyResp myResp = this.dataListQryByModularId(dataListQry);

        return myResp;

    }

    @Transactional
    public MyResp dataItemAdd(List<DataItemDto> dataItemDtos) {


        int successNum = 0;
        int failNum = 0;

        List<DataItem> dataItemList = new ArrayList<>();

        outsideLoop:
        for (DataItemDto dataItemDto : dataItemDtos) {
            Long modularId = dataItemDto.getModularId();
            List<DataUnit> dataItems = dataItemDto.getDataItems();

            if (modularId == 0){
                continue outsideLoop;
            }

            Long dataItemId = RandomUtil.getId();

            insideLoop:
            for (DataUnit dataUnit : dataItems) {
                Long modularAttrId = dataUnit.getModularAttrId();
                String dataVal = dataUnit.getDataVal();

                if (modularAttrId == 0) {
                    failNum++;
                    continue insideLoop;
                }

                DataItem dataItem = new DataItem();
                dataItem.setModularAttrId(modularAttrId);
                dataItem.setDataVal(dataVal);
                dataItem.setDataItemId(dataItemId);
                dataItem.setModularId(modularId);

                dataItemList.add(dataItem);

                successNum ++;
            }
        }
        dataItemRepository.saveAll(dataItemList);

        String msg = "新增成功【" + successNum + "】条";
        return new MyResp().SuccessResp(msg);
    }

    @Transactional
    public MyResp dataItemUpdate(List<DataItemDto> dataItemDtos) {


        int successNum = 0;
        int failNum = 0;

        List<DataItem> dataItemList = new ArrayList<>();

        outsideLoop:
        for (DataItemDto dataItemDto : dataItemDtos) {
            Long modularId = dataItemDto.getModularId();
            List<DataUnit> dataItems = dataItemDto.getDataItems();

            if (modularId == 0){
                continue outsideLoop;
            }

            Long dataItemId = dataItemDto.getDataItemId();

            List<DataItem> byDataItemId = dataItemRepository.findByDataItemId(dataItemId);
            if (byDataItemId.size() <1){
                log.error("DataItemId 不存在1");
                continue outsideLoop;
            }

            dataItemRepository.deleteByDataItemId(dataItemId);


            insideLoop:
            for (DataUnit dataUnit : dataItems) {
                Long modularAttrId = dataUnit.getModularAttrId();
                String dataVal = dataUnit.getDataVal();

                if (modularAttrId == 0) {
                    failNum++;
                    continue insideLoop;
                }

                DataItem dataItem = new DataItem();
                dataItem.setModularAttrId(modularAttrId);
                dataItem.setDataVal(dataVal);
                dataItem.setDataItemId(dataItemId);
                dataItem.setModularId(modularId);

                dataItemList.add(dataItem);

                successNum ++;
            }
        }
        dataItemRepository.saveAll(dataItemList);

        String msg = "新增成功【" + successNum + "】条";
        return new MyResp().SuccessResp(msg);
    }

    @Transactional
    public MyResp dataItemDel(Long dataItemId) {
        log.info("删除dataItemId【{}】", dataItemId);
        dataItemRepository.deleteByDataItemId(dataItemId);

        return new MyResp().SuccessResp("删除成功");
    }


}
