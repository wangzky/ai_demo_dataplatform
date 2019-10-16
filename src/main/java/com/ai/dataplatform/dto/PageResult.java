package com.ai.dataplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * Created with LongelliJ IDEA.
 * Description:
 *
 * @Title: PageResult
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-09 10:56
 */
@Data
public class PageResult {
    private Long total;
    private List<Object> dataList;

    public PageResult(){

    }
    public PageResult(Long total ,List dataList){
        this.total = total;
        this.dataList = dataList;
    }

}
