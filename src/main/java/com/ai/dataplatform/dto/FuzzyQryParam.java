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

    @ApiModelProperty(value = "模块名称")
    private String modularName;
    @ApiModelProperty(value = "模块key名称")
    private String modularAttrName;
    @ApiModelProperty(value = "页数 ，默认1")
    private int page = 1;
    @ApiModelProperty(value = "页大小 ， 默认10")
    private int size = 10;
}
