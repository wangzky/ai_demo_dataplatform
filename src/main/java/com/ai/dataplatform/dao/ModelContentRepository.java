package com.ai.dataplatform.dao;

import com.ai.dataplatform.entity.ModelContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelContentRepository
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dao
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 14:21
 */

@Repository
public interface ModelContentRepository extends JpaRepository<ModelContent, Long> {
    List<ModelContent> findAllByModelId (Long modelId);

    List<ModelContent> findAllByModelIdAndFatherNodeId(Long modelId, Long fatherNodeId);

    void deleteByModelId (Long modelId);

    @Query(value = "SELECT COUNT(DISTINCT modular_id ) FROM model_content WHERE 1=1 and model_id = :modelId ",nativeQuery = true)
    Integer findAllMudularSizeByModelId(@Param(value = "modelId")Long modelId);

    @Query(value = "SELECT COUNT(DISTINCT modular_id ) FROM model_content WHERE 1=1 and model_id = :modelId  AND modular_id IN (SELECT modular_id FROM modular_item)" ,nativeQuery = true)
    Integer findAllMudularSizeTrueByModelId(@Param(value = "modelId")Long modelId);

    List<ModelContent> findAllByModularId(Long modularId);

}
