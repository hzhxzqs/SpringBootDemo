package zam.hzh.jpa.common.result;

import java.io.Serializable;

public class Msg implements Serializable {

    private static final long serialVersionUID = -3774535169136091173L;

    private String code;

    private String message;

    private Object data;

    public Msg() {
    }

    public Msg(MsgEnum msg) {
        this.code = msg.getCode();
        this.message = msg.getMessage();
    }

    public Msg(MsgEnum msg, Object data) {
        this.code = msg.getCode();
        this.message = msg.getMessage();
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
}
