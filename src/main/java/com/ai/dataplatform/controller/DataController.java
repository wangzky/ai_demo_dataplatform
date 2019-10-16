package com.ai.dataplatform.controller;

import com.ai.dataplatform.dto.*;
import com.ai.dataplatform.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataController
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.controller
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-11 09:48
 */
@RestController
@Slf4j
@RequestMapping(value = "/data")
@Api(value = "数据录入")
public class DataController {

    @Autowired
    DataService dataService;

    /**有数据的模块列表查询*/
    @RequestMapping(value = "hasDataListQry", method = {RequestMethod.POST})
    @ApiOperation(value = "有数据的模块列表查询", notes = "有数据的模块列表查询")
    public MyResp hasDataListQry(@Valid @RequestBody DataItemListQry qry) {
        return dataService.hasDataListQry(qry);
    }

    /**根据模块ID 查询数据列表*/
    @RequestMapping(value = "dataListQryByModularId", method = {RequestMethod.POST})
    @ApiOperation(value = "根据模块ID 查询数据列表", notes = "根据模块ID 查询数据列表  keyWord 传modularId 模块ID")
    public MyResp dataListQryByModularId(@Valid @RequestBody DataListQry qry) {
        return dataService.dataListQryByModularId(qry);
    }

    /**根据数据ID 查询数据详情*/
    @RequestMapping(value = "dataInfoQryByDataItemId", method = {RequestMethod.GET})
    @ApiOperation(value = "根据数据ID 查询数据详情", notes = "根据数据ID 查询数据详情")
    public MyResp dataInfoQryByDataItemId(@RequestParam Long dataItemId) {
        return dataService.dataInfoQryByDataItemId(dataItemId);
    }

    /**数据新增*/
    @RequestMapping(value = "dataItemAdd", method = {RequestMethod.POST})
    @ApiOperation(value = "数据新增", notes = "数据新增")
    public MyResp dataItemAdd(@Valid @RequestBody List<DataItemDto> dataItemDtos) {
        return dataService.dataItemAdd(dataItemDtos);
    }

    /**数据修改*/
    @RequestMapping(value = "dataItemUpdate", method = {RequestMethod.POST})
    @ApiOperation(value = "数据修改", notes = "数据修改")
    public MyResp dataItemUpdate(@Valid @RequestBody List<DataItemDto> dataItemDtos) {
        return dataService.dataItemUpdate(dataItemDtos);
    }

    /**数据删除*/
    @RequestMapping(value = "dataItemDel", method = {RequestMethod.GET})
    @ApiOperation(value = "数据删除", notes = "数据删除")
    public MyResp dataItemDel(@RequestParam Long dataItemId) {
        return dataService.dataItemDel(dataItemId);
    }
}
