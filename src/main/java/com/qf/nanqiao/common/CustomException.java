package com.qf.nanqiao.common;

/**
 * 自定义异常
 *
 * @author zed
 * @date 2022/11/29
 */
public class CustomException extends Exception{

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
