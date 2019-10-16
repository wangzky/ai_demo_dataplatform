package com.ai.dataplatform.dao;

import com.ai.dataplatform.entity.SysParam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: SysParamRepository
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dao
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 16:10
 */
public interface SysParamRepository extends JpaRepository<SysParam , Long> {
}
