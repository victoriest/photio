package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author VictoriEST
 * @date 2017/5/22
 * spring-boot-server
 */
public class RedisHashOps extends BaseOps implements VictoriestHashOperations {

    private HashOperations<String, Object, Object> ops;

    public RedisHashOps(RedisTemplate<String, Object> template, long lifetime) {
        super(template, lifetime);
        this.ops = template.opsForHash();
    }

    @Override
    public Long delete(String s, Object... objects) {
        return ops.delete(s, objects);
    }

    @Override
    public Boolean hasKey(String s, Object o) {
        return ops.hasKey(s, o);
    }

    @Override
    public Object get(String s, Object o) {
        return ops.get(s, o);
    }

    @Override
    public List<Object> multiGet(String s, Collection<Object> collection) {
        return ops.multiGet(s, collection);
    }

    @Override
    public Long incrementWithExpire(String s, Object s2, long l, long lifetime) {
        Long result = ops.increment(s, s2, l);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long increment(String s, Object s2, long l) {
        return incrementWithExpire(s, s2, l, this.lifetime);
    }

    @Override
    public Double incrementWithExpire(String s, Object s2, double v, long lifetime) {
        Double result = ops.increment(s, s2, v);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public void putAllWithExpire(String s, Map<?, ?> map, long lifetime) {
        ops.putAll(s, map);
        setLifetime(s, lifetime);
    }

    @Override
    public Double increment(String s, Object s2, double v) {
        return incrementWithExpire(s, s2, v, this.lifetime);
    }

    @Override
    public Set<Object> keys(String s) {
        return ops.keys(s);
    }

    @Override
    public Long size(String s) {
        return ops.size(s);
    }



    @Override
    public void putAll(String s, Map<?, ?> map) {
        ops.putAll(s, map);
        setLifetime(s);
    }

    @Override
    public void putWithExpire(String s, Object s2, Object o, long lifetime) {
        ops.put(s, s2, o);
        setLifetime(s, lifetime);
    }

    @Override
    public void put(String s, Object s2, Object o) {
        ops.put(s, s2, o);
        setLifetime(s);
    }

    @Override
    public Boolean putIfAbsentWithExpire(String s, Object s2, Object o, long lifetime) {
        Boolean result = ops.putIfAbsent(s, s2, o);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Boolean putIfAbsent(String s, Object s2, Object o) {
        Boolean result = ops.putIfAbsent(s, s2, o);
        setLifetime(s);
        return result;
    }

    @Override
    public List<Object> values(String s) {
        return ops.values(s);
    }

    @Override
    public Map<Object, Object> entries(String s) {
        return ops.entries(s);
    }

    @Override
    public Cursor<Map.Entry<Object, Object>> scan(String s, ScanOptions scanOptions) {
        return ops.scan(s, scanOptions);
    }

    @Override
    public RedisOperations<String, ?> getOperations() {
        return ops.getOperations();
    }

}
