package zam.hzh.quartz.common.result;

public enum MsgEnum {

    MSG_SUCCESS("0000", "操作成功"),
    MSG_ERROR("0001", "操作失败"),
    SYSTEM_BUSY("0002", "系统繁忙");

    private final String code;

    private final String message;

    MsgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
