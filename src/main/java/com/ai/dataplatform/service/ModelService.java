package com.ai.dataplatform.service;

import com.ai.dataplatform.dao.ModelContentRepository;
import com.ai.dataplatform.dao.ModelItemRepository;
import com.ai.dataplatform.dto.*;
import com.ai.dataplatform.entity.ModelContent;
import com.ai.dataplatform.entity.ModelItem;
import com.ai.dataplatform.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelService
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.service
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 14:10
 */
@Slf4j
@Service
public class ModelService {

    @Autowired
    ModelItemRepository modelItemRepository;

    @Autowired
    ModelContentRepository modelContentRepository;

    public MyResp modelListQry(DataItemListQry qry){
        log.info("查询列表：【{}】" , qry.toString());
        Page<ModelItem> page = modelItemRepository.queryAllByModelNameLike("%"+qry.getKeyWord()+"%", new PageRequest(qry.getPage() - 1 < 0 ? 0 : qry.getPage() - 1, qry.getSize()));
        List<ModelItem> list = page.getContent();
        Long totalPages = page.getTotalElements();
        return new MyResp().SuccessResp("查询成功", new PageResult(totalPages, list));
    }

    public MyResp modelItemQry(Long modelId){
        log.info("查询模型详情：【{}】" , modelId);
        ModelDto modelDto = new ModelDto();
        ModelNode modelNode = new ModelNode();
        List<ModelContent> list =  modelContentRepository.findAllByModelIdAndFatherNodeId(modelId , 0L);
        if (list.size() < 1) {
            return new MyResp().FailResp("该模型无内容");
        }

        ModelContent adminContent = list.get(0);
        modelNode.setKey(String.valueOf(adminContent.getModularId()));
        modelNode.setTitle(adminContent.getModularName());
        modelNode.setChildren(qryModelContent(modelId , adminContent.getModularId()));

        modelDto.setAdminNode(modelNode);

        Optional<ModelItem> byId = modelItemRepository.findById(modelId);
        if (!byId.equals(Optional.empty())){
            modelDto.setModelItem(byId.get());
        }

        return new MyResp().SuccessResp("查询成功" , modelDto);
    }

    @Transactional
    public MyResp modelItemAdd(List<ModelDto> modelDtos){

        int successNum = 0;
        int failNum = 0 ;
        List<Long> ids = new ArrayList<>();

        for (ModelDto modelDto : modelDtos){
            long id = RandomUtil.getId();
            ModelItem modelItem = modelDto.getModelItem();
            String modelName = modelItem.getModelName();

            if (StringUtils.isEmpty(modelName) || null != modelItemRepository.queryByModelName(modelName)){
                failNum ++;
                continue;
            }
            modelItem.setModelId(id);
            modelItemRepository.save(modelItem);

            ModelNode adminNode = modelDto.getAdminNode();
            List<ModelContent> modelContentList = new ArrayList<>();

            dealModelContent(adminNode , id , 0L , modelContentList);

            modelContentRepository.saveAll(modelContentList);
            successNum ++;
            ids.add(id);
        }
        String msg = "新增成功【"+successNum+"】条 ，失败【"+failNum+"】条";
        return new MyResp().SuccessResp(msg , ids);
    }

    @Transactional
    public MyResp modelItemUpdate(List<ModelDto> modelDtos){

        int successNum = 0;
        int failNum = 0 ;

        List<Long> ids = new ArrayList<>();

        for (ModelDto modelDto : modelDtos){
            ModelItem modelItem = modelDto.getModelItem();
            Long id = modelItem.getModelId();
            Optional<ModelItem> byId = modelItemRepository.findById(id);
            if (byId.equals(Optional.empty())){
                failNum ++;
                continue;
            }
            modelItemRepository.save(modelItem);

            // 全量删除
            modelContentRepository.deleteByModelId(id);

            // 保存
            ModelNode adminNode = modelDto.getAdminNode();
            List<ModelContent> modelContentList = new ArrayList<>();

            dealModelContent(adminNode , id , 0L , modelContentList);

            modelContentRepository.saveAll(modelContentList);
            successNum ++;
            ids.add(id);

        }
        String msg = "修改成功【"+successNum+"】条 ，失败【"+failNum+"】条";
        return new MyResp().SuccessResp(msg , ids);
    }

    @Transactional
    public MyResp modelItemDel(Long modelId){
        log.info("删除【{}】",modelId);

        Optional<ModelItem> byId = modelItemRepository.findById(modelId);
        if (byId.equals(Optional.empty())){
            return new MyResp().FailResp("模型不存在");
        }

        modelItemRepository.deleteById(modelId);
        modelContentRepository.deleteByModelId(modelId);

        return new MyResp().SuccessResp("删除成功:" + modelId);
    }

    private void dealModelContent(ModelNode adminNode , Long modelId ,Long fatherNodeId , List<ModelContent> modelContentList){
        Long modularId = Long.valueOf(adminNode.getKey());
        ModelContent modelContent = new ModelContent();
        modelContent.setModelId(modelId);
        modelContent.setFatherNodeId(fatherNodeId);
        modelContent.setModularId(modularId);
        modelContent.setModularName(adminNode.getTitle());
        modelContentList.add(modelContent);

        List<ModelNode> childNodes = adminNode.getChildren();
        if (childNodes!= null && childNodes.size() > 0){
            for (ModelNode modelNode : childNodes){
                if (null != modelNode){
                    dealModelContent(modelNode , modelId , modularId , modelContentList);
                }
            }
        }
    }


    private List<ModelNode>  qryModelContent(Long modelId , Long fatherNodeId ){
        List<ModelContent> list =  modelContentRepository.findAllByModelIdAndFatherNodeId(modelId , fatherNodeId);

        List<ModelNode> modelNodes = new ArrayList<>();
        if (list.size()> 0){

            for (ModelContent modelContent : list){
                Long modularId = modelContent.getModularId();
                String modularName = modelContent.getModularName();

                ModelNode modelNode = new ModelNode();
                modelNode.setKey(String.valueOf(modularId));
                modelNode.setTitle(modularName);

                List<ModelNode> modelNodes1 = qryModelContent(modelId, modularId);

                modelNode.setChildren(modelNodes1);

                modelNodes.add(modelNode);
            }
        }

        return modelNodes;
    }



}
