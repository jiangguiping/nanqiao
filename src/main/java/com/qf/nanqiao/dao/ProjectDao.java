package com.qf.nanqiao.dao;

import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.Project;
import com.qf.nanqiao.entity.ProjectDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 服务项目表(Project)表数据库访问层
 *
 * @author zed
 * @since 2022-11-30 15:16:40
 */
public interface ProjectDao {

    /**
     * 获取所有服务项目的名称和ID
     *
     * @return {@link List}<{@link ProjectDTO}>
     */
    @Select("select id,title,img from project")
    List<ProjectDTO> findAll();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Project queryById(Integer id);


    /**
     * 查询所有服务项目
     *
     * @param title 项目名称
     * @return {@link List}<{@link Project}>
     */
    List<Project> queryAll(String title);

    /**
     * 统计总行数
     *
     * @param project 查询条件
     * @return 总行数
     */
    long count(Project project);

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 影响行数
     */
    int insert(Project project);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Project> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Project> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Project> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Project> entities);

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 影响行数
     */
    int update(Project project);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

