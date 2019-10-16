package com.ai.dataplatform.controller;

import com.ai.dataplatform.dto.DataItemListQry;
import com.ai.dataplatform.dto.ModelDto;
import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelController
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.controller
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 14:08
 */
@RestController
@RequestMapping(value = "/model")
@Api(value = "模型相关")
public class ModelController {
    
    @Autowired
    ModelService modelService;
    
    /**模型列表查询*/
    @RequestMapping(value = "modelListQry", method = {RequestMethod.POST})
    @ApiOperation(value = "模型列表查询", notes = "模型列表查询")
    public MyResp modelListQry(@Valid @RequestBody DataItemListQry qry) {
        return modelService.modelListQry(qry);
    }

    /**模型详情查询*/
    @RequestMapping(value = "modelItemQry", method = {RequestMethod.GET})
    @ApiOperation(value = "模型详情查询", notes = "模型详情查询")
    public MyResp modelItemQry(@RequestParam Long modelId) {
        return modelService.modelItemQry(modelId);
    }

    /**模型新增*/
    @RequestMapping(value = "modelItemAdd", method = {RequestMethod.POST})
    @ApiOperation(value = "模型新增", notes = "模型新增")
    public MyResp modelItemAdd(@Valid @RequestBody List<ModelDto> modelDtos) {
        return modelService.modelItemAdd(modelDtos);
    }

    /**模型修改*/
    @RequestMapping(value = "modelItemUpdate", method = {RequestMethod.POST})
    @ApiOperation(value = "模型修改", notes = "模型修改")
    public MyResp modelItemUpdate(@Valid @RequestBody List<ModelDto> modelDtos) {
        return modelService.modelItemUpdate(modelDtos);
    }

    /**模型删除*/
    @RequestMapping(value = "modelItemDel", method = {RequestMethod.GET})
    @ApiOperation(value = "模型删除", notes = "模型删除")
    public MyResp modelItemDel(@RequestParam Long modelId) {
        return modelService.modelItemDel(modelId);
    }
}
