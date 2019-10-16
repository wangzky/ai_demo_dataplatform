package com.ai.dataplatform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataItemListQry
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-09 10:42
 */
@ApiModel(value = "列表查询")
@Data
public class DataItemListQry {
    @ApiModelProperty(value = "关键字")
    private String keyWord;
    @ApiModelProperty(value = "页数 ，默认1")
    private int page = 1;
    @ApiModelProperty(value = "页大小 ， 默认10")
    private int size = 10;

}
