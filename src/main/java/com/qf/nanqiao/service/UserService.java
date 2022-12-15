package com.qf.nanqiao.service;

import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.User;
import com.qf.nanqiao.entity.UserDTO;

/**
 * (User)表服务接口
 *
 * @author zed
 * @since 2022-11-29 15:09:26
 */
public interface UserService {

    /**
     * 登录
     *
     * @param userDTO 用户dto
     * @return {@link User}
     */
    User login(UserDTO userDTO) throws Exception;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 分页查询
     *
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @return 查询结果
     */
    PageInfo<User> queryByPage(Integer pageIndex, Integer pageSize);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
