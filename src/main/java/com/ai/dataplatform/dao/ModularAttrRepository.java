package com.ai.dataplatform.dao;

import com.ai.dataplatform.entity.ModularAttr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: ModularAttrRepository
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dao
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-10 10:14
 */

@Repository
public interface ModularAttrRepository extends JpaRepository<ModularAttr,Long> {

    public List<ModularAttr> findAllByModularIdOrderBySort(Long modularId);

    public void deleteByModularId(Long modularId);
}
