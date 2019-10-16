package com.ai.dataplatform.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: MyResp
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-08 17:09
 */
@Data
@ApiModel(value = "返回对象", description = "返回对象")
public class MyResp {
    private String msg = "success";
    private String code = "0000";
    private Object data;


    public MyResp FailResp(String msg){
        this.code = "9999";
        this.msg = StringUtils.isEmpty(msg)? "fail" : msg;
        return this;
    }
    public MyResp SuccessResp(String msg){
        this.msg = StringUtils.isEmpty(msg)? "success" : msg;
        return this;
    }
    public MyResp SuccessResp(String msg,Object data){
        this.msg = StringUtils.isEmpty(msg)? "success" : msg;
        this.data = data;
        return this;
    }

}
