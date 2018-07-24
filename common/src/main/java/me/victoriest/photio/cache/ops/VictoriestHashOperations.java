package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.HashOperations;

import java.util.Map;

/**
 *
 * @author VictoriEST
 * @date 2017/6/22
 * spring-boot-server
 */
public interface VictoriestHashOperations extends HashOperations<String, Object, Object> {

    Long incrementWithExpire(String s, Object s2, long l, long lifetime);

    Double incrementWithExpire(String s, Object s2, double v, long lifetime);

    void putAllWithExpire(String s, Map<? extends Object, ?> map, long lifetime);

    void putWithExpire(String s, Object s2, Object o, long lifetime);

    Boolean putIfAbsentWithExpire(String s, Object s2, Object o, long lifetime);

}
