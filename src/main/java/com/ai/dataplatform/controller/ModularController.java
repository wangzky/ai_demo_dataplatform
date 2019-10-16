package com.ai.dataplatform.controller;

import com.ai.dataplatform.dto.DataItemListQry;
import com.ai.dataplatform.dto.ModularDto;
import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.service.ModularService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
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
 * @date: 2019-10-10 09:52
 */
@RestController
@RequestMapping(value = "/modular")
@Api(value = "模块相关")
public class ModularController {

    @Autowired
    ModularService modularService;

    /**模块列表查询*/
    @RequestMapping(value = "modularListQry", method = {RequestMethod.POST})
    @ApiOperation(value = "模块列表查询", notes = "模块列表查询")
    public MyResp modularListQry(@Valid @RequestBody DataItemListQry qry) {
       return modularService.modularListQry(qry);
    }
    /**模块详情查询*/
    @RequestMapping(value = "modularItemQry", method = {RequestMethod.GET})
    @ApiOperation(value = "模块详情查询", notes = "模块详情查询")
    public MyResp modularItemQry(@RequestParam Long modularId) {
        return modularService.modularItemQry(modularId);
    }
    /**模块新增*/
    @RequestMapping(value = "modularItemAdd", method = {RequestMethod.POST})
    @ApiOperation(value = "模块新增", notes = "模块新增")
    public MyResp modularItemAdd(@Valid @RequestBody List<ModularDto> modularDtos) {
        return modularService.modularItemAdd(modularDtos);
    }
    /**模块修改*/
    @RequestMapping(value = "modularItemUpdate", method = {RequestMethod.POST})
    @ApiOperation(value = "模块修改", notes = "模块修改")
    public MyResp modularItemUpdate(@Valid @RequestBody List<ModularDto> modularDtos) {
        return modularService.modularItemUpdate(modularDtos);
    }
    /**模块删除*/
    @RequestMapping(value = "modularItemDel", method = {RequestMethod.GET})
    @ApiOperation(value = "模块删除", notes = "模块删除")
    public MyResp modularItemDel(@RequestParam Long modularId) {
        return modularService.modularItemDel(modularId);
    }
}
