package com.ai.dataplatform.service;

import com.ai.dataplatform.dao.ModularAttrRepository;
import com.ai.dataplatform.dao.ModularItemRepository;
import com.ai.dataplatform.dto.DataItemListQry;
import com.ai.dataplatform.dto.ModularDto;
import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.dto.PageResult;
import com.ai.dataplatform.entity.ModularAttr;
import com.ai.dataplatform.entity.ModularItem;
import com.ai.dataplatform.util.RandomUtil;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModularService
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.service
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 10:09
 */
@Service
@Slf4j
public class ModularService {
    @Autowired
    ModularItemRepository modularItemRepository;

    @Autowired
    ModularAttrRepository modularAttrRepository;


    public MyResp modularListQry(DataItemListQry qry){
        log.info("查询列表：【{}】" , qry.toString());
        Page<ModularItem> page = modularItemRepository.findByKeyWord(qry.getKeyWord(), new PageRequest(qry.getPage() - 1 < 0 ? 0 : qry.getPage() - 1, qry.getSize()));
        List<ModularItem> list = page.getContent();
        Long totalPages = page.getTotalElements();
        return new MyResp().SuccessResp("查询成功", new PageResult(totalPages, list));
    }

    public MyResp modularItemQry(Long modularId){
        log.info("查询详情：【{}】" , modularId);
        List list =  modularAttrRepository.findAllByModularIdOrderBySort(modularId);
        if (list.size() < 1) {
            return new MyResp().FailResp("该模块无属性");
        }
        return new MyResp().SuccessResp("查询成功" , list);
    }

    @Transactional(rollbackFor = Exception.class)
    public MyResp modularItemAdd(List<ModularDto> modularDtos){

        int successNum = 0;
        int failNum = 0 ;
        String failModularNames = "";
        for (ModularDto modularDto : modularDtos){

            long id = RandomUtil.getId();
            ModularItem modularItem = modularDto.getModularItem();
            String modularName = modularItem.getModularName();
            ModularItem byModularName = modularItemRepository.findByModularName(modularName);
            if (byModularName != null) {
                failNum ++;
                failModularNames += modularName + ",";
                log.info("模块名称重复 【{}】" , modularName);
                continue;
            }
            modularItem.setModularId(id);

            modularItemRepository.save(modularItem);

            List<ModularAttr> modularAttrs = modularDto.getModularAttrs();
            for (ModularAttr modularAttr : modularAttrs){
                modularAttr.setModularId(id);
            }
            modularAttrRepository.saveAll(modularAttrs);

            successNum ++;
        }
        String msg = "新增成功【"+successNum+"】条 ，失败【"+failNum+"】条";
        if (failNum> 0){
            msg += "  失败条目："+failModularNames;
        }
        return new MyResp().SuccessResp(msg);
    }


    @Transactional(rollbackFor = Exception.class)
    public MyResp modularItemUpdate(List<ModularDto> modularDtos){

        int successNum = 0;
        int failNum = 0 ;
        String failModularIds = "";
        for (ModularDto modularDto : modularDtos){

            ModularItem modularItem = modularDto.getModularItem();
            Long modularId = modularItem.getModularId();
            Optional<ModularItem> byId = modularItemRepository.findById(modularId);
            if (byId.equals(Optional.empty())) {
                failNum ++;
                failModularIds += modularItem.getModularId() + ",";
                log.info("模块id不存在 【{}】" , modularItem.getModularId());
                continue;
            }
            modularItemRepository.save(modularItem);

            List<ModularAttr> modularAttrs = modularDto.getModularAttrs();
            for (ModularAttr modularAttr: modularAttrs){
                modularAttr.setModularId(modularId);
            }
            modularAttrRepository.saveAll(modularAttrs);

            successNum ++;
        }
        String msg = "修改成功【"+successNum+"】条 ，失败【"+failNum+"】条";
        if (failNum> 0){
            msg += "  失败条目："+failModularIds;
        }
        return new MyResp().SuccessResp(msg);
    }


    @Transactional
    public MyResp modularItemDel(Long modularId){
        log.info("删除【{}】",modularId);

        Optional<ModularItem> byId = modularItemRepository.findById(modularId);
        if (byId.equals(Optional.empty())){
            return new MyResp().FailResp("模块不存在");
        }

        modularItemRepository.deleteById(modularId);
        modularAttrRepository.deleteByModularId(modularId);

        return new MyResp().SuccessResp("删除成功:"+modularId);
    }

}
