package com.ai.dataplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelNode
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 14:31
 */
@Data
public class ModelNode {
    private String key;
    private String title;
    private List<ModelNode> children;
}
