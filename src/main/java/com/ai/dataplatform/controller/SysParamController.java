package com.ai.dataplatform.controller;

import com.ai.dataplatform.dto.DataItemListQry;
import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.dto.PageResult;
import com.ai.dataplatform.entity.DataItem;
import com.ai.dataplatform.entity.SysParam;
import com.ai.dataplatform.service.SysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: SysParamController
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.controller
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 15:54
 */
@RestController
@Slf4j
@RequestMapping(value = "/sysParam")
@Api(value = "系统参数")
public class SysParamController {

    @Autowired
    SysParamService sysParamService;

    @RequestMapping(value = "add", method = {RequestMethod.POST})
    @ApiOperation(value = "新增", notes = "新增")
    public MyResp add(@Valid @RequestBody SysParam sysParam) {
        return sysParamService.addOrUpdate(sysParam);
    }

    @RequestMapping(value = "update", method = {RequestMethod.POST})
    @ApiOperation(value = "修改", notes = "修改")
    public MyResp update(@Valid @RequestBody SysParam sysParam) {
        return sysParamService.addOrUpdate(sysParam);
    }

    @RequestMapping(value = "del", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除", notes = "删除")
    public MyResp del(@RequestParam Long id) {
        return sysParamService.del(id);
    }

    @RequestMapping(value = "qryById", method = {RequestMethod.GET})
    @ApiOperation(value = "查询配置详情根据ID", notes = "查询配置详情根据ID")
    public MyResp qryById(@RequestParam Long id) {
        return sysParamService.qryById(id);
    }

}
