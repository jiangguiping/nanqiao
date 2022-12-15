package com.qf.nanqiao.config;

import com.qf.nanqiao.entity.R;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    // 参数校验的异常处理
    @ExceptionHandler(BindException.class)
    public R<String> validatedBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return R.fail(message);
    }

    // 控制器层的全局异常处理
    // ExceptionHandler 注解的作用是：要处理哪种类信息的异常
    @ExceptionHandler(Exception.class)
    public R<String> handlerException(Exception e) {
        return R.fail(e.getMessage());
    }
}
