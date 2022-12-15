package com.qf.nanqiao.entity;

import lombok.Data;

@Data
public class OrdersDTO {

    // 项目ID
    private Integer projectId;

    // 项目名称
    private String projectName;

    // 技师ID
    private Integer physioId;

    // 技师名称
    private String physioName;

    // 项目价格
    private Double totalMoney;
}
