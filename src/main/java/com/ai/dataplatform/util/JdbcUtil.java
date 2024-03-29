package com.ai.dataplatform.util;

import com.ai.dataplatform.dto.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: JdbcUtil
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.util
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-11 15:31
 */
@Component
public class JdbcUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 返回单个dto
     * @param sql 查询sql
     * @param queryArgs 查询参数
     * @param rowMapper dto mapper
     * @param <T> dto
     * @return dto
     */
    public <T>T getCustomerDto(String sql, Object[] queryArgs, RowMapper<T> rowMapper) {
        return jdbcTemplate.queryForObject(sql, queryArgs, rowMapper);
    }

    /**
     * 返回dto列表
     * @param sql 查询sql
     * @param queryArgs 查询参数
     * @param rowMapper dto mapper
     * @param <T> dto
     * @return dto list
     * */
    public <T> List<T> getCustomerDtoList(String sql, Object[] queryArgs, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, queryArgs, rowMapper);
    }

    /**
     * 返回分页对象
     * @param sql 查询sql
     * @param queryArgs 查询参数
     * @param rowMapper dto mapper
     * @param countSql 总量sql
     * @param countArgs 总量参数
     * @param page 当前页
     * @param size 每页大小
     * @param <T> dto
     * @return 分页对象
     */
    public <T> PageBean<T> getCustomerPageDto(String sql, Object[] queryArgs,
                                              String countSql, Object[] countArgs, int page, int size) {

        if (page <= 0){
            throw new RuntimeException("当前页数必须大于1");
        }
        if (size <= 0){
            throw new RuntimeException("每页大小必须大于1");
        }
        //总共数量
        int totalSize = jdbcTemplate.queryForObject(countSql,countArgs,Integer.class);
        if (totalSize == 0){
            return PageBean.<T>builder()
                    .content(new ArrayList<>())
                    .elementTotalSize(0)
                    .page(0)
                    .size(0)
                    .totalPage(0)
                    .totalSize(0)
                    .build();
        }
        //总页数
        int totalPage = totalSize%size == 0 ? totalSize/size : totalSize/size + 1;
        //开始位置
        int offset = (page -1)*size;
        //return item size
        int limit =  size;
        sql = sql +" limit "+ limit +" offset "+offset;
        List content = jdbcTemplate.queryForList(sql,queryArgs);
        return PageBean.<T>builder()
                .content(content)
                .elementTotalSize(content.size())
                .totalSize(totalSize)
                .totalPage(totalPage)
                .page(page)
                .size(size)
                .build();
    }
}
