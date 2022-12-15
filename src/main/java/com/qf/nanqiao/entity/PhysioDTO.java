package com.qf.nanqiao.entity;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 理疗师的数据传输对象
 * DTO: Data Transfer Object
 */
@Data
public class PhysioDTO {

    /**
     * 理疗师id
     */
    private Integer id;

    /**
     * 昵称
     */
    @NotBlank(message = "{physio.nickname.notBlank}")
    private String nickname;
    /**
     * 头像
     */
    @NotBlank(message = "{physio.avatar.notBlank}")
    private String avatar;

    /**
     * 服务状态：1可服务  0 不可服务
     */
    @Max(value = 1, message = "{physio.state.min}")
    @Min(value = 0, message = "{physio.state.max}")
    @NotNull(message = "{physio.state.notNull}")
    private Integer state;

    /**
     * 点赞数
     */
    private Integer like;
    /**
     * 服务单数
     */
    private Integer billCount;

    /**
     * 可以做的项目IDS
     */
    @NotEmpty(message = "{physio.projectIds.notEmpty}")
    private List<Integer> projectIds;
}
