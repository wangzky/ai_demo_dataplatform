package com.ai.dataplatform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: SysParam
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.entity
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 15:47
 */
@Data
@Table(name = "sys_param")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class SysParam {
    @Id
    private Long id;

    @Column(length = 2000)
    private String paramName;

    @Column(length = 2000)
    private String paramVal;
    @Column(length = 2000)
    private String state;

    @Column(length = 2000)
    private String remark;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
