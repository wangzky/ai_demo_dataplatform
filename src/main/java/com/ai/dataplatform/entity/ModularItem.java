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
 * @Title: Modular
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.entity
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-09 17:47
 */
@Entity
@Table(name = "modular_item")
@ApiModel(value = "模块项")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ModularItem {
    @Id
    @ApiModelProperty(value = "模块Id")
    private Long modularId;
    @Column(length = 2000)
    @ApiModelProperty(value = "模块名称")
    private String modularName;
    @Column
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
