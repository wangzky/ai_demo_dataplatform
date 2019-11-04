package com.ai.dataplatform.service;

import com.ai.dataplatform.dao.ModelContentRepository;
import com.ai.dataplatform.dao.ModelItemRepository;
import com.ai.dataplatform.dao.ModularItemRepository;
import com.ai.dataplatform.dto.*;
import com.ai.dataplatform.entity.ModelItem;
import com.ai.dataplatform.entity.ModularItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataAuditService
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.service
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 11:19
 */
@Service
@Slf4j
public class DataAuditService {

    @Autowired
    ModelItemRepository modelItemRepository;
    @Autowired
    ModularItemRepository modularItemRepository;
    @Autowired
    ModelContentRepository modelContentRepository;


    public MyResp dataAudit(DataItemListQry qry){

//        List<ModelItem> modelItemList = modelItemRepository.findAll();

        Page<ModelItem> page = modelItemRepository.queryAllByModelNameLike("%"+qry.getKeyWord()+"%", new PageRequest(qry.getPage() - 1 < 0 ? 0 : qry.getPage() - 1, qry.getSize()));
        List<ModelItem> modelItemList = page.getContent();
        Long totalPages = page.getTotalElements();

        List<ModelItemAudit> modelItemAudits = new ArrayList<>();

        for (ModelItem item : modelItemList){
            ModelItemAudit modelItemAudit = new ModelItemAudit(item);
            Long modelId = item.getModelId();
            Integer allMudularSize = modelContentRepository.findAllMudularSizeByModelId(modelId);
            Integer allMudularSizeTrue = modelContentRepository.findAllMudularSizeTrueByModelId(modelId);

            modelItemAudit.setModularTotal(String.valueOf(allMudularSize));
            modelItemAudit.setModularValidTotal(String.valueOf(allMudularSizeTrue));
            modelItemAudit.setModularFailureTotal(String.valueOf(allMudularSize - allMudularSizeTrue));

            modelItemAudits.add(modelItemAudit);
        }


        log.info("稽核结果【{}】"  , modelItemAudits.toString());
        return new MyResp().SuccessResp("查询成功", new PageResult(totalPages, modelItemAudits));
    }


    public MyResp dataAuditForFailureByModelId(Long modelId){
        List failureByModelId = modelContentRepository.findFailureByModelId(modelId);

        log.info("查询【{}】失效模块,结果【{}】" , modelId , failureByModelId.toString());
        return new MyResp().SuccessResp("查询成功",failureByModelId);
    }
}
