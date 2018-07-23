
package me.victoriest.photio.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Victoriest
 */
public class ResponseDto<T> implements Serializable {
    private static final long serialVersionUID = -6173736864456710787L;

    private int state;

    @JsonInclude(Include.NON_NULL)
    private T data;

    private String msg;

    private Long date;

    public ResponseDto() {
        super();
    }

    public ResponseDto<T> success() {
        return success(null);
    }

    public ResponseDto<T> success(T data) {
        this.state = 0;
        this.data = data;
        this.msg = "success";
        date = (new Date()).getTime();
        return this;
    }

    public ResponseDto<T> success(T data, String message) {
        this.state = 0;
        this.data = data;
        this.msg = message;
        date = (new Date()).getTime();
        return this;
    }

    public ResponseDto<T> fail(String message) {
        return fail(-1, message);
    }

    public ResponseDto<T> fail(int state, String message) {
        this.state = state;
        this.msg = message;
        date = (new Date()).getTime();
        return this;
    }

    public ResponseDto<T> fail(int state, String message, T data) {
        this.state = state;
        this.msg = message;
        this.data = data;
        date = (new Date()).getTime();
        return this;
    }

//    @Transient
//    @JSONField(serialize = false, deserialize = false)
//    public boolean isSuccess() {
//        return state == 0;
//    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ResultDTO [state=" + state + ", data=" + data + ", msg=" + msg
                + "]";
    }

}
