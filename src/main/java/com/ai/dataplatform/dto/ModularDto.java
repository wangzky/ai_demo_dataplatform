package com.ai.dataplatform.dto;

import com.ai.dataplatform.entity.ModularAttr;
import com.ai.dataplatform.entity.ModularItem;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModularDto
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 10:35
 */
@Data
public class ModularDto {
    private ModularItem modularItem;
    private List<ModularAttr> modularAttrs;
}
