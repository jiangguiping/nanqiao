package com.qf.nanqiao.controller;

import cn.hutool.core.util.RandomUtil;
import com.qf.nanqiao.common.CustomException;
import com.qf.nanqiao.config.RedisConstant;
import com.qf.nanqiao.entity.R;
import com.qf.nanqiao.entity.User;
import com.qf.nanqiao.entity.UserDTO;
import com.qf.nanqiao.service.UserService;
import com.qf.nanqiao.utils.JwtUtils;
import com.qf.nanqiao.utils.RedisUtils;
import com.qf.nanqiao.utils.SmsUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@Api(tags = "用户接口")
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final SmsUtils smsUtils;

    @GetMapping("captcha")
    @ApiOperation("获取图像验证码")
    @ApiImplicitParam(name = "username", value = "用户名", required = true)
    public void captcha(String username, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha();
        String code = captcha.text();
        // 把验证码的文字放入redis中
        // key: nanqiao:captcha:username value: code
        RedisUtils.setString(RedisConstant.CAPTCHA + username, code, 300);
        response.setContentType("image/jpeg");
        captcha.out(response.getOutputStream());
    }

    @GetMapping("sms")
    @ApiOperation("发送短信")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true)
    public void sendSms(@RequestParam("phone") String phone) throws TencentCloudSDKException {
        Assert.hasLength(phone, "手机号不能为空");
        // 随机生成一个短信验证码
        String code = RandomUtil.randomNumbers(6);
        // 把这个验证码放入redis中
        RedisUtils.setString(RedisConstant.SMS_CODE + phone, code, 300);
        // 给这个手机号发送短信
        smsUtils.sendSms(new String[]{code}, new String[]{phone});
    }

    @PostMapping("login")
    @ApiOperation("用户登录")
    public R login(@RequestBody UserDTO userDTO, HttpServletResponse response) throws Exception {
        // 进行参数校验
        String username = userDTO.getUsername();
        String phone = userDTO.getPhone();
        if (!StringUtils.hasLength(username) && !StringUtils.hasLength(phone)) {
            throw new CustomException("用户名和手机号不能全为空");
        }
        if (StringUtils.hasLength(username)) {
            if (!StringUtils.hasLength(userDTO.getPassword())) {
                throw new CustomException("密码不能为空");
            }
        }
        if (StringUtils.hasLength(phone)) {
            if (!StringUtils.hasLength(userDTO.getCode())) {
                throw new CustomException("验证码不能为空");
            }
        }
        User user = userService.login(userDTO);
        if (Objects.isNull(user)) {
            return R.fail("登录失败");
        }
        // 用户登录成功后 基本信息id,name生成 jwttoken
        String token = JwtUtils.createToken(user.getId().toString(), user.getUsername());
        // 扔给前端 header/cookie
        response.addHeader("token", token);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return R.ok(user);
    }

}
