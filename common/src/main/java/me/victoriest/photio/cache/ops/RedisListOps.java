package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author VictoriEST
 * @date 2017/5/23
 * spring-boot-server
 */
public class RedisListOps extends BaseOps implements VictoriestListOperations {

    private ListOperations<String, Object> ops;

    public RedisListOps(RedisTemplate<String, Object> template, long lifetime) {
        super(template, lifetime);
        this.ops = template.opsForList();
    }

    @Override
    public List<Object> range(String s, long l, long l1) {
        return ops.range(s, l, l1);
    }

    @Override
    public void trimWithExpire(String s, long l, long l1, long lifetime) {
        ops.trim(s, l, l1);
        setLifetime(s, lifetime);
    }

    @Override
    public void trim(String s, long l, long l1) {
        ops.trim(s, l, l1);
        setLifetime(s);
    }

    @Override
    public Long size(String s) {
        return ops.size(s);
    }


    @Override
    public Long leftPushWithExpire(String s, Object o, long lifetime) {
        Long result = ops.leftPush(s, o);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long leftPush(String s, Object o) {
        Long result = ops.leftPush(s, o);
        setLifetime(s);
        return result;
    }

    @Override
    public Long leftPushAllWithExpire(String s, long lifetime, Object... objects) {
        Long result = ops.leftPushAll(s, objects);
        setLifetime(s, lifetime);
        return result;
    }


    @Override
    public Long leftPushAll(String s, Object... objects) {
        Long result = ops.leftPushAll(s, objects);
        setLifetime(s);
        return result;
    }

    @Override
    public Long leftPushAllWithExpire(String s, Collection<Object> collection, long lifetime) {
        Long result = ops.leftPushAll(s, collection);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long leftPushAll(String s, Collection<Object> collection) {
        Long result = ops.leftPushAll(s, collection);
        setLifetime(s);
        return result;
    }

    @Override
    public Long leftPushIfPresentWithExpire(String s, Object o, long lifetime) {
        Long result = ops.leftPushIfPresent(s, o);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long leftPushIfPresent(String s, Object o) {
        Long result = ops.leftPushIfPresent(s, o);
        setLifetime(s);
        return result;
    }


    @Override
    public Long leftPushWithExpire(String s, Object o, Object v1, long lifetime) {
        Long result = ops.leftPush(s, o, v1);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long leftPush(String s, Object o, Object v1) {
        Long result = ops.leftPush(s, o, v1);
        setLifetime(s);
        return result;
    }

    @Override
    public Long rightPushWithExpire(String s, Object o, long lifetime) {
        Long result = ops.rightPush(s, o);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long rightPush(String s, Object o) {
        Long result = ops.rightPush(s, o);
        setLifetime(s);
        return result;
    }

    @Override
    public Long rightPushAllWithExpire(String s, long lifetime, Object... objects) {
        Long result = ops.rightPushAll(s, objects);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long rightPushAll(String s, Object... objects) {
        Long result = ops.rightPushAll(s, objects);
        setLifetime(s);
        return result;
    }

    @Override
    public Long rightPushAllWithExpire(String s, Collection<Object> collection, long lifetime) {
        Long result = ops.rightPushAll(s, collection);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long rightPushAll(String s, Collection<Object> collection) {
        Long result = ops.rightPushAll(s, collection);
        setLifetime(s);
        return result;
    }

    @Override
    public Long rightPushIfPresentWithExpire(String s, Object o, long lifetime) {
        Long result = ops.rightPushIfPresent(s, o);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long rightPushIfPresent(String s, Object o) {
        Long result = ops.rightPushIfPresent(s, o);
        setLifetime(s);
        return result;
    }

    @Override
    public Long rightPushWithExpire(String s, Object o, Object v1, long lifetime) {
        Long result = ops.rightPush(s, o, v1);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long rightPush(String s, Object o, Object v1) {
        Long result = ops.rightPush(s, o, v1);
        setLifetime(s);
        return result;
    }

    @Override
    public void setWithExpire(String s, long l, Object o, long lifetime) {
        ops.set(s, l, o);
        setLifetime(s, lifetime);
    }

    @Override
    public void set(String s, long l, Object o) {
        ops.set(s, l, o);
        setLifetime(s);
    }

    @Override
    public Long removeWithExpire(String s, long l, Object o, long lifetime) {
        Long result = ops.remove(s, l, o);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long remove(String s, long l, Object o) {
        Long result = ops.remove(s, l, o);
        setLifetime(s);
        return result;
    }

    @Override
    public Object index(String s, long l) {
        return ops.index(s, l);
    }

    @Override
    public Object leftPop(String s) {
        return ops.leftPop(s);
    }

    @Override
    public Object leftPop(String s, long l, TimeUnit timeUnit) {
        return ops.leftPop(s, l, timeUnit);
    }

    @Override
    public Object rightPop(String s) {
        return ops.rightPop(s);
    }

    @Override
    public Object rightPop(String s, long l, TimeUnit timeUnit) {
        return ops.rightPop(s, l, timeUnit);
    }

    @Override
    public Object rightPopAndLeftPushWithExpire(String s, String k1, long lifetime) {
        Object result = ops.rightPopAndLeftPush(s, k1);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Object rightPopAndLeftPush(String s, String k1) {
        return ops.rightPopAndLeftPush(s, k1);
    }

    @Override
    public Object rightPopAndLeftPushWithExpire(String s, String k1, long l, TimeUnit timeUnit, long lifetime) {
        Object result = ops.rightPopAndLeftPush(s, k1, l, timeUnit);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Object rightPopAndLeftPush(String s, String k1, long l, TimeUnit timeUnit) {
        return ops.rightPopAndLeftPush(s, k1, l, timeUnit);
    }

    @Override
    public RedisOperations<String, Object> getOperations() {
        return ops.getOperations();
    }



























}
