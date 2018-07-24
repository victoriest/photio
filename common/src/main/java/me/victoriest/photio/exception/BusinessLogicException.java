package me.victoriest.photio.exception;


import me.victoriest.photio.message.Messages;

/**
 * 业务逻辑异常
 *
 * @author lihu
 * @date 2017/6/23
 */
public class BusinessLogicException extends RuntimeException {
    private static final long serialVersionUID = -1895453507043974790L;

    private String msg;

    private int state;


    public BusinessLogicException(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public BusinessLogicException(Messages messages) {
        this.state = messages.getCode();
        this.msg = messages.getMessage();
    }

    public int getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
