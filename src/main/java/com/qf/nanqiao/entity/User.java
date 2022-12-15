package com.qf.nanqiao.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author zed
 * @since 2022-11-29 15:09:26
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -92388301813318713L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private String sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;

    public User() {
    }

    public User(String username, String password, String avatar, String phone, String email, Integer age, String sex, String address) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.createTime = new Date();
    }
}

