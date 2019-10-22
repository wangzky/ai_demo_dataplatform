package com.ai.dataplatform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataListQry
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-11 11:09
 */
@Data
public class DataListQry {
    @ApiModelProperty(value = "关键字")
    private String keyWord;
    @ApiModelProperty(value = "模块ID")
    private Long modularId;
    @ApiModelProperty(value = "数据项ID")
    private Long dataItemId = 0L;
    @ApiModelProperty(value = "页数 ，默认1")
    private int page = 1;
    @ApiModelProperty(value = "页大小 ， 默认10")
    private int size = 10;

}
