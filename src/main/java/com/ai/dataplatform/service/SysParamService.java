package com.ai.dataplatform.service;

import com.ai.dataplatform.dao.SysParamRepository;
import com.ai.dataplatform.dto.MyResp;
import com.ai.dataplatform.entity.ModularItem;
import com.ai.dataplatform.entity.SysParam;
import com.ai.dataplatform.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: SysParamService
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.service
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-14 16:03
 */
@Slf4j
@Service
public class SysParamService {

    @Autowired
    SysParamRepository sysParamRepository;

    @Transactional
    public MyResp addOrUpdate(SysParam sysParam) {
        Long id = sysParam.getId();

        if (id == null || id == 0) {
            Long newId = RandomUtil.getId();
            sysParam.setId(newId);

            sysParamRepository.save(sysParam);

            return new MyResp().SuccessResp("新增成功" ,newId);
        } else {
            Optional<SysParam> byId = sysParamRepository.findById(id);
            if (byId.equals(Optional.empty())){
                return new MyResp().FailResp("ID 有误！");
            }
            sysParamRepository.save(sysParam);

            return new MyResp().SuccessResp("修改成功" ,id);
        }
    }

    @Transactional
    public MyResp del(Long id){
        log.info("删除参数【{}】" , id);
        Optional<SysParam> byId = sysParamRepository.findById(id);
        if (byId.equals(Optional.empty())){
            return new MyResp().FailResp("参数不存在！");
        }
        sysParamRepository.deleteById(id);
        return new MyResp().SuccessResp("删除成功！");
    }

    public MyResp qryById(Long id){

        Optional<SysParam> byId = sysParamRepository.findById(id);
        if (byId.equals(Optional.empty())){
            return new MyResp().FailResp("无记录");
        }
        SysParam sysParam = byId.get();

        return new MyResp().SuccessResp("查询成功！",sysParam);
    }


    @PostConstruct
    public void initData(){
        Optional<SysParam> byId = sysParamRepository.findById(111111L);
        if (byId.equals(Optional.empty())){
            SysParam sysParam = new SysParam();
            sysParam.setId(111111L);
            sysParam.setParamName("数据库架构");
            sysParam.setParamVal("");
            sysParam.setRemark("测试");
            sysParam.setState("01");
            sysParamRepository.save(sysParam);
            log.info("数据初始化成功【{}】" , sysParam.toString());
        }else {
            log.info("数据已存在【{}】" , byId.get().toString());
        }
    }
}
