package com.ticket.ticket.caches;

import java.beans.JavaBean;
import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;



public class RedisCacheConfg {

@Bean
JedisConnectionFactory jedisConnectionFactory()
{
  return new JedisConnectionFactory();
}

    @Bean
public RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(Duration.ofMinutes(60))
      .disableCachingNullValues()
      .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
}

@Bean
public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return (builder) -> builder
      .withCacheConfiguration("itemCache",
        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
      .withCacheConfiguration("customerCache",
        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
}

// @Bean
// public RedisTemplate<String, Object> redisTemplate()
// {
//   final RedisTemplate<String, Object> template = new RedisTemplate<>();
// }

}
