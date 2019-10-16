package com.ai.dataplatform.aspect;

import com.ai.dataplatform.dto.MyResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: GlobalExceptionHandler
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.aspect
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-16 10:16
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public MyResp notFound(RuntimeException e) {
        log.error("运行时异常：", e);
        String msg = "运行时异常:"+ e.getMessage();
        return new MyResp().FailResp(msg);
    }

}
