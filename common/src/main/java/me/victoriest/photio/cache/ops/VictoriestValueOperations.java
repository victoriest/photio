package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.ValueOperations;

/**
 *
 * @author VictoriEST
 * @date 2017/6/22
 * spring-boot-server
 */
public interface VictoriestValueOperations extends ValueOperations<String, Object> {

    void setWithExpire(String s, Object o, long lifetime);

    Boolean setIfAbsentWithExpire(String s, Object o, long lifetime);

    Object getAndSetWithExpire(String s, Object o, long lifetime);

    Long incrementWithExpire(String s, long l, long lifetime);

    Double incrementWithExpire(String s, double v, long lifetime);

    Integer appendWithExpire(String s, String s2, long lifetime);

    Boolean setBitWithExpire(String s, long l, boolean b, long lifetime);

}
