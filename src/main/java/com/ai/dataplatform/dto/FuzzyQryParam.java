package com.ai.dataplatform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: FuzzyQryParam
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 10:45
 */
@Data
public class FuzzyQryParam {
    @ApiModelProperty(value = "查询类型")
    private FuzzyQryTypeEnum fuzzyQryType;
    @ApiModelProperty(value = "值")
    private String keyWord;
    @ApiModelProperty(value = "是否过滤文件 默认不过滤 ， 1 过滤")
    private String isFile = "0";
    @ApiModelProperty(value = "页数 ，默认1")
    private int page = 1;
    @ApiModelProperty(value = "页大小 ， 默认10")
    private int size = 10;
}
