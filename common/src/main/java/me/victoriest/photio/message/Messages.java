package me.victoriest.photio.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * @author lihu
 * @date 2017/6/23
 */
public enum Messages {

    /**
     * Token
     */
    TOKEN_REQUIRED(1000, "token.required"),
    TOKEN_INVALID(1001, "token.invalid"),
    TOKEN_KICKED_OFF(1002, "token.kicked_off"),

    /**
     * Login (用户登录)
     */
    LOGIN_UER_NOT_FOUND(10020, "login.user.not_found"),
    LOGIN_USER_PWD_RSA_DECRYPT_FAILED(10021, "login.user.pwd.rsa.decrypt.failed"),

    /**
     * 通用异常
     */
    INTERNAL_SERVER_ERROR(500, "internal_server_error"),
    MISSING_SERVLET_REQUEST_PARAMETER(400, "missing_servlet_request_parameter"),
    NO_SUCH_ALGORITHM_EXCEPTION(20000, "no_such_algorithm_exception");

    private final int code;
    private final String message;

    Messages(int code, String description) {
        this.code = code;
        this.message = description;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new UTF8Control());

    public String getMessage() {
        try {
            String friendlyMessage = resourceBundle.getString(message);
            return friendlyMessage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return message;
        }
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}

