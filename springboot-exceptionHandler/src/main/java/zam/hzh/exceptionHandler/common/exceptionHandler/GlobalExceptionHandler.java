package zam.hzh.exceptionHandler.common.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import zam.hzh.exceptionHandler.common.exception.MyException;
import zam.hzh.exceptionHandler.common.result.Msg;
import zam.hzh.exceptionHandler.common.result.MsgEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 404 Not Found异常
     *
     * @param e 异常
     * @return noFound页面
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException e) {
        logger.warn("未找到路径：{}", e.getRequestURL());
        return "notFound";
    }

    /**
     * 方法请求方式不支持，如以GET方式请求POST方法
     *
     * @param request request
     * @param e       异常
     * @return Msg
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Msg handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        logger.warn("请求路径：{}，请求方式：{} 不支持，支持请求方式：{}",
                request.getRequestURI(), e.getMethod(), e.getSupportedHttpMethods());
        return Msg.error(MsgEnum.REQUEST_METHOD_NOT_SUPPORTED);
    }

    /**
     * 自定义异常
     *
     * @param e 异常
     * @return Msg
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Msg handleMyException(MyException e) {
        logger.error(e.getErrorMsg());
        return Msg.getMsg(e.getErrorCode(), e.getErrorMsg(), "由GlobalExceptionHandler类处理");
    }

    /**
     * 未处理异常
     *
     * @param e 异常
     * @return Msg
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Msg handleException(Exception e) {
        logger.error(e.getMessage());
        return Msg.error(MsgEnum.SYSTEM_BUSY);
    }
}
