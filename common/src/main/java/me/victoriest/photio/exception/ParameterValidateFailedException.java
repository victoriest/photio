package me.victoriest.photio.exception;


/**
 * 参数校验不通过exception
 *
 * @author lihu
 * @date 2017/6/22
 */
public class ParameterValidateFailedException extends RuntimeException {
    private static final long serialVersionUID = 787587296294390188L;

    private String msg;

    public ParameterValidateFailedException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
