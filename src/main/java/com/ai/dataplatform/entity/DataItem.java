package com.ai.dataplatform.entity;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataItem
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.entity
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-09 10:07
 */
@Entity
@Table(name = "data_item")
@ApiModel(value = "数据项")
@Data
public class DataItem {
    @Id
    @GeneratedValue
    @ApiModelProperty(name = "id", value = "数据ID")
    private long id;
    @Column
    @ApiModelProperty(name = "dataItemId", value = "数据项ID")
    private long dataItemId;
    @Column
    @ApiModelProperty(name = "modularId", value = "模块ID")
    private long modularId;
    @Column
    @ApiModelProperty(name = "modularAttrId", value = "数据项键ID")
    private Long modularAttrId;
    @Column(length = 2000)
    @ApiModelProperty(name = "dataVal", value = "数据项值")
    private String dataVal;
}
