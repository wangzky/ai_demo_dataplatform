package com.ai.dataplatform.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelContent
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.entity
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 09:42
 */
@Entity
@Table(name = "model_content")
@ApiModel(value = "模型内容")
@Data
public class ModelContent {
    @Id
    @GeneratedValue
    private Long configId;
    @Column
    private Long modelId;
    @Column
    private Long modularId;
    @Column(length = 2000)
    private String modularName;
    @Column
    private Long fatherNodeId;

}
