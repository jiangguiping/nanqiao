package com.qf.nanqiao.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * <p>
 * 封装一些常见的key-value存取的方法，包括设置过期时间等方法
 * 因为是工具类，建议给外界提供静态方法
 *
 * @author zed
 * @date 2022/11/25
 */
@Component
public class RedisUtils {

    private static RedisUtils INSTANCE = new RedisUtils();
    private static final String PREFIX = "nanqiao:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    // Spring Bean的生命周期
    @PostConstruct
    public void init() {
        INSTANCE.redisTemplate = redisTemplate;
        INSTANCE.stringRedisTemplate = stringRedisTemplate;
    }

    // 可以提供过期时间和时间单位的方法
    public static void setString(String key, String value, long time, TimeUnit timeUnit) {
        INSTANCE.stringRedisTemplate.opsForValue().set(getKey(key), value, time, timeUnit);
    }

    // 可以提供过期时间
    public static void setString(String key, String value, long time) {
        setString(key, value, time, TimeUnit.SECONDS);
    }

    // 不设置过期时间的方法
    public static void setString(String key, String value) {
        INSTANCE.stringRedisTemplate.opsForValue().set(getKey(key), value);
    }

    // 根据key获取string类型数据的方法
    public static String getString(String key) {
        return INSTANCE.stringRedisTemplate.opsForValue().get(getKey(key));
    }

    private static String getKey(String key) {
        return PREFIX + key;
    }

    public static void setObject(String key, Object value, long time, TimeUnit timeUnit) {
        INSTANCE.redisTemplate.opsForValue().set(getKey(key), value, time, timeUnit);
    }

    public static void setObject(String key, Object value, long time) {
        setObject(key, value, time, TimeUnit.SECONDS);
    }

    public static void setObject(String key, Object value) {
        INSTANCE.redisTemplate.opsForValue().set(getKey(key), value);
    }

    public static Object getObject(String key) {
        return INSTANCE.redisTemplate.opsForValue().get(getKey(key));
    }

    // 获取过期时间的方法
    public static long ttl(String key) {
        return INSTANCE.stringRedisTemplate.getExpire(getKey(key));
    }
}
