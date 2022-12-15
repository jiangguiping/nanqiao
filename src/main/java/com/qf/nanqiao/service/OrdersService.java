package com.qf.nanqiao.service;

import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.Orders;
import com.qf.nanqiao.entity.OrdersDTO;
import io.swagger.models.auth.In;
import org.springframework.data.domain.PageRequest;

/**
 * 订单表(Orders)表服务接口
 *
 * @author zed
 * @since 2022-12-05 10:44:18
 */
public interface OrdersService {

    /**
     * 支付订单
     *
     * @param orderNo 订单编号
     * @return boolean 是否支付成功
     */
    boolean payOrder(String orderNo);

    /**
     * 通过订单编号查询单条数据
     *
     * @param no 订单编号
     * @return {@link Orders}
     */
    Orders queryByNo(String no);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Orders queryById(Integer id);

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 查询结果
     */
    PageInfo<Orders> queryByPage(Integer page, Integer size);

    /**
     * 新增数据
     *
     * @param ordersDTO 实例对象
     * @return 实例对象
     */
    Orders insert(OrdersDTO ordersDTO);

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    Orders update(Orders orders);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 订单数
     *
     * @return long
     */
    long count();
}
