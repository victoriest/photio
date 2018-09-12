package me.victoriest.photio.gateway;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.model.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author VictoriEST
 * @date 2018/9/12
 * photio
 */
@Component
public class ErrorFilter extends SendErrorFilter {
    Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            Throwable throwable = ctx.getThrowable();
            ResponseDto dto;
            if (throwable.getCause() instanceof BusinessLogicException) {
                dto = new ResponseDto().fail(((BusinessLogicException) throwable.getCause()).getState(),
                        ((BusinessLogicException) throwable.getCause()).getMsg());

            } else {
                dto = new ResponseDto().fail(throwable.getCause().getMessage());
            }
            ctx.setResponseBody(JSON.toJSONString(dto));
            ctx.remove("throwable");
            ctx.getResponse().setContentType("application/json; charset=utf-8");

            log.info("this is a ErrorFilter : ", dto.getMsg());
            super.run();
        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }
        return null;
    }

}
