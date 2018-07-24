package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.SetOperations;

import java.util.Collection;

/**
 *
 * @author VictoriEST
 * @date 2017/6/22
 * spring-boot-server
 */
public interface VictoriestSetOperations extends SetOperations<String, Object> {

    Long addWithExpire(String s, long lifetime, Object... objects);

    Long removeWithExpire(String s, long lifetime, Object... objects);

    Object popWithExpire(String s, long lifetime);

    Boolean moveWithExpire(String s, Object o, String k1, long lifetime);

    Long intersectAndStoreWithExpire(String s, String k1, String k2, long lifetime);

    Long intersectAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime);

    Long unionAndStoreWithExpire(String s, String k1, String k2, long lifetime);

    Long unionAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime);

    Long differenceAndStoreWithExpire(String s, String k1, String k2, long lifetime);

    Long differenceAndStoreWithExpire(String s, Collection<String> collection, String k1, long lifetime);
}
