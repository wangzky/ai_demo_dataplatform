package com.ai.dataplatform.service;

import com.ai.dataplatform.dao.ModularAttrRepository;
import com.ai.dataplatform.dao.ModularItemRepository;
import com.ai.dataplatform.dto.FuzzyQryParam;
import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.dto.PageBean;
import com.ai.dataplatform.dto.PageResult;
import com.ai.dataplatform.entity.ModularItem;
import com.ai.dataplatform.util.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: FuzzyQryService
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.service
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 10:50
 */
@Service
@Slf4j
public class FuzzyQryService {

    @Autowired
    JdbcUtil jdbcUtil;


    public MyResp fuzzyQry(FuzzyQryParam fuzzyQryParam){
        log.info("模糊查询列表：【{}】" , fuzzyQryParam.toString());

        String modularName = fuzzyQryParam.getModularName();
        String modularAttrName = fuzzyQryParam.getModularAttrName();

        String sql = "SELECT * FROM modular_item WHERE 1=1 ";
        String countSql = "SELECT count(*) FROM modular_item WHERE 1=1 ";

        if (! StringUtils.isEmpty(modularName)){
            sql += "AND modular_name LIKE CONCAT('%', '"+ modularName +"','%')";
        }

        if (! StringUtils.isEmpty(modularAttrName)){
            sql += "AND modular_id IN " +
                    "(SELECT modular_id FROM modular_attr WHERE 1=1 AND " +
                    "( modular_attr_name  LIKE CONCAT('%','"+modularAttrName+"','%') OR  " +
                    "modular_attr_notes  LIKE CONCAT('%','"+modularAttrName+"','%')))";
        }


        PageBean pageDto = jdbcUtil.getCustomerPageDto(
                sql,
                new Object[]{},
                countSql,
                new Object[]{},
                fuzzyQryParam.getPage(),
                fuzzyQryParam.getSize());


        return new MyResp().SuccessResp("查询成功" ,new PageResult(Long.valueOf(pageDto.getTotalSize()) , pageDto.getContent()));
    }
}
