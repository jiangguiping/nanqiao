package com.qf.nanqiao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 理疗师和项目的关系表(PhysioPro)实体类
 *
 * @author zed
 * @since 2022-12-01 17:16:03
 */
@Data
public class PhysioPro implements Serializable {
    private static final long serialVersionUID = -61100945185447002L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 理疗师ID
     */
    private Integer physioId;
    /**
     * 项目ID
     */
    private Integer projectId;

}

