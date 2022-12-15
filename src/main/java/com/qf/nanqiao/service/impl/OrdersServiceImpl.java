package com.qf.nanqiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.common.OrderState;
import com.qf.nanqiao.common.PayType;
import com.qf.nanqiao.dao.OrdersDao;
import com.qf.nanqiao.dao.PhysioDao;
import com.qf.nanqiao.dao.ProjectDao;
import com.qf.nanqiao.entity.*;
import com.qf.nanqiao.service.OrdersService;
import com.qf.nanqiao.utils.StringUtils;
import com.qf.nanqiao.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 订单表(Orders)表服务实现类
 *
 * @author zed
 * @since 2022-12-05 10:44:18
 */
@Slf4j
@Service("ordersService")
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersDao ordersDao;
    private final PhysioDao physioDao;
    private final ProjectDao projectDao;

    @Override
    @Transactional
    public boolean payOrder(String orderNo) {
        // 修改订单状态为已支付，修改支付方式
        Orders orders = this.queryByNo(orderNo);
        if (Objects.nonNull(orders)) {
            orders.setPayType(PayType.ALI_PAY.getCode());
            orders.setFlag(OrderState.PAID.getCode());
            // 订单的更新时间
            orders.setUpdateTime(new Date());
            // 更新订单
            this.update(orders);
            // 查询技师
            Integer physioId = orders.getPhysioId();
            Physio physio = physioDao.queryById(physioId);
            if (Objects.nonNull(physio)) {
                // 技师的服务单数加1
                physio.setBillCount(physio.getBillCount() + 1);
                // 更新技师
                physioDao.update(physio);
                // 更新项目
                Integer projectId = orders.getProjectId();
                Project project = projectDao.queryById(projectId);
                if (Objects.nonNull(project)) {
                    // 项目的消费人数+1
                    project.setConsumeCount(project.getConsumeCount() + 1);
                    projectDao.update(project);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Orders queryByNo(String no) {
        return ordersDao.queryByNo(no);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Orders queryById(Integer id) {
        return this.ordersDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 页大小
     * @return 查询结果
     */
    @Override
    public PageInfo<Orders> queryByPage(Integer page, Integer size) {
        List<Orders> orders = new ArrayList<>();
        PageHelper.startPage(page, size);
        // 从ThreadLocal获取到用户信息
        User user = (User) ThreadLocalUtils.get("user");
        if (Objects.nonNull(user)) {
            orders = this.ordersDao.queryAllByPage(user.getId());
        }
        return new PageInfo<>(orders);
    }

    /**
     * TODO 新增数据-马上预约-下单
     *
     * @param ordersDTO 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Orders insert(OrdersDTO ordersDTO) {
        // 拷贝属性
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersDTO, orders);
        // 补全数据
        orders.setFlag(OrderState.UNPAID.getCode());
        orders.setCreateTime(new Date());
        // UUID格式: 55edd68fd7544302ad37d5e94eab36ab
        orders.setNo(StringUtils.getUUID());
        // 补全用户ID
        User user = (User) ThreadLocalUtils.get("user");
        orders.setUid(user.getId());
        if (ordersDao.insert(orders) > 0) {
            return orders;
        }
        return null;
    }

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Orders update(Orders orders) {
        this.ordersDao.update(orders);
        return this.queryById(orders.getId());
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
        return this.ordersDao.deleteById(id) > 0;
    }

    /**
     * 订单数
     *
     * @return long
     */
    @Override
    public long count() {
        return ordersDao.count(null);
    }
}
