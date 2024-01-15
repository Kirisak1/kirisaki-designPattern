package com.kirisaki.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 */
@Configuration
public class RedisConfig {
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //设置连接
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置自定义序列化方式
        setSerializeConfig(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    private void setSerializeConfig(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory redisConnectionFactory) {
        //普通 Key 和 HashKey 采用 StringRedisSerializer 进行序列化
        StringRedisSerializer redisKeySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setHashKeySerializer(redisKeySerializer);

        //解决查询缓存转换异常的问题
        Jackson2JsonRedisSerializer<?> redisValueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        redisValueSerializer.setObjectMapper(objectMapper);

        //普通 Value 与 Hash 类型的 Value 采用 jackson 方式进行序列化
        redisTemplate.setValueSerializer(redisValueSerializer);
        redisTemplate.setHashValueSerializer(redisValueSerializer);
        redisTemplate.afterPropertiesSet();
    }
}
