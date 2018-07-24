package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 * @author VictoriEST
 * @date 2017/5/23
 * spring-boot-server
 */
public class RedisSetOps extends BaseOps implements VictoriestSetOperations {

    private SetOperations<String, Object> ops;

    public RedisSetOps(RedisTemplate<String, Object> template, long lifetime) {
        super(template, lifetime);
        this.ops = template.opsForSet();
    }

    @Override
    public Long addWithExpire(String s, long lifetime, Object... objects) {
        Long result = ops.add(s, objects);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long add(String s, Object... objects) {
        Long result = ops.add(s, objects);
        setLifetime(s);
        return result;
    }

    @Override
    public Long removeWithExpire(String s, long lifetime, Object... objects) {
        Long result = ops.remove(s, objects);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Long remove(String s, Object... objects) {
        Long result = ops.remove(s, objects);
        setLifetime(s);
        return result;
    }

    @Override
    public Object popWithExpire(String s, long lifetime) {
        Object result = ops.pop(s);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Object pop(String s) {
        Object result = ops.pop(s);
        setLifetime(s);
        return result;
    }

    @Override
    public List<Object> pop(String s, long l) {
        // TODO VICTORIEST : IMPLEMENT IT
        return null;
    }

    @Override
    public Boolean moveWithExpire(String s, Object o, String k1, long lifetime) {
        Boolean result = ops.move(s, o, k1);
        setLifetime(s, lifetime);
        return result;
    }

    @Override
    public Boolean move(String s, Object o, String k1) {
        Boolean result = ops.move(s, o, k1);
        setLifetime(s);
        return result;
    }

    @Override
    public Long size(String s) {
        return ops.size(s);
    }

    @Override
    public Boolean isMember(String s, Object o) {
        return ops.isMember(s, o);
    }

    @Override
    public Set<Object> intersect(String s, String k1) {
        return ops.intersect(s, k1);
    }

    @Override
    public Set<Object> intersect(String s, Collection<String> collection) {
        return ops.intersect(s, collection);
    }

    @Override
    public Long intersectAndStoreWithExpire(String s, String k1, String k2, long lifetime) {
        Long result = ops.intersectAndStore(s, k1, k2);
        setLifetime(k2, lifetime);
        return result;
    }

    @Override
    public Long intersectAndStore(String s, String k1, String k2) {
        Long result = ops.intersectAndStore(s, k1, k2);
        setLifetime(k2);
        return result;
    }

    @Override
    public Long intersectAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime) {
        Long result = ops.intersectAndStore(s, collection, k1);
        setLifetime(k1, lifetime);
        return result;
    }

    @Override
    public Long intersectAndStore(String s, Collection<String> collection, String k1) {
        Long result = ops.intersectAndStore(s, collection, k1);
        setLifetime(k1);
        return result;
    }

    @Override
    public Set<Object> union(String s, String k1) {
        return ops.union(s, k1);
    }

    @Override
    public Set<Object> union(String s, Collection<String> collection) {
        return ops.union(s, collection);
    }

    @Override
    public Long unionAndStoreWithExpire(String s, String k1, String k2, long lifetime) {
        Long result = ops.unionAndStore(s, k1, k2);
        setLifetime(k2, lifetime);
        return result;
    }

    @Override
    public Long unionAndStore(String s, String k1, String k2) {
        Long result = ops.unionAndStore(s, k1, k2);
        setLifetime(k2);
        return result;
    }

    @Override
    public Long unionAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime) {
        Long result = ops.unionAndStore(s, collection, k1);
        setLifetime(k1, lifetime);
        return result;
    }

    @Override
    public Long unionAndStore(String s, Collection<String> collection, String k1) {
        Long result = ops.unionAndStore(s, collection, k1);
        setLifetime(k1);
        return result;
    }

    @Override
    public Set<Object> difference(String s, String k1) {
        return ops.difference(s, k1);
    }

    @Override
    public Set<Object> difference(String s, Collection<String> collection) {
        return ops.difference(s, collection);
    }

    @Override
    public Long differenceAndStoreWithExpire(String s, String k1, String k2, long lifetime) {
        Long result = ops.differenceAndStore(s, k1, k2);
        setLifetime(k2, lifetime);
        return result;
    }

    @Override
    public Long differenceAndStore(String s, String k1, String k2) {
        Long result = ops.differenceAndStore(s, k1, k2);
        setLifetime(k2);
        return result;
    }

    @Override
    public Long differenceAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime) {
        Long result = ops.differenceAndStore(s, collection, k1);
        setLifetime(k1, lifetime);
        return result;
    }

    @Override
    public Long differenceAndStore(String s, Collection<String> collection, String k1) {
        Long result = ops.differenceAndStore(s, collection, k1);
        setLifetime(k1);
        return result;
    }

    @Override
    public Set<Object> members(String s) {
        return ops.members(s);
    }

    @Override
    public Object randomMember(String s) {
        return ops.randomMember(s);
    }

    @Override
    public Set<Object> distinctRandomMembers(String s, long l) {
        return ops.distinctRandomMembers(s, l);
    }

    @Override
    public List<Object> randomMembers(String s, long l) {
        return ops.randomMembers(s, l);
    }

    @Override
    public Cursor<Object> scan(String s, ScanOptions scanOptions) {
        return ops.scan(s, scanOptions);
    }

    @Override
    public RedisOperations<String, Object> getOperations() {
        return ops.getOperations();
    }
}
