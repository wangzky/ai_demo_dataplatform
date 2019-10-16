package com.ai.dataplatform.dao;

import com.ai.dataplatform.entity.DataItem;
import com.ai.dataplatform.entity.ModularItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModularItemRepository
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dao
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 09:58
 */

@Repository
public interface ModularItemRepository extends JpaRepository<ModularItem , Long> {
    @Query(value = "select * from modular_item where 1=1 and modular_name like CONCAT('%',:keyWord,'%')",
            countQuery = "select count(*) from modular_item where 1=1 and modular_name like CONCAT('%',:keyWord,'%')",
            nativeQuery = true)
    Page<ModularItem> findByKeyWord(@Param(value = "keyWord") String keyWord, Pageable pageable);

    ModularItem findByModularName(String modularName);

}
