package com.ai.dataplatform.dto;

import com.ai.dataplatform.entity.ModelItem;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelDto
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 14:25
 */
@Data
public class ModelDto {
    private ModelItem modelItem;
    private ModelNode adminNode;

}
