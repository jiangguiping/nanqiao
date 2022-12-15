package com.qf.nanqiao.service;

import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.Physio;
import com.qf.nanqiao.entity.PhysioDTO;

import java.util.List;

/**
 * 理疗师表(Physio)表服务接口
 *
 * @author zed
 * @since 2022-12-01 17:04:47
 */
public interface PhysioService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PhysioDTO queryById(Integer id);

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @param nickname 昵称
     * @return 查询结果
     */
    PageInfo<Physio> queryByPage(Integer page,Integer size,String nickname);


    /**
     * 根据项目ID查找理疗师列表
     *
     * @param projectId 项目
     * @return 查询结果
     */
    List<Physio> queryByProjectId(Integer projectId);

    /**
     * 新增数据
     *
     * @param physioDTO 实例对象
     * @return 实例对象
     */
    Physio insert(PhysioDTO physioDTO);

    /**
     * 修改数据
     *
     * @param physioDTO 实例对象
     * @return 实例对象
     */
    PhysioDTO update(PhysioDTO physioDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取理疗师数量
     *
     * @return long
     */
    long count();
}
