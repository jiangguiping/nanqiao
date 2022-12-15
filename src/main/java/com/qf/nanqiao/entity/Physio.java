package com.qf.nanqiao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 理疗师表(Physio)实体类
 *
 * @author zed
 * @since 2022-12-01 17:04:47
 */
@Data
public class Physio implements Serializable {
    private static final long serialVersionUID = 998889015541258528L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 服务状态:1可服务 0不可服务
     */
    private Integer state;
    /**
     * 点赞数
     */
    private Integer like;
    /**
     * 服务单数
     */
    private Integer billCount;

}

