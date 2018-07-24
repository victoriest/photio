package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Set;

/**
 *
 * @author VictoriEST
 * @date 2017/5/23
 * Redis 有序集合和无序集合一样也是string类型元素的集合,且不允许重复的成员。
 * 不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
 * 有序集合的成员是唯一的,但分数(score)却可以重复。
 */
public class RedisZSetOps extends BaseOps implements VictoriestZSetOperations {

    private ZSetOperations<String, Object> ops;

    public RedisZSetOps(RedisTemplate<String, Object> template, long lifetime) {
        super(template, lifetime);
        this.ops = template.opsForZSet();
    }

    @Override
    public Boolean addWithExpire(String s, Object o, double v1, long lifetime) {
        Boolean r = ops.add(s, o, v1);
        setLifetime(s, lifetime);
        return r;
    }

    @Override
    public Boolean add(String s, Object o, double v1) {
        Boolean r = ops.add(s, o, v1);
        setLifetime(s);
        return r;
    }

    @Override
    public Long addWithExpire(String s, Set<ZSetOperations.TypedTuple<Object>> set, long lifetime) {
        Long r = ops.add(s, set);
        setLifetime(s, lifetime);
        return r;
    }

    @Override
    public Long add(String s, Set<ZSetOperations.TypedTuple<Object>> set) {
        Long r = ops.add(s, set);
        setLifetime(s);
        return r;
    }

    @Override
    public Long removeWithExpire(String s, long lifetime, Object... objects) {
        Long r = ops.remove(s, objects);
        setLifetime(s, lifetime);
        return r;
    }

    @Override
    public Long remove(String s, Object... objects) {
        Long r = ops.remove(s, objects);
        setLifetime(s);
        return r;
    }

    @Override
    public Double incrementScoreWithExpire(String s, Object o, double v1, long lifetime) {
        Double r = ops.incrementScore(s, o, v1);
        setLifetime(s, lifetime);
        return r;
    }

    @Override
    public Double incrementScore(String s, Object o, double v1) {
        Double r = ops.incrementScore(s, o, v1);
        setLifetime(s);
        return r;
    }

    @Override
    public Long rank(String s, Object o) {
        return ops.rank(s, o);
    }

    @Override
    public Long reverseRank(String s, Object o) {
        return ops.reverseRank(s, o);
    }

    @Override
    public Set<Object> range(String s, long l, long l1) {
        return ops.range(s, l, l1);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String s, long l, long l1) {
        return ops.rangeWithScores(s, l, l1);
    }

    @Override
    public Set<Object> rangeByScore(String s, double v, double v1) {
        return ops.rangeByScore(s, v, v1);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String s, double v, double v1) {
        return ops.rangeByScoreWithScores(s, v, v1);
    }

    @Override
    public Set<Object> rangeByScore(String s, double v, double v1, long l, long l1) {
        return ops.rangeByScore(s, v, v1, l, l1);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String s, double v, double v1, long l, long l1) {
        return ops.rangeByScoreWithScores(s, v, v1, l, l1);
    }

    @Override
    public Set<Object> reverseRange(String s, long l, long l1) {
        return ops.reverseRange(s, l, l1);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String s, long l, long l1) {
        return ops.reverseRangeWithScores(s, l, l1);
    }

    @Override
    public Set<Object> reverseRangeByScore(String s, double v, double v1) {
        return ops.reverseRangeByScore(s, v, v1);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String s, double v, double v1) {
        return ops.reverseRangeByScoreWithScores(s, v, v1);
    }

    @Override
    public Set<Object> reverseRangeByScore(String s, double v, double v1, long l, long l1) {
        return ops.reverseRangeByScore(s, v, v1, l, l1);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String s, double v, double v1, long l, long l1) {
        return ops.reverseRangeByScoreWithScores(s, v, v1, l, l1);
    }

    @Override
    public Long count(String s, double v, double v1) {
        return ops.count(s, v, v1);
    }

    @Override
    public Long size(String s) {
        return ops.size(s);
    }

    @Override
    public Long zCard(String s) {
        return ops.zCard(s);
    }

    @Override
    public Double score(String s, Object o) {
        return ops.score(s, o);
    }

    @Override
    public Long removeRangeWithExpire(String s, long l, long l1, long lifetime) {
        Long r = ops.removeRange(s, l, l1);
        setLifetime(s, lifetime);
        return r;
    }

    @Override
    public Long removeRange(String s, long l, long l1) {
        Long r = ops.removeRange(s, l, l1);
        setLifetime(s);
        return r;
    }

    @Override
    public Long removeRangeByScoreWithExpire(String s, double v, double v1, long lifetime) {
        Long r = ops.removeRangeByScore(s, v, v1);
        setLifetime(s, lifetime);
        return r;
    }

    @Override
    public Long removeRangeByScore(String s, double v, double v1) {
        Long r = ops.removeRangeByScore(s, v, v1);
        setLifetime(s);
        return r;
    }

    @Override
    public Long unionAndStoreWithExpire(String s, String k1, String k2, long lifetime) {
        Long r = ops.unionAndStore(s, k1, k2);
        setLifetime(k2, lifetime);
        return r;
    }

    @Override
    public Long unionAndStore(String s, String k1, String k2) {
        Long r = ops.unionAndStore(s, k1, k2);
        setLifetime(k2);
        return r;
    }

    @Override
    public Long unionAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime) {
        Long r = ops.unionAndStore(s, collection, k1);
        setLifetime(k1, lifetime);
        return r;
    }

    @Override
    public Long unionAndStore(String s, Collection<String> collection, String k1) {
        Long r = ops.unionAndStore(s, collection, k1);
        setLifetime(k1);
        return r;
    }

    @Override
    public Long intersectAndStoreWithExpire(String s, String k1, String k2, long lifetime) {
        Long r = ops.intersectAndStore(s, k1, k2);
        setLifetime(k2, lifetime);
        return r;
    }

    @Override
    public Long intersectAndStore(String s, String k1, String k2) {
        Long r = ops.intersectAndStore(s, k1, k2);
        setLifetime(k2);
        return r;
    }

    @Override
    public Long intersectAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime) {
        Long r = ops.intersectAndStore(s, collection, k1);
        setLifetime(k1, lifetime);
        return r;
    }

    @Override
    public Long intersectAndStore(String s, Collection<String> collection, String k1) {
        Long r = ops.intersectAndStore(s, collection, k1);
        setLifetime(k1);
        return r;
    }

    @Override
    public Cursor<ZSetOperations.TypedTuple<Object>> scan(String s, ScanOptions scanOptions) {
        return ops.scan(s, scanOptions);
    }

    @Override
    public Set<Object> rangeByLex(String s, RedisZSetCommands.Range range) {
        throw new NotImplementedException();
    }

    @Override
    public Set<Object> rangeByLex(String s, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
        throw new NotImplementedException();
    }

    @Override
    public RedisOperations<String, Object> getOperations() {
        return ops.getOperations();
    }

}
