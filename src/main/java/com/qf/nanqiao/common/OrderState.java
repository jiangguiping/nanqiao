package com.qf.nanqiao.common;

/**
 * 订单状态
 *
 * @author zed
 * @date 2022/12/05
 */
public enum OrderState {
    UNPAID(0, "待付款"),
    PAID(1, "已付款");

    int code;
    String msg;

    OrderState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
