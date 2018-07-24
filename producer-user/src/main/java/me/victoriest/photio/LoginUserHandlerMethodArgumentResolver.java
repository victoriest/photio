package me.victoriest.springcloud;

import me.victoriest.annotation.LoginUser;
import me.victoriest.dao.mapper.source.UserMapper;
import me.victoriest.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterType().isAssignableFrom(User.class)) &&
                parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        // 获取用户id
        Object object = request.getAttribute(AuthorizationInterceptor.LOGIN_USER_ID, RequestAttributes.SCOPE_REQUEST);

        User user;

        if (object != null) {
            user = userMapper.selectByPrimaryKey((Integer) object);
            return user;
        }

        return null;
    }
}
