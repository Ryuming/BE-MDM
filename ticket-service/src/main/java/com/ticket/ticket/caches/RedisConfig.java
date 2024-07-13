package com.ticket.ticket.caches;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ticket.ticket.entities.CachedResponse;
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, CachedResponse<?>> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, CachedResponse<?>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use Jackson JSON Serializer for values
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Use String Serializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}