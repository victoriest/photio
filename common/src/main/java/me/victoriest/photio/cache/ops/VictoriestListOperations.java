package me.victoriest.photio.cache.ops;

import org.springframework.data.redis.core.ListOperations;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author VictoriEST
 * @date 2017/6/22
 * spring-boot-server
 */
public interface VictoriestListOperations extends ListOperations<String, Object> {

    void trimWithExpire(String s, long l, long l1, long lifetime);

    Long leftPushWithExpire(String s, Object o, long lifetime);

    Long leftPushAllWithExpire(String s, long lifetime, Object... objects);

    Long leftPushAllWithExpire(String s, Collection<Object> collection, long lifetime);

    Long leftPushIfPresentWithExpire(String s, Object o, long lifetime);

    Long leftPushWithExpire(String s, Object o, Object v1, long lifetime);

    Long rightPushWithExpire(String s, Object o, long lifetime);

    Long rightPushAllWithExpire(String s, long lifetime, Object... objects);

    Long rightPushAllWithExpire(String s, Collection<Object> collection, long lifetime);

    Long rightPushIfPresentWithExpire(String s, Object o, long lifetime);

    Long rightPushWithExpire(String s, Object o, Object v1, long lifetime);

    void setWithExpire(String s, long l, Object o, long lifetime);

    Long removeWithExpire(String s, long l, Object o, long lifetime);

    Object rightPopAndLeftPushWithExpire(String s, String k1, long lifetime);

    Object rightPopAndLeftPushWithExpire(String s, String k1, long l, TimeUnit timeUnit, long lifetime);
}
