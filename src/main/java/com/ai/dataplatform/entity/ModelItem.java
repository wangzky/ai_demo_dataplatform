package com.ai.dataplatform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelItem
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.entity
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 09:36
 */
@ApiModel(value = "模型项")
@Entity
@Table(name = "model_item")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ModelItem {
    @Id
    @ApiModelProperty(value = "模型Id")
    private Long modelId;
    @Column(length = 2000)
    private String modelName;
    @Column
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
