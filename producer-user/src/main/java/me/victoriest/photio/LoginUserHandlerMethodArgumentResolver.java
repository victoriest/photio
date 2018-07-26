package me.victoriest.photio;

import me.victoriest.photio.annotation.LoginUser;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.service.UserService;
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
 *
 * @author VictoriEST
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterType().isAssignableFrom(User.class)) &&
                parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) {
        // 获取用户id
        Object object = request.getAttribute(AuthorizationInterceptor.LOGIN_USER_ACCOUNT,
                RequestAttributes.SCOPE_REQUEST);

        User user;

        if (object != null) {
            user = userService.getByAccount((String) object).get();
            return user;
        }

        return null;
    }
}
