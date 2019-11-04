package com.ai.dataplatform.controller;

import com.ai.dataplatform.dto.DataItemListQry;
import com.ai.dataplatform.dto.FuzzyQryParam;
import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.service.DataAuditService;
import com.ai.dataplatform.service.FuzzyQryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: FuzzyQryController
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.controller
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 10:42
 */
@Slf4j
@RestController
@RequestMapping(value = "/")
@Api(value = "其他接口")
public class OtherController {

    @Autowired
    FuzzyQryService fuzzyQryService;

    @Autowired
    DataAuditService dataAuditService;

    @PostMapping(value = "/fuzzyQry")
    @ApiOperation(value = "模糊查询", notes = "模糊查询")
    public MyResp fuzzyQry(@RequestBody FuzzyQryParam fuzzyQryParam){
        return fuzzyQryService.fuzzyQry(fuzzyQryParam);
    }

    @PostMapping(value = "/dataAudit")
    @ApiOperation(value = "数据稽核", notes = "数据稽核")
    public MyResp dataAudit(@RequestBody DataItemListQry qry){
        return dataAuditService.dataAudit(qry);
    }

    @GetMapping(value = "/dataAuditForFailureByModelId")
    @ApiOperation(value = "根据模型id查询失效模块", notes = "根据模型id查询失效模块")
    public MyResp dataAuditForFailureByModelId(@RequestParam Long modelId){
        return dataAuditService.dataAuditForFailureByModelId(modelId);
    }

    @GetMapping(value = "/test")
    @ApiOperation(value = "测试异常", notes = "测试异常")
    public MyResp test(){

        String str = null;
        boolean equals = str.equals("123");
        return new MyResp().SuccessResp("正常" , equals);
    }

}
