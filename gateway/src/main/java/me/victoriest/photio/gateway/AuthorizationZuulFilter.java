package me.victoriest.photio.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.gateway.service.feign.UserFeignClient;
import me.victoriest.photio.message.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
            "/producer-user/v1/api/testRsa",
            "/v2/api-docs"
    };

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserFeignClient userFeignClient;

    @Value("${token.enable}")
    private boolean enableToken;

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
            throw new BusinessLogicException(Messages.TOKEN_REQUIRED);
        }

        String token = accessToken.toString();

        if(userFeignClient.verifyToken(token).getState() != 0) {
            throw new BusinessLogicException(Messages.TOKEN_INVALID);
        }

        return null;
    }
}
