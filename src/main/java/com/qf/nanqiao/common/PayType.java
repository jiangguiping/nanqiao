package com.qf.nanqiao.common;

/**
 * 支付类型
 *
 * @author zed
 * @date 2022/12/05
 */
public enum PayType {
    ALI_PAY(1, "支付宝"),
    WX_PAY(2, "微信支付"),
    CARD_PAY(3, "银行卡支付");

    int code;
    String type;

    PayType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
