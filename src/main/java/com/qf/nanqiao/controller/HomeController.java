package com.qf.nanqiao.controller;

import com.qf.nanqiao.entity.ProjectDTO;
import com.qf.nanqiao.entity.R;
import com.qf.nanqiao.service.OrdersService;
import com.qf.nanqiao.service.PhysioService;
import com.qf.nanqiao.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 主页控制器
 *
 * @author zed
 * @date 2022/12/07
 */
@Slf4j
@RestController
@Api(tags = "主页控制器")
@RequiredArgsConstructor
@RequestMapping("home")
public class HomeController {

    private final ProjectService projectService;
    private final PhysioService physioService;
    private final OrdersService ordersService;

    /**
     * 查询统计信息
     *
     * @return {@link R}
     */
    @ApiOperation("查询统计信息")
    @GetMapping
    public R queryStatics() {
        // 使用stream获取总行数 这个效率没有在service直接写个count方法的效率高
        // 这里是为了给大家演示stream流
        List<ProjectDTO> pList = projectService.findAll();
        long projectCount = pList.size();
        // 在service写个count方法
        long physioCount = physioService.count();
        // 获取订单数量
        long ordersCount = ordersService.count();
        // 获取项目图片
        List<String> imgList = pList.stream().map(ProjectDTO::getImg).collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("projectCount", projectCount);
        data.put("physioCount", physioCount);
        data.put("ordersCount", ordersCount);
        data.put("imgList", imgList);
        return R.ok(data);
    }
}
