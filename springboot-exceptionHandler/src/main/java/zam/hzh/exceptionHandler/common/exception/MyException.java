package zam.hzh.exceptionHandler.common.exception;

import zam.hzh.exceptionHandler.common.result.MsgEnum;

/**
 * 自定义异常
 */
public class MyException extends Exception {

    private static final long serialVersionUID = -8338819131804733133L;

    private String errorCode;

    private String errorMsg;

    private Throwable throwable;

    public MyException() {
        super();
    }

    public MyException(MsgEnum msgEnum) {
        super(msgEnum.getMessage());
        this.errorCode = msgEnum.getCode();
        this.errorMsg = msgEnum.getMessage();
    }

    public MyException(MsgEnum msgEnum, Throwable throwable) {
        super(msgEnum.getMessage(), throwable);
        this.errorCode = msgEnum.getCode();
        this.errorMsg = msgEnum.getMessage();
        this.throwable = throwable;
    }

    public MyException(String errorMsg) {
        super(errorMsg);
        this.errorCode = MsgEnum.MSG_ERROR.getCode();
        this.errorMsg = errorMsg;
    }

    public MyException(String errorCode, String errorMsg, Throwable throwable) {
        super(errorMsg, throwable);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.throwable = throwable;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "MyException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", throwable=" + throwable +
                '}';
    }
}
