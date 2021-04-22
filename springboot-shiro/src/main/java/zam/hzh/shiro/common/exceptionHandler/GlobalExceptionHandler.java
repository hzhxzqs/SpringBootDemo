package zam.hzh.shiro.common.exceptionHandler;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import zam.hzh.shiro.common.exception.MyException;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.common.result.MsgEnum;

/**
 * 异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 未认证异常，用户未登录但访问@RequiresRoles、@RequiresPermissions等注解标记的页面时抛出
     *
     * @param e 异常
     * @return 页面
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public String handleUnauthenticatedException(UnauthenticatedException e) {
        logger.error("捕获未认证异常：", e);
        return "/authc/unauthc";
    }

    /**
     * 未授权异常，用户无授权但访问@RequiresRoles、@RequiresPermissions等注解标记的页面时抛出
     *
     * @param e 异常
     * @return 页面
     */
    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException(UnauthorizedException e) {
        logger.error("捕获未授权异常：", e);
        return "/authc/unauthorized";
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
        logger.error("捕获自定义异常，异常码：{}，异常信息：{}，异常：",
                e.getErrorCode(), e.getErrorMsg(), e.getThrowable());
        return Msg.getMsg(e.getErrorCode(), e.getErrorMsg(), null);
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
        logger.error("捕获未处理异常：", e);
        return Msg.error(MsgEnum.SYSTEM_BUSY);
    }
}
