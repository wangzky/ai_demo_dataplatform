package com.ai.dataplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataItemDto
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-11 09:45
 */
@Data
public class DataItemDto {
    private Long modularId;
    private Long dataItemId;
    private List<DataUnit> dataItems;

}
