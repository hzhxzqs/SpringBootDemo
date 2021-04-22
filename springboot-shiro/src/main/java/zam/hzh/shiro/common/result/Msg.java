package zam.hzh.shiro.common.result;

import java.io.Serializable;

public class Msg implements Serializable {

    private static final long serialVersionUID = -2928132758921606716L;

    private String code;

    private String message;

    private Object data;

    public Msg() {
    }

    private Msg(MsgEnum msg) {
        this.code = msg.getCode();
        this.message = msg.getMessage();
    }

    private Msg(MsgEnum msg, Object data) {
        this.code = msg.getCode();
        this.message = msg.getMessage();
        this.data = data;
    }

    private Msg(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Msg success() {
        return new Msg(MsgEnum.MSG_SUCCESS);
    }

    public static Msg success(Object data) {
        return new Msg(MsgEnum.MSG_SUCCESS, data);
    }

    public static Msg error() {
        return new Msg(MsgEnum.MSG_ERROR);
    }

    public static Msg error(MsgEnum msg) {
        return new Msg(msg);
    }

    public static Msg getMsg(MsgEnum msg, Object data) {
        return new Msg(msg, data);
    }

    public static Msg getMsg(String code, String message, Object data) {
        return new Msg(code, message, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
