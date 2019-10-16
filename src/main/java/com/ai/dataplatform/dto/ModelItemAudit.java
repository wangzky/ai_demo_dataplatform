package com.ai.dataplatform.dto;

import com.ai.dataplatform.entity.ModelItem;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelItemAudit
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 14:45
 */
@Data
public class ModelItemAudit extends ModelItem {
    /**模型中模块总数*/
    private String modularTotal;
    /**有效模块数*/
    private String modularValidTotal;
    /**无效模块数*/
    private String modularFailureTotal;

    public ModelItemAudit (){}

    public ModelItemAudit(ModelItem modelItem){
        this.setModelId(modelItem.getModelId());
        this.setModelName(modelItem.getModelName());
        this.setCreateTime(modelItem.getCreateTime());
    }
}
