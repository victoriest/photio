package me.victoriest.photio.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import me.victoriest.photio.JwtHelper;
import me.victoriest.photio.config.TokenConfig;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.message.Messages;
import me.victoriest.photio.rsa.TokenService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * zuul服务过滤
 *
 * @author VictoriEST
 * @date 2018/1/26
 * spring-cloud-step-by-step
 */
@Component
public class AuthorizationZuulFilter extends ZuulFilter {

    private String[] ignoreFilteUrls = new String[] {
            "/producer-user/v1/api/login",
            "/producer-user/v1/api/registry",
            "/producer-user/v1/api/getRsaKey",
    };

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String LOGIN_USER_ACCOUNT = "LOGIN_USER_ACCOUNT";

    @Value("${token.enable}")
    private boolean enableToken;
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Autowired
//    private TokenConfig tokenConfig;

    @Override
    public String filterType() {
//        filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
//        pre：路由之前
//        routing：路由之时
//        post： 路由之后
//        error：发送错误调用
        return "pre";
    }

    @Override
    public int filterOrder() {
        //filterOrder：过滤的顺序
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        String uri = context.getRequest().getRequestURI();
        for (String s : ignoreFilteUrls) {
            if (uri.contains(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() {
        // 滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getHeader("token");
        if(accessToken == null) {
            accessToken = request.getParameter("token");
        }
        if(accessToken == null) {
            System.out.println("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {

                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }

        String token = accessToken.toString();

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

//        // 从redis中获取token信息
//        Optional<Map<String, Object>> tokenInfo = tokenService.getTokenInfo(token);
//        if (!tokenInfo.isPresent()) {
//            /*************************************************************
//             *  该代码是为了不影响dev模式下单元测试token值问题
//             */
//            if (tokenConfig.isTokenSimulateEnabled()) {
//                Integer unitTestTokenValue = tokenConfig.getUnitTestTokenValue();
//                if (unitTestTokenValue != null && String.valueOf(unitTestTokenValue).equals(token)) {
//                    // 设置userId到request里，后续根据userId获取用户信息
//                    request.setAttribute(LOGIN_USER_ACCOUNT, "test");
//                    return true;
//                }
//            }
//            /*************************************************************/
//            // token 无效或已过期
//            throw new BusinessLogicException(Messages.TOKEN_INVALID);
//        }
//
//        Map<String, Object> map = tokenInfo.get();
//        if (map.containsKey("account")) {
//            // 判断账号是否被踢下线
//            String account = (String) map.get("account");
//            Optional<String> userLastLoginToken = tokenService.getUserLastLoginToken(account);
//            if (userLastLoginToken.isPresent() && !userLastLoginToken.get().equals(token)) {
//                // 账号在其它设备登录用户被踢下线
//                throw new BusinessLogicException(Messages.TOKEN_KICKED_OFF);
//            }
//            // 设置account到request里，后续根据account获取用户信息
//            request.setAttribute(LOGIN_USER_ACCOUNT, account);
//        }

        return null;
    }
}
