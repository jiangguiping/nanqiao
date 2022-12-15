package com.qf.nanqiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis配置类
 * <p>
 * 作用：配置下RedisTemplate中value值的序列化方式
 *
 * @author zed
 * @date 2022/11/25
 */
@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 修改RedisTemplate中key的序列化方式 string
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // 修改RedisTemplate中value的序列化方式 json
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.json());
        // 设置下factory
        template.setConnectionFactory(factory);
        return template;
    }
}
