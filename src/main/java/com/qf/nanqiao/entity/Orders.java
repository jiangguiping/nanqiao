package com.qf.nanqiao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表(Orders)实体类
 *
 * @author zed
 * @since 2022-12-05 11:10:21
 */
@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = -79800439981634619L;
    
    private Integer id;
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 总金额
     */
    private Double totalMoney;
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * 订单状态
     */
    private Integer flag;
    /**
     * 创建时间
     * GMT+8 北京时间 东8区时间
     * GMT Greenwich mean time中文解释：格林威治时间（世界标准时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /**
     * 订单编号
     */
    private String no;
    /**
     * 项目Id
     */
    private Integer projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 技师ID
     */
    private Integer physioId;
    /**
     * 技师名字
     */
    private String physioName;


}

