package com.qf.nanqiao.controller;

import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.Project;
import com.qf.nanqiao.entity.ProjectDTO;
import com.qf.nanqiao.entity.R;
import com.qf.nanqiao.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "服务项目接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("project")
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 新增服务项目
     *
     * @param project 项目
     * @return {@link R}<{@link Project}>
     */
    @ApiOperation("新增服务项目")
    @PostMapping("insert")
    public R<Project> insert(@RequestBody Project project) {
        Assert.hasLength(project.getTitle(), "项目标题不能为空");
        Assert.hasLength(project.getImg(), "项目图片不能为空");
        Assert.hasLength(project.getDuration(), "项目时长不能为空");
        Assert.notNull(project.getPrice(), "项目价格不能为空");
        Project insert = projectService.insert(project);
        return R.ok(insert);
    }


    /**
     * 分页查询服务项目
     *
     * @param page  页面
     * @param size  大小
     * @param title 项目名称
     * @return {@link R}<{@link Project}>
     */
    @ApiOperation("分页查询服务项目")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "查询第几页"),
            @ApiImplicitParam(name = "size", value = "每页显示多少条数据"),
            @ApiImplicitParam(name = "title", value = "项目名称模糊搜索")
    })
    @PostMapping("page")
    public R<PageInfo<Project>> findByPage(@RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                           @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                           String title) {
        PageInfo<Project> pageInfo = projectService.queryByPage(page, size, title);
        return R.ok(pageInfo);
    }

    /**
     * 查询所有服务项目的ID和名称
     *
     * @return
     */
    @ApiOperation("查询所有服务项目的ID和名称")
    @GetMapping("list")
    public R<List<ProjectDTO>> findAll() {
        return R.ok(projectService.findAll());
    }


    /**
     * 根据ID查询项目
     *
     * @param id 服务项目ID
     * @return {@link R}<{@link Project}>
     */
    @GetMapping("{id}")
    @ApiOperation("根据ID查询项目")
    public R<Project> findById(@PathVariable("id") Integer id) {
        Assert.notNull(id, "ID不能为空");
        return R.ok(projectService.queryById(id));
    }

    /**
     * 更新服务项目
     *
     * @param project 项目
     * @return {@link R}<{@link Project}>
     */
    @PostMapping("update")
    @ApiOperation("更新服务项目")
    public R<Project> update(@RequestBody Project project) {
        Assert.notNull(project.getId(), "ID不能为空");
        return R.ok(projectService.update(project));
    }

    /**
     * 根据ID删除服务项目
     *
     * @param id 服务项目id
     * @return {@link R}<{@link Project}>
     */
    @DeleteMapping("{id}")
    @ApiOperation("根据ID删除服务项目")
    public R<Boolean> delete(@PathVariable("id") Integer id) {
        Assert.notNull(id, "ID不能为空");
        return R.ok(projectService.deleteById(id));
    }

}
