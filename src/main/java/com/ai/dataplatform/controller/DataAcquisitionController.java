//package com.ai.dataplatform.controller;
//
//import com.ai.dataplatform.dao.DataItemRepository;
//import com.ai.dataplatform.dto.DataItemListQry;
//import com.ai.dataplatform.dto.MyResp;
//import com.ai.dataplatform.dto.PageResult;
//import com.ai.dataplatform.entity.DataItem;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.repository.query.Param;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @Title: DataAcquisitionController
// * @ProjectName: ai_demo_dataplatform
// * @PackageName: com.ai.dataplatform.controller
// * @Description: TODO
// * @author: wangzk
// * @date: 2019-10-09 09:59
// */
//@Slf4j
//@RestController
//@RequestMapping(value = "/daq")
//@Api(value = "数据采集 & 查询")
//public class DataAcquisitionController {
//
//    @Autowired
//    DataItemRepository dataItemRepository;
//
//    @RequestMapping(value = "add", method = {RequestMethod.POST})
//    @ApiOperation(value = "新增", notes = "新增")
//    public MyResp add(@Valid @RequestBody DataItem dataItem) {
//        if (dataItem.getId() != 0) {
//            Optional<DataItem> byId = dataItemRepository.findById(dataItem.getId());
//            if (byId.get() != null) {
//                return new MyResp().FailResp("ID 已存在！");
//            }
//        }
//        dataItemRepository.save(dataItem);
//        return new MyResp().SuccessResp("新增成功");
//    }
//
//    @RequestMapping(value = "update", method = {RequestMethod.POST})
//    @ApiOperation(value = "修改", notes = "修改")
//    public MyResp update(@Valid @RequestBody DataItem dataItem) {
//        if (dataItem.getId() != 0) {
//            Optional<DataItem> byId = dataItemRepository.findById(dataItem.getId());
//            if (byId.equals(Optional.empty())) {
//                return new MyResp().FailResp("ID 不存在！");
//            } else {
//                dataItemRepository.save(dataItem);
//                return new MyResp().SuccessResp("修改成功");
//            }
//        }
//        return new MyResp().FailResp("ID为空！");
//    }
//
//    @RequestMapping(value = "del", method = {RequestMethod.DELETE})
//    @ApiOperation(value = "删除", notes = "删除")
//    public MyResp del(@RequestParam Long id) {
//        Optional<DataItem> byId = dataItemRepository.findById(id);
//        if (byId.equals(Optional.empty())) {
//            return new MyResp().FailResp("ID 不存在！");
//        } else {
//            dataItemRepository.deleteById(id);
//            return new MyResp().SuccessResp("删除成功");
//        }
//    }
//
//    @RequestMapping(value = "qry", method = {RequestMethod.POST})
//    @ApiOperation(value = "查询", notes = "模糊查询")
//    public MyResp qry(@Valid @RequestBody DataItemListQry qry) {
//        Page<DataItem> page = dataItemRepository.findByKeyWord(qry.getKeyWord(), new PageRequest(qry.getPage() - 1 < 0 ? 0 : qry.getPage() - 1, qry.getSize()));
//        List<DataItem> list = page.getContent();
//        Long totalPages = page.getTotalElements();
//        return new MyResp().SuccessResp("查询成功", new PageResult(totalPages, list));
//    }
//}
