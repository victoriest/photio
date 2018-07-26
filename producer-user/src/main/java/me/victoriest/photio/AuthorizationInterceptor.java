package me.victoriest.photio;

import io.jsonwebtoken.Claims;
import me.victoriest.photio.annotation.IgnoreAuthorize;
import me.victoriest.photio.config.TokenConfig;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.message.Messages;
import me.victoriest.photio.rsa.TokenService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author VictoriEST
 * @date 2018/3/28
 * spring-cloud-step-by-step
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String LOGIN_USER_ACCOUNT = "LOGIN_USER_ACCOUNT";

    @Value("${token.enable}")
    private boolean enableToken;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(!enableToken) {
            return true;
        }

        IgnoreAuthorize ignoreAuthorize;
        if (handler instanceof HandlerMethod) {
            ignoreAuthorize = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuthorize.class);
        } else {
            return true;
        }

        // 如果有@IgnoreAuth注解，则不验证token
        if (ignoreAuthorize != null) {
            return true;
        }

        // 从header中获取token
        String token = request.getHeader("token");
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        // token 为空
        if (StringUtils.isBlank(token)) {
            logger.info("token is empty, the requested url :" + request.getRequestURL());
            throw new BusinessLogicException(Messages.TOKEN_REQUIRED);
        }

        Claims claims;
        try {
            claims = JwtHelper.parseToken(token, "23u90dsfjoia;9u0wrejmtiorejmwpotgju9043[iu530jm4rfejdsigo'jmdsiug9ir0ew[p5uite[0t");
        }
        catch (Exception e) {
            // token 无效或已过期
            throw new BusinessLogicException(Messages.TOKEN_INVALID);
        }
        if(claims == null) {
            throw new BusinessLogicException(Messages.TOKEN_INVALID);
        }

        // 从redis中获取token信息
        Optional<Map<String, Object>> tokenInfo = tokenService.getTokenInfo(token);
        if (!tokenInfo.isPresent()) {
            /*************************************************************
             *  该代码是为了不影响dev模式下单元测试token值问题
             */
            if (tokenConfig.isTokenSimulateEnabled()) {
                Integer unitTestTokenValue = tokenConfig.getUnitTestTokenValue();
                if (unitTestTokenValue != null && String.valueOf(unitTestTokenValue).equals(token)) {
                    // 设置userId到request里，后续根据userId获取用户信息
                    request.setAttribute(LOGIN_USER_ACCOUNT, "test");
                    return true;
                }
            }
            /*************************************************************/
            // token 无效或已过期
            throw new BusinessLogicException(Messages.TOKEN_INVALID);
        }

        Map<String, Object> map = tokenInfo.get();
        if (map.containsKey("account")) {
            // 判断账号是否被踢下线
            String account = (String) map.get("account");
            Optional<String> userLastLoginToken = tokenService.getUserLastLoginToken(account);
            if (userLastLoginToken.isPresent() && !userLastLoginToken.get().equals(token)) {
                // 账号在其它设备登录用户被踢下线
                throw new BusinessLogicException(Messages.TOKEN_KICKED_OFF);
            }
            // 设置account到request里，后续根据account获取用户信息
            request.setAttribute(LOGIN_USER_ACCOUNT, account);
        }

        return true;
    }

}
