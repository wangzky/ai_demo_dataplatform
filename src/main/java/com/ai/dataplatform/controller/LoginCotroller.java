package com.ai.dataplatform.controller;

import com.ai.dataplatform.dto.LoginDto;
import com.ai.dataplatform.dto.MyResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: LoginCotroller
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.controller
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-22 09:42
 */
@Slf4j
@RestController
@Api(value = "登录相关接口")
@RequestMapping(value = "/login")
public class LoginCotroller {

    @RequestMapping(value = "in", method = {RequestMethod.POST})
    @ApiOperation(value = "登录", notes = "登录")
    public MyResp loginIn(@RequestBody LoginDto loginDto){

        if (loginDto.getUserName().equals("admin") && loginDto.getPassWord().equals("admin")){
            return new MyResp().SuccessResp("登录成功");
        }else {
            return new MyResp().FailResp("登录失败：用户名密码错误");
        }

    }
    @RequestMapping(value = "out", method = {RequestMethod.GET})
    @ApiOperation(value = "登出", notes = "登出")
    public MyResp loginOut(){

        return new MyResp().SuccessResp("注销成功");
    }
}
