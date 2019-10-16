package com.ai.dataplatform.dao;

import com.ai.dataplatform.entity.ModelItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModelItemRepository
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dao
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 14:13
 */

@Repository
public interface ModelItemRepository extends JpaRepository<ModelItem, Long> {

//    @Query(value = "select * from model_item where 1=1 and model_name like CONCAT('%',:modelName,'%') ",
//            countQuery = "select count(*) from model_item where 1=1 and model_name like CONCAT('%',:modelName,'%') ",
//            nativeQuery = true)
    Page<ModelItem> queryAllByModelNameLike(String modelName, Pageable pageable);

    ModelItem queryByModelName(String modelName);

}
