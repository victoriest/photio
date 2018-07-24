package me.victoriest.photio.cache;

import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * @author VictoriEST
 * @date 2017/5/22
 * spring-boot-server
 */
public class PermanentRedisCache extends BaseRedisCache {

    public PermanentRedisCache(RedisTemplate redisTemplate) {
        super(redisTemplate, -1);
    }
}
