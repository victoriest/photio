package me.victoriest.photio.aop;

import me.victoriest.photio.annotation.LoginUser;
import me.victoriest.photio.cache.CacheKey;
import me.victoriest.photio.cache.RedisCache;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.message.Messages;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.service.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author VictoriEST
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    RedisCache redisCache;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterType().isAssignableFrom(User.class)) &&
                parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) {
        Object accessToken = request.getHeader("token");
        if(accessToken == null) {
            accessToken = request.getParameter("token");
        }
        if(accessToken == null) {
            throw new BusinessLogicException(Messages.TOKEN_REQUIRED);
        }

        String token = accessToken.toString();
        Map<String, Object> tokenInfo = null;
        String key = CacheKey.TOKEN_KEY_PREFIX + token;
        try {
            tokenInfo = (Map<String, Object>)redisCache.getValueOps().get(key);
        }
        catch (Exception e) {
            throw new BusinessLogicException(Messages.TOKEN_INVALID);
        }

        if(tokenInfo.containsKey("account")) {
            Long id = (Long) tokenInfo.get("id");
            User user = userFeignClient.getById(id).getData();
            return user;
        }

        throw new BusinessLogicException(Messages.TOKEN_INVALID);
    }
}
