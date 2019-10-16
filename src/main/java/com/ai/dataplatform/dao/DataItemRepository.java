package com.ai.dataplatform.dao;

import com.ai.dataplatform.entity.DataItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DataItemRepository
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dao
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-09 10:18
 */

@Repository
public interface DataItemRepository extends JpaRepository<DataItem, Long> {




    @Query(value = "SELECT * FROM modular_item a WHERE 1=1 AND a.modular_name like CONCAT('%',:keyWord,'%') AND a.modular_id IN ( SELECT b.modular_id FROM data_item b GROUP BY b.modular_id)",
            countQuery = "SELECT COUNT(*) FROM modular_item a WHERE 1=1 AND a.modular_name like CONCAT('%',:keyWord,'%')  AND a.modular_id IN ( SELECT b.modular_id FROM data_item b GROUP BY b.modular_id)",
            nativeQuery = true)
    Page hasDataListQry(@Param(value = "keyWord") String keyWord ,Pageable pageable);

    @Query(value = "select 1",
            countQuery = "select 1",
            nativeQuery = true)
    Page dataListQryByModularId(@Param(value = "modularId")Long modularId,@Param(value = "keyWord") String keyWord ,Pageable pageable);


    void deleteByDataItemId(Long dataItemId);

    List<DataItem> findByDataItemId(Long dataItemId);

    @Query(value = "select modular_id from data_item where data_item_id = :dataItemId " , nativeQuery = true)
    List<Long> qryModularIdByDataItemId(@Param(value = "dataItemId") Long dataItemId);

    @Query(value = "SELECT * FROM data_item WHERE 1=1\n" +
            "AND ( id LIKE CONCAT('%',:keyWord,'%')\n" +
            "OR  data_name LIKE CONCAT ('%',:keyWord,'%')\n" +
            "OR  data_remark LIKE CONCAT ('%',:keyWord,'%')\n" +
            "OR  data_file_name LIKE CONCAT ('%',:keyWord,'%')\n" +
            ")",
            countQuery = "SELECT count(*) FROM data_item WHERE 1=1\n" +
                    "AND ( id LIKE CONCAT('%',:keyWord,'%')\n" +
                    "OR  data_name LIKE CONCAT ('%',:keyWord,'%')\n" +
                    "OR  data_remark LIKE CONCAT ('%',:keyWord,'%')\n" +
                    "OR  data_file_name LIKE CONCAT ('%',:keyWord,'%')\n" +
                    ")",
            nativeQuery = true)
    Page<DataItem> findByKeyWord(@Param(value = "keyWord")String keyWord, Pageable pageable);
}
