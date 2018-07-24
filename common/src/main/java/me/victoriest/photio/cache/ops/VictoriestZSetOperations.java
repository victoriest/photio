package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.Set;

/**
 *
 * @author VictoriEST
 * @date 2017/6/22
 * spring-boot-server
 */
public interface VictoriestZSetOperations extends ZSetOperations<String, Object> {

    Boolean addWithExpire(String s, Object o, double v1, long lifetime);

    Long addWithExpire(String s, Set<TypedTuple<Object>> set, long lifetime);

    Long removeWithExpire(String s, long lifetime, Object... objects);

    Double incrementScoreWithExpire(String s, Object o, double v1, long lifetime);

    Long removeRangeWithExpire(String s, long l, long l1, long lifetime);

    Long removeRangeByScoreWithExpire(String s, double v, double v1, long lifetime);

    Long unionAndStoreWithExpire(String s, String k1, String k2, long lifetime);

    Long unionAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime);

    Long intersectAndStoreWithExpire(String s, String k1, String k2, long lifetime);

    Long intersectAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime);

}
