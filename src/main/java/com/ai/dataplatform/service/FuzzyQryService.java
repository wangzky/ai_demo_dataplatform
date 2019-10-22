package com.ai.dataplatform.service;

import com.ai.dataplatform.dto.*;
import com.ai.dataplatform.util.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public MyResp fuzzyQry(FuzzyQryParam fuzzyQryParam) {
        log.info("模糊查询:{}【{}】", fuzzyQryParam.getFuzzyQryType(), fuzzyQryParam.toString());

        PageBean pageBean;
        if (fuzzyQryParam.getFuzzyQryType().equals(FuzzyQryTypeEnum.MODULAR_KEY_WORD)) {
            pageBean = modularFuzzyQry(fuzzyQryParam);
        } else {
            pageBean = dataValueFuzzyQry(fuzzyQryParam);
        }
        return new MyResp().SuccessResp("查询成功", new PageResult(Long.valueOf(pageBean.getTotalSize()), pageBean.getContent()));
    }

    private PageBean dataValueFuzzyQry(FuzzyQryParam fuzzyQryParam) {

        String keyWord = fuzzyQryParam.getKeyWord();
        String isFile = fuzzyQryParam.getIsFile();

        String sql = "SELECT \n" +
                "a.data_item_id,\n" +
                "a.data_val,\n" +
                "b.modular_id,\n" +
                "b.modular_name,\n" +
                "c.modular_attr_id,\n" +
                "c.modular_attr_name,\n" +
                "c.modular_attr_notes,\n" +
                "c.modular_attr_type\n" +
                "FROM data_item a \n" +
                "INNER JOIN modular_item b ON a.modular_id = b.modular_id \n" +
                "LEFT JOIN modular_attr c ON a.modular_attr_id = c.modular_attr_id\n" +
                "WHERE 1=1 \n" +
                "AND ( \n" +
                "a.data_val LIKE CONCAT('%','" + keyWord + "','%')\n" +
                ")";

        String countSql =
                "SELECT \n" +
                        "COUNT(*)\n" +
                        "FROM data_item a \n" +
                        "INNER JOIN modular_item b ON a.modular_id = b.modular_id \n" +
                        "LEFT JOIN modular_attr c ON a.modular_attr_id = c.modular_attr_id\n" +
                        "WHERE 1=1 \n" +
                        "AND ( \n" +
                        "a.data_val LIKE CONCAT('%','" + keyWord + "','%')\n" +
                        ")";
        if (isFile.equals("1")) {
            sql += "AND c.modular_attr_type != 'file' ";
            countSql += "AND c.modular_attr_type != 'file' ";
        }

        log.info("查询sql 【{}】", sql);


        PageBean pageDto = jdbcUtil.getCustomerPageDto(
                sql,
                new Object[]{},
                countSql,
                new Object[]{},
                fuzzyQryParam.getPage(),
                fuzzyQryParam.getSize());

        return pageDto;
    }

    private PageBean modularFuzzyQry(FuzzyQryParam fuzzyQryParam) {
        String keyWord = fuzzyQryParam.getKeyWord();

        String sql = "SELECT * FROM modular_item WHERE 1=1 ";
        String countSql = "SELECT count(*) FROM modular_item WHERE 1=1 ";

        if (!StringUtils.isEmpty(keyWord)) {
            String str = "AND ( modular_name LIKE CONCAT('%', '" + keyWord + "','%') "  +
                    "OR modular_id IN " +
                    "(SELECT modular_id FROM modular_attr WHERE 1=1 AND " +
                    "( modular_attr_name  LIKE CONCAT('%','" + keyWord + "','%') OR  " +
                    "modular_attr_notes  LIKE CONCAT('%','" + keyWord + "','%'))) )";
            sql += str;
            countSql += str;
        }
        log.info("查询sql 【{}】", sql);

        PageBean pageDto = jdbcUtil.getCustomerPageDto(
                sql,
                new Object[]{},
                countSql,
                new Object[]{},
                fuzzyQryParam.getPage(),
                fuzzyQryParam.getSize());

        return pageDto;
    }
}
