package com.qf.nanqiao.entity;

import lombok.Data;

/**
 * 后端给前端统一响应的对象
 *
 * @param <T> 数据的类型
 */
@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    // 私有化构造方法，不需要别人来调用的
    private R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 全参数的响应成功的方法
    public static <T> R<T> ok(Integer code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    // 只传递data数据的默认响应
    public static <T> R<T> ok(T data) {
        return ok(200, "请求成功", data);
    }

    // 不需要参数的成功响应
    public static <T> R<T> ok() {
        return ok(null);
    }

    // 全参数的响应失败的方法
    public static <T> R<T> fail(Integer code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    // 只传递data数据的默认失败响应
    public static <T> R<T> fail(T data) {
        return fail(-1, "请求失败", data);
    }

    // 不需要参数的失败响应
    public static <T> R<T> fail() {
        return fail(null);
    }

}
