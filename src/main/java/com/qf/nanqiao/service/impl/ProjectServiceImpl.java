package com.qf.nanqiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.Project;
import com.qf.nanqiao.dao.ProjectDao;
import com.qf.nanqiao.entity.ProjectDTO;
import com.qf.nanqiao.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 服务项目表(Project)表服务实现类
 *
 * @author zed
 * @since 2022-11-30 15:16:41
 */
@Slf4j
@Service("projectService")
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;

    @Override
    public List<ProjectDTO> findAll() {
        return projectDao.findAll();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Project queryById(Integer id) {
        return this.projectDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param page  页面
     * @param size  大小
     * @param title 项目名称
     * @return 查询结果
     */
    @Override
    public PageInfo<Project> queryByPage(Integer page, Integer size, String title) {
        PageHelper.startPage(page, size);
        List<Project> list = this.projectDao.queryAll(title);
        return new PageInfo<>(list);
    }

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Project insert(Project project) {
        // 补全创建时间
        project.setCreateTime(new Date());
        // 补全消费人数
        project.setConsumeCount(0);
        this.projectDao.insert(project);
        return project;
    }

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Project update(Project project) {
        this.projectDao.update(project);
        return this.queryById(project.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        return this.projectDao.deleteById(id) > 0;
    }
}
