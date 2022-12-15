package com.qf.nanqiao.entity;

import lombok.Data;

@Data
public class UserDTO {
    // 用户名
    private String username;
    // 明文密码
    private String password;
    // 图形验证码或者短信验证码
    private String code;
    // 手机号
    private String phone;
}
