package com.qf.nanqiao.dao;

import com.qf.nanqiao.entity.PhysioPro;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 理疗师和项目的关系表(PhysioPro)表数据库访问层
 *
 * @author zed
 * @since 2022-12-01 17:16:03
 */
public interface PhysioProDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PhysioPro queryById(Integer id);

    /**
     * 新增数据
     *
     * @param physioPro 实例对象
     * @return 影响行数
     */
    int insert(PhysioPro physioPro);

    /**
     * 批量新增关联关系
     *
     * @param list 列表
     * @return int
     */
    int insertBatch(List<PhysioPro> list);

    /**
     * 修改数据
     *
     * @param physioPro 实例对象
     * @return 影响行数
     */
    int update(PhysioPro physioPro);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 通过理疗id删除关联数据
     *
     * @param physioId 理疗id
     * @return int
     */
    int deleteByPhysioId(Integer physioId);

    /**
     * 按项目id删除关联数据
     *
     * @param projectId 项目id
     * @return int
     */
    int deleteByProjectId(Integer projectId);


    /**
     * 通过理疗id查询项目id列表
     *
     * @param physioId 理疗id
     * @return {@link List}<{@link Integer}>
     */
    @Select("select project_id from physio_pro where physio_id=#{physioId}")
    List<Integer> findProjectIdsByPhysioId(Integer physioId);

}

