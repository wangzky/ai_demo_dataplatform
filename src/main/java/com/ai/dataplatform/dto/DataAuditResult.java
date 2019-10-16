package com.ai.dataplatform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataAuditResult
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 11:13
 */
@Data
public class DataAuditResult {
    @ApiModelProperty(value = "模型数量")
    private String modelSize;
    @ApiModelProperty(value = "模块数量")
    private String modularSize;
    @ApiModelProperty(value = "模型中模块数量")
    private String modularInModelSize;
    @ApiModelProperty(value = "模型外模块数量")
    private String modularOutModelSize;
    @ApiModelProperty(value = "模型中模块准确性")
    private String modelPrecision;
}
