package me.victoriest.photio.cache;

import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * @author VictoriEST
 * @date 2017/5/22
 * spring-boot-server
 */
public class RedisCache extends BaseRedisCache {

    public RedisCache(RedisTemplate redisTemplate, long timeLimit) {
        super(redisTemplate, timeLimit);
    }

}
