package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by VictoriEST on 2017/5/23.
 * spring-boot-server
 */
class BaseOps {

    protected RedisTemplate<String, Object> template;

    protected long lifetime;

    BaseOps(RedisTemplate<String, Object> template, long lifetime) {
        this.template = template;
        this.lifetime = lifetime;
    }

    Boolean setLifetime(String key) {
        if(lifetime <= 0) {
            return template.persist(key);
        }
        return template.expire(key, lifetime, TimeUnit.SECONDS);
    }

    Boolean setLifetime(String key, long lt) {
        if(lt <= 0) {
            return template.persist(key);
        }
        return template.expire(key, lt, TimeUnit.SECONDS);
    }

}
