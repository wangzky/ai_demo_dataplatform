package com.ai.dataplatform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModularAttr
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.entity
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-09 17:53
 */
@Entity
@Table(name = "modular_attr")
@ApiModel(value = "模块属性")
@Data
public class ModularAttr {
    @Id
    @ApiModelProperty(value = "属性ID")
    @GeneratedValue
    private Long modularAttrId;
    @Column(length = 2000)
    private Long modularId;
    @Column(length = 2000)
    private String modularAttrName;
    @Column(length = 2000)
    private String modularAttrType;
    @Column(length = 2000)
    private String modularAttrNotes;
    @Column(length = 8)
    private int sort;

}
