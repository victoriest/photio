package me.victoriest.photio.annotation;

import java.lang.annotation.*;

/**
 *  忽略Token验证
 * Created by VictoriEST on 2018/3/28.
 * spring-cloud-step-by-step
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuthorize {
}
