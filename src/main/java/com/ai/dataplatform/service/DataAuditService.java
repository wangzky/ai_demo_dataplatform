package com.ai.dataplatform.service;

import com.ai.dataplatform.dao.ModelContentRepository;
import com.ai.dataplatform.dao.ModelItemRepository;
import com.ai.dataplatform.dao.ModularItemRepository;
import com.ai.dataplatform.dto.DataAuditResult;
import com.ai.dataplatform.dto.ModelItemAudit;
import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.entity.ModelItem;
import com.ai.dataplatform.entity.ModularItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    public MyResp dataAudit(){

        List<ModelItem> modelItemList = modelItemRepository.findAll();

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


        log.info("稽核结果【{}】" + modelItemAudits.toString());
        return new MyResp().SuccessResp("查询成功" , modelItemAudits);
    }
}
