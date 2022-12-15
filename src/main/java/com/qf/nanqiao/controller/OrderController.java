package com.qf.nanqiao.controller;

import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.Orders;
import com.qf.nanqiao.entity.OrdersDTO;
import com.qf.nanqiao.entity.R;
import com.qf.nanqiao.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 *
 * @author zed
 * @date 2022/12/05
 */
@Slf4j
@RestController
@Api(tags = "订单控制器")
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {

    private final OrdersService ordersService;

    /**
     * 根据ID查找订单
     *
     * @param id 订单id
     * @return {@link R}
     */
    @ApiOperation("根据ID查找订单")
    @GetMapping("{id}")
    public R findById(@PathVariable("id") Integer id) {
        Assert.notNull(id, "订单ID不能为空");
        return R.ok(ordersService.queryById(id));
    }

    /**
     * 分页查询订单
     *
     * @param page 页面
     * @param size 大小
     * @return {@link R}<{@link PageInfo}<{@link Orders}>>
     */
    @ApiOperation("分页查询订单")
    @PostMapping("page")
    public R<PageInfo<Orders>> findById(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return R.ok(ordersService.queryByPage(page, size));
    }


    /**
     * 根据ID删除订单
     *
     * @param id 订单id
     * @return {@link R}
     */
    @ApiOperation("根据ID删除订单")
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable("id") Integer id) {
        Assert.notNull(id, "订单ID不能为空");
        return R.ok(ordersService.deleteById(id));
    }

    /**
     * 马上预约
     *
     * @param dto dto
     * @return {@link R}
     */
    @ApiOperation("马上预约")
    @PostMapping("insert")
    public R insert(@RequestBody OrdersDTO dto) {
        Assert.notNull(dto.getProjectId(), "项目ID不能为空");
        Assert.hasText(dto.getProjectName(), "项目名称不能为空");
        Assert.notNull(dto.getPhysioId(), "技师ID不能为空");
        Assert.hasText(dto.getPhysioName(), "技师名称不能为空");
        // 鼠标放在哪个方法上 按ctrl alt b 可以快速进入方式实现
        return R.ok(ordersService.insert(dto));
    }
}
