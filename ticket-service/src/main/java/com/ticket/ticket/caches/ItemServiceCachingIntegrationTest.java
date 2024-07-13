package com.ticket.ticket.caches;




import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import com.ticket.ticket.DTOs.TourDTO;

@Import({ CacheConfig.class, TourDTO.class})
@EnableCaching
@ImportAutoConfiguration(classes = { 
  CacheAutoConfiguration.class, 
  RedisAutoConfiguration.class 
})

public class ItemServiceCachingIntegrationTest {
    
}
