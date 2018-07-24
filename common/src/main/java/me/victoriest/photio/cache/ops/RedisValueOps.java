package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author VictoriEST
 * @date 2017/5/22
 * spring-boot-server
 */
public class RedisValueOps extends BaseOps implements VictoriestValueOperations {

    private ValueOperations<String, Object> ops;

    public RedisValueOps(RedisTemplate<String, Object> template, long lifetime) {
        super(template, lifetime);
        this.ops = template.opsForValue();
    }


    @Override
    public void setWithExpire(String s, Object o, long lifetime) {
        ops.set(s, o);
//        ops.set(s, o, lifetime, TimeUnit.SECONDS);
        setLifetime(s, lifetime);
    }

    @Override
    public void set(String s, Object o) {
        ops.set(s, o);
//        ops.set(s, o, lifetime, TimeUnit.SECONDS);
        setLifetime(s);
    }

    @Override
    public void set(String s, Object o, long l, TimeUnit timeUnit) {
        ops.set(s, o, lifetime, TimeUnit.SECONDS);
    }

    @Override
    public Boolean setIfAbsentWithExpire(String s, Object o, long lifetime) {
        Boolean result = ops.setIfAbsent(s, o);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Boolean setIfAbsent(String s, Object o) {
        Boolean result = ops.setIfAbsent(s, o);
        setLifetime(s);
        return result;
    }

    public void multiSet(Map<? extends String, ?> map) {
//        ops.multiSet(map);
//        // TODO VICTORIEST
//        return template.expire(s, second, TimeUnit.SECONDS);
        throw new NotImplementedException();
    }

    public Boolean multiSetIfAbsent(Map<? extends String, ?> map) {
        throw new NotImplementedException();
    }

    @Override
    public Object get(Object o) {
        return ops.get(o);
    }

    @Override
    public Object getAndSetWithExpire(String s, Object o, long lifetime) {
        Object result = ops.getAndSet(s, o);
        setLifetime(s, lifetime);
        return result;
    }

    public Object getAndSet(String s, Object o) {
        Object result = ops.getAndSet(s, o);
        setLifetime(s);
        return result;
    }

    @Override
    public List<Object> multiGet(Collection<String> collection) {
        return ops.multiGet(collection);
    }

    @Override
    public Long incrementWithExpire(String s, long l, long lifetime) {
        Long result = ops.increment(s, l);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long increment(String s, long l) {
        Long result = ops.increment(s, l);
        setLifetime(s);
        return result;
    }

    @Override
    public Double incrementWithExpire(String s, double v, long lifetime) {
        double result = ops.increment(s, v);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Double increment(String s, double v) {
        double result = ops.increment(s, v);
        setLifetime(s);
        return result;
    }

    @Override
    public Integer appendWithExpire(String s, String s2, long lifetime) {
        Integer result = ops.append(s, s2);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Integer append(String s, String s2) {
        Integer result = ops.append(s, s2);
        setLifetime(s);
        return result;
    }

    @Override
    public String get(String s, long l, long l1) {
        return ops.get(s, l, l1);
    }

    @Override
    public void set(String s, Object o, long l) {
        ops.set(s, o, l);
    }

    @Override
    public Long size(String s) {
        return ops.size(s);
    }

    @Override
    public Boolean setBitWithExpire(String s, long l, boolean b, long lifetime) {
        Boolean result = ops.setBit(s, l, b);
        setLifetime(s, lifetime);
        return result;
    }

    public Boolean setBit(String s, long l, boolean b) {
        Boolean result = ops.setBit(s, l, b);
        setLifetime(s);
        return result;
    }

    @Override
    public Boolean getBit(String s, long l) {
        return ops.getBit(s, l);
    }

    @Override
    public RedisOperations<String, Object> getOperations() {
        return ops.getOperations();
    }

}
