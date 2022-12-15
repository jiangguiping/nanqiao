package com.qf.nanqiao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 服务项目表(Project)实体类
 *
 * @author zed
 * @since 2022-11-30 15:16:41
 */
@Data
public class Project implements Serializable {
    private static final long serialVersionUID = -67709828996193722L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 项目标题
     */
    private String title;
    /**
     * 项目图片
     */
    private String img;
    /**
     * 项目时长
     */
    private String duration;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 消费人数
     */
    private Integer consumeCount;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Project() {
    }

    public Project(Integer id, String title, String img, String duration, Integer price, Integer consumeCount) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.duration = duration;
        this.price = price;
        this.consumeCount = consumeCount;
        this.createTime = new Date();
    }
}

