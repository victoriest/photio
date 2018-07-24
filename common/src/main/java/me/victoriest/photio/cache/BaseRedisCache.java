package me.victoriest.photio.cache;

import me.victoriest.photio.cache.ops.*;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * @author VictoriEST
 * @date 2017/5/18
 * spring-boot-server
 */
public class BaseRedisCache {

    private RedisTemplate redisTemplate;

    protected VictoriestValueOperations valueOps;

    protected VictoriestHashOperations hashOps;

    protected VictoriestListOperations listOps;

    protected VictoriestSetOperations setOps;

    protected VictoriestZSetOperations zSetOps;

    protected static final String PERMANENT_PREFIX = "PERMANENT_";

    private long lifetime;

    public long getLifetime() {
        return lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    public VictoriestValueOperations getValueOps() {
        return valueOps;
    }

    public VictoriestHashOperations getHashOps() {
        return hashOps;
    }

    public VictoriestListOperations getListOps() {
        return listOps;
    }

    public VictoriestSetOperations getSetOps() {
        return setOps;
    }

    public VictoriestZSetOperations getzSetOps() {
        return zSetOps;
    }

    public BaseRedisCache(RedisTemplate redisTemplate, long lifetime) {
        this.redisTemplate = redisTemplate;
        this.lifetime = lifetime;
//        zSetOps = redisTemplate.opsForZSet();
        valueOps = new RedisValueOps(redisTemplate, lifetime);
        hashOps = new RedisHashOps(redisTemplate, lifetime);
        listOps = new RedisListOps(redisTemplate, lifetime);
        setOps = new RedisSetOps(redisTemplate, lifetime);
        zSetOps = new RedisZSetOps(redisTemplate, lifetime);


    }

//    public Consumer<String> expireFunction = (key) -> redisTemplate.expire(key, timeLimit, TimeUnit.SECONDS);

    public DataType type(String key) {
        return redisTemplate.type(key);

    }

    protected String getPermanentKey(String key) {
        return PERMANENT_PREFIX + key;
    }

}
