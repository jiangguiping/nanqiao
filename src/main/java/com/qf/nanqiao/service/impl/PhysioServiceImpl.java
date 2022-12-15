package com.qf.nanqiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.qf.nanqiao.dao.PhysioDao;
import com.qf.nanqiao.dao.PhysioProDao;
import com.qf.nanqiao.entity.Physio;
import com.qf.nanqiao.entity.PhysioDTO;
import com.qf.nanqiao.entity.PhysioPro;
import com.qf.nanqiao.service.PhysioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 理疗师表(Physio)表服务实现类
 *
 * @author zed
 * @since 2022-12-01 17:04:47
 */
@Slf4j
@Service("physioService")
@RequiredArgsConstructor
public class PhysioServiceImpl implements PhysioService {

    private final PhysioDao physioDao;
    private final PhysioProDao physioProDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PhysioDTO queryById(Integer id) {
        Physio physio = this.physioDao.queryById(id);
        List<Integer> projectIds = physioProDao.findProjectIdsByPhysioId(id);
        PhysioDTO physioDTO = new PhysioDTO();
        // 拷贝属性
        BeanUtils.copyProperties(physio, physioDTO);
        physioDTO.setProjectIds(projectIds);
        return physioDTO;
    }

    /**
     * 分页查询
     *
     * @param page     页码
     * @param size     页大小
     * @param nickname 昵称
     * @return 查询结果
     */
    @Override
    public PageInfo<Physio> queryByPage(Integer page, Integer size, String nickname) {
        PageHelper.startPage(page, size);
        List<Physio> list = this.physioDao.queryAllByPage(nickname);
        return new PageInfo<>(list);
    }

    /**
     * 根据项目ID查找理疗师列表
     *
     * @param projectId 项目
     * @return 查询结果
     */
    @Override
    public List<Physio> queryByProjectId(Integer projectId) {
        return physioDao.queryByProjectId(projectId);
    }

    /**
     * 新增理疗师
     *
     * @param physioDTO 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Physio insert(PhysioDTO physioDTO) {
        Physio physio = new Physio();
        physio.setLike(0);
        physio.setBillCount(0);
        physio.setState(physioDTO.getState());
        physio.setAvatar(physioDTO.getAvatar());
        physio.setNickname(physioDTO.getNickname());
        // 新增理疗师
        if (this.physioDao.insert(physio) > 0) {
            if (batchInsertPhysioProject(physioDTO.getProjectIds(), physio.getId())) {
                return physio;
            }
        }
        return null;
    }

    /**
     * 批量插入理疗项目
     *
     * @param projectIds 项目id集合
     * @param physioId   理疗师id
     */
    private boolean batchInsertPhysioProject(List<Integer> projectIds, Integer physioId) {
        // 设置项目和理疗师的关系
        // 构造关联对象的集合
        List<PhysioPro> list = new ArrayList<>();
        projectIds.forEach(proId -> {
            PhysioPro physioPro = new PhysioPro();
            physioPro.setProjectId(proId); // 101  102  103
            physioPro.setPhysioId(physioId); // 11
            list.add(physioPro);
        });
        // 批量新增
        return physioProDao.insertBatch(list) > 0;
    }

    /**
     * 修改理疗师
     *
     * @param physioDTO 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public PhysioDTO update(PhysioDTO physioDTO) {
        Physio physio = new Physio();
        physio.setAvatar(physioDTO.getAvatar());
        physio.setNickname(physioDTO.getNickname());
        physio.setId(physioDTO.getId());
        physio.setState(physioDTO.getState());
        // 修改理疗师
        if (this.physioDao.update(physio) > 0) {
            // 也要修改关联关系
            if (physioProDao.deleteByPhysioId(physio.getId()) > 0) {
                if (batchInsertPhysioProject(physioDTO.getProjectIds(), physioDTO.getId())) {
                    return this.queryById(physio.getId());
                }
            }
        }
        return null;
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
        // 删除理疗师
        if (this.physioDao.deleteById(id) > 0) {
            // 删除项目和理疗师的关联关系
            if (physioProDao.deleteByPhysioId(id) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long count() {
        return physioDao.count(null);
    }
}
