package me.victoriest.photio;

import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.exception.ParameterValidateFailedException;
import me.victoriest.photio.message.Messages;
import me.victoriest.photio.model.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 *
 * @author victoriest
 * @date 2017/5/7
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String getParamsInfo(HttpServletRequest request) {
        StringBuilder paramsInfo = new StringBuilder();
        Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()){
            String key = paramNames.nextElement();
            paramsInfo.append("key=");
            paramsInfo.append(key);
            paramsInfo.append(",value=");
            paramsInfo.append(request.getParameter(key));
            paramsInfo.append("  ");
        }
        return paramsInfo.toString();
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseDto businessLogicExceptionHandler(HttpServletRequest request, BusinessLogicException e) {
        String paramsInfo = getParamsInfo(request);
            logger.info("METHOD:" + request.getMethod() + " |Url:" + request.getRequestURL().toString() + "|params: " + paramsInfo + "|Message:" + e.getMsg(), e);
        return new ResponseDto().fail(e.getState(), e.getMsg());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseDto missingServletRequestParameterExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        String paramsInfo = getParamsInfo(request);
        String msg = Messages.MISSING_SERVLET_REQUEST_PARAMETER.getMessage() + ":" + e.getParameterName();
            logger.info("METHOD:" + request.getMethod() + " |Url:" + request.getRequestURL().toString() + "|params: " + paramsInfo + "|Message:" + msg, e);
        return new ResponseDto().fail(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(ParameterValidateFailedException.class)
    public ResponseDto parameterValidateFailedExceptionHandler(HttpServletRequest request, ParameterValidateFailedException e) {
        String paramsInfo = getParamsInfo(request);
            logger.info("METHOD:" + request.getMethod() + " |Url:" + request.getRequestURL().toString() + "|params: " + paramsInfo + "|Message:" + e.getMessage(), e);
        return new ResponseDto().fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseDto exceptionHandler(HttpServletRequest request, Exception e) {
        String paramsInfo = getParamsInfo(request);

        logger.error("METHOD:" + request.getMethod() + " |Url:" + request.getRequestURL().toString() + "|params: " + paramsInfo + "|Message:" + e.getMessage(), e);
        return new ResponseDto().fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR.getMessage());
    }

}
