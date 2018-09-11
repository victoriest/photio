package me.victoriest.photio;

import me.victoriest.photio.annotation.LoginUser;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.message.Messages;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.rsa.TokenService;
import me.victoriest.photio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;
import java.util.Optional;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author VictoriEST
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

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

        Optional<Map<String, Object>> optTokenInfo = tokenService.getTokenInfo(token);
        if (!optTokenInfo.isPresent()) {
            // token 无效或已过期
            throw new BusinessLogicException(Messages.TOKEN_INVALID);
        }
        Map<String, Object> tokenInfo = optTokenInfo.get();

        if(tokenInfo.containsKey("account")) {
            // 判断账号是否被踢下线
            String account = tokenInfo.get("account").toString();
            return userService.getByAccount(account).get();
        }

        throw new BusinessLogicException(Messages.TOKEN_INVALID);
    }
}
