package com.qf.nanqiao.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * jwt工具类
 *
 * @author zed
 * @date 2022/12/06
 */
public class JwtUtils {

    // 加盐
    public static final String SALT = "nanqiao";

    /**
     * 创建令牌
     *
     * @param id   id
     * @param name 名字
     * @return {@link String}
     */
    public static String createToken(String id, String name) {
        // long now = System.currentTimeMillis();  //当前时间
        // long exp = now + 1000 * 60 * 30; //过期时间为30分钟
        //创建JWT对象
        JwtBuilder builder = Jwts.builder().setId(id)//设置负载内容
                .setSubject(name)
                .setIssuedAt(new Date())//设置签发时间
                .signWith(SignatureAlgorithm.HS256, SALT);//设置签名秘钥
        //构建token
        return builder.compact();
    }


    /**
     * 解析令牌
     *
     * @param token 令牌
     * @return {@link String}
     */
    public static String parseToken(String token) {
        //解析Token，生成Claims对象，Token中存放的用户信息解析到了claims对象中
        Claims claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
        return claims.getId();
    }
}
