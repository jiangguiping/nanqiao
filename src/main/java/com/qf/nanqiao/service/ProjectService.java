package com.qf.nanqiao.service;

import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.Project;
import com.qf.nanqiao.entity.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 服务项目表(Project)表服务接口
 *
 * @author zed
 * @since 2022-11-30 15:16:41
 */
public interface ProjectService {

    List<ProjectDTO> findAll();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Project queryById(Integer id);

    /**
     * 分页查询
     *
     * @param page 页面
     * @param size 大小
     * @param title 项目名称
     * @return 查询结果
     */
    PageInfo<Project> queryByPage(Integer page, Integer size,String title);

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    Project insert(Project project);

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    Project update(Project project);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
