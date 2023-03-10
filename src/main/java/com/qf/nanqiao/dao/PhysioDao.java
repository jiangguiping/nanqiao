package com.qf.nanqiao.dao;

import com.qf.nanqiao.entity.Physio;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 理疗师表(Physio)表数据库访问层
 *
 * @author zed
 * @since 2022-12-01 17:04:47
 */
public interface PhysioDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Physio queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param nickname 昵称
     * @return 对象列表
     */
    List<Physio> queryAllByPage(String nickname);

    /**
     * 统计总行数
     *
     * @param physio 查询条件
     * @return 总行数
     */
    long count(Physio physio);

    /**
     * 新增数据
     *
     * @param physio 实例对象
     * @return 影响行数
     */
    int insert(Physio physio);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Physio> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Physio> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Physio> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Physio> entities);

    /**
     * 修改数据
     *
     * @param physio 实例对象
     * @return 影响行数
     */
    int update(Physio physio);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 按项目id查询理疗师列表
     *
     * @param projectId 项目id
     * @return {@link List}<{@link Physio}>
     */
    List<Physio> queryByProjectId(Integer projectId);
}

