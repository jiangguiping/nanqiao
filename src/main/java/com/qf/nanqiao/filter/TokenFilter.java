package com.qf.nanqiao.filter;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Sets;
import com.qf.nanqiao.entity.User;
import com.qf.nanqiao.utils.JwtUtils;
import com.qf.nanqiao.utils.RedisUtils;
import com.qf.nanqiao.utils.ThreadLocalUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Token过滤器
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    // 需要放行的请求路径：验证码 登录 注册 注销
    private static final Set<String> ALLOW_PATH =
            Collections.unmodifiableSet(Sets.newHashSet("/user/captcha", "/user/login", "/user/register", "/user/logout","/file/upload","/doc.html"));

    public static final String TOKEN = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求的地址
        String uri = request.getRequestURI();
        if (ALLOW_PATH.contains(uri)) {
            // 放行请求
            filterChain.doFilter(request, response);
        } else {
            // 从请求中获取token
            String token = getToken(request);
            // 如果token是空的，拦截请求返回没有权限
            if (StringUtils.hasText(token)) {
                // 解析用户ID
                String uid = JwtUtils.parseToken(token);
                // 从redis取出来用户放入ThreadLocal
                User user = (User) RedisUtils.getObject("user:" + uid);
                ThreadLocalUtils.put("user", user);
                // 放行请求
                filterChain.doFilter(request, response);
            } else {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "*");
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> map = new HashMap<>();
                map.put("code", 401);
                map.put("msg", "没有权限访问");
                response.getWriter().write(JSON.toJSONString(map));
            }
        }
    }


    /**
     * 从三个地方中获取token
     *
     * @param request 请求
     * @return {@link String}
     */
    private String getToken(HttpServletRequest request) {
        // token前端有可能是从header中传递也有可能是从cookie中传入也有可能从参数param中传传递
        String token = request.getHeader(TOKEN);
        if (!StringUtils.hasText(token)) {
            Cookie[] cookies = request.getCookies();
            if (Objects.nonNull(cookies) && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (Objects.equals(cookie.getName(), TOKEN)) {
                        token = cookie.getValue();
                    }
                }
                // for循环结束了 如果token还是空 说明cookie中也没有取到值，在从参数param中取值
                if (!StringUtils.hasText(token)) {
                    token = request.getParameter(TOKEN);
                }
            }
        }
        return token;
    }
}