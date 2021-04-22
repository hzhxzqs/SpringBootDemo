package zam.hzh.shiro.common.result;

public enum MsgEnum {

    MSG_SUCCESS("0000", "操作成功"),
    MSG_ERROR("0001", "操作失败"),
    SYSTEM_BUSY("0002", "系统繁忙"),
    OLD_PASSWORD_ERROR("0003", "原密码错误"),
    USER_NOT_EXIST("0004", "用户不存在"),
    PASSWORD_ERROR("0005", "密码错误"),
    USERNAME_EXIST("0006", "用户名已存在"),
    ROLE_EXIST("0007", "角色已存在"),
    ROLE_NOT_EXIST("0008", "角色不存在"),
    PERMISSION_EXIST("0009", "权限已存在"),
    PASSWORD_CONFIRM_ERROR("0010", "两次密码不一致"),
    USER_DISABLED("0011", "用户被禁用"),
    USER_LOCK("0012", "密码输入错误达到5次，用户锁定5分钟");

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
