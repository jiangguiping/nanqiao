package com.qf.nanqiao.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.nanqiao.common.CustomException;
import com.qf.nanqiao.config.RedisConstant;
import com.qf.nanqiao.dao.UserDao;
import com.qf.nanqiao.entity.User;
import com.qf.nanqiao.entity.UserDTO;
import com.qf.nanqiao.service.UserService;
import com.qf.nanqiao.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author zed
 * @since 2022-11-29 15:09:26
 */
@Slf4j
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User login(UserDTO userDTO) throws Exception {
        User loginUser = null;
        String username = userDTO.getUsername();
        // 短信验证码或者图像验证码
        String code = userDTO.getCode();
        if (StringUtils.hasLength(username)) {
            log.info("使用用户名和密码登录");
            // 校验图像验证码是否正确
            String redisCaptchaCode = RedisUtils.getString(RedisConstant.CAPTCHA + username);
            if (Objects.equals(code, redisCaptchaCode)) {
                // 前端传递过来的密码，需要进行加密后跟数据库中的密码做比对的
                String password = userDTO.getPassword();
                String md5Password = SecureUtil.md5(password);
                List<User> userList = userDao.findByUsername(username);
                for (User user : userList) {
                    // 前端传递过来的密码加密后和数据库中的密文进行比对
                    if (Objects.equals(user.getPassword(), md5Password)) {
                        log.info("用户名密码登录成功,用户名{},密码{}", username, password);
                        loginUser = user;
                    }
                }
            } else {
                throw new CustomException("图像验证码错误");
            }
        }
        String phone = userDTO.getPhone();
        if (StringUtils.hasLength(phone)) {
            log.info("使用手机号进行登录");
            // 判断手机验证码是否正确
            String redisSmdCode = RedisUtils.getString(RedisConstant.SMS_CODE + phone);
            if (Objects.equals(code, redisSmdCode)) {
                log.info("手机号验证码登录成功");
                loginUser = userDao.findByPhone(phone);
            } else {
                throw new CustomException("短信验证码错误");
            }
        }
        // 把用户信息放入redis中
        RedisUtils.setObject("user:" + loginUser.getId(), loginUser, 1, TimeUnit.HOURS);
        return loginUser;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @return 查询结果
     */
    @Override
    public PageInfo<User> queryByPage(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<User> list = this.userDao.findAll();
        return new PageInfo<>(list);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
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
        return this.userDao.deleteById(id) > 0;
    }
}
