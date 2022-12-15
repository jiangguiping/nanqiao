package com.qf.nanqiao.controller;

import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.entity.Physio;
import com.qf.nanqiao.entity.PhysioDTO;
import com.qf.nanqiao.entity.R;
import com.qf.nanqiao.service.PhysioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@Api(tags = "理疗师接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("physio")
public class PhysioController {

    private final PhysioService physioService;

    @ApiOperation("根据ID查询理疗师")
    @ApiImplicitParam(name = "id", value = "理疗师ID")
    @GetMapping("{id}")
    public R<PhysioDTO> findById(@PathVariable("id") Integer id) {
        Assert.notNull(id, "ID不能为空");
        return R.ok(physioService.queryById(id));
    }

    @ApiOperation("分页查询理疗师")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "查询第几页"),
            @ApiImplicitParam(name = "size", value = "每页显示多少条数据"),
            @ApiImplicitParam(name = "nickname", value = "理疗师昵称模糊搜索")
    })
    @PostMapping("page")
    public R<PageInfo<Physio>> findByPage(@RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                          @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                          String nickname) {
        PageInfo<Physio> pageInfo = physioService.queryByPage(page, size, nickname);
        return R.ok(pageInfo);
    }

    @PostMapping("insert")
    @ApiOperation("新增理疗师")
    public R insert(@RequestBody @Validated PhysioDTO dto) {
        // 新增理疗师
        Physio physio = physioService.insert(dto);
        if (Objects.nonNull(physio)) {
            return R.ok(physio);
        }
        return R.fail("新增理疗师失败");
    }

    @PostMapping("update")
    @ApiOperation("更新理疗师")
    public R update(@RequestBody PhysioDTO dto) {
        // 参数校验
        Assert.notNull(dto.getId(), "ID不能为空");
        validatePhysioDto(dto);
        // 新增理疗师
        PhysioDTO physio = physioService.update(dto);
        if (Objects.nonNull(physio)) {
            return R.ok(physio);
        }
        return R.fail("更新理疗师失败");
    }

    @DeleteMapping("{id}")
    @ApiOperation("根据ID删除理疗师")
    public R<String> delete(@PathVariable("id") Integer id) {
        Assert.notNull(id, "ID不能为空");
        if (physioService.deleteById(id)) {
            return R.ok("删除成功");
        }
        return R.fail("删除失败");
    }


    @ApiOperation("根据项目ID查找理疗师")
    @ApiImplicitParam(name = "id", value = "项目id", required = true)
    @GetMapping("project/{id}")
    public R<List<Physio>> findByProjectId(@PathVariable("id") Integer id) {
        Assert.notNull(id, "项目ID不能为空");
        List<Physio> physioList = physioService.queryByProjectId(id);
        return R.ok(physioList);
    }


    // 参数校验
    private static void validatePhysioDto(PhysioDTO dto) {
        Assert.hasText(dto.getNickname(), "昵称不能为空");
        Assert.hasText(dto.getAvatar(), "头像不能为空");
        Assert.notEmpty(dto.getProjectIds(), "服务项目不能没有");
    }

}
