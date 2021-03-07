package zam.hzh.exceptionHandler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zam.hzh.exceptionHandler.common.exception.MyException;

@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/demo")
    public String demo() {
        return "demo";
    }

    @RequestMapping(value = "/postMethod", method = RequestMethod.POST)
    public String postMethod() {
        return "demo";
    }

    @RequestMapping("/myException")
    public String msgException() throws MyException {
        throw new MyException("抛出MyException异常");
    }

    @RequestMapping("/exception")
    public String exception() throws Exception {
        throw new Exception("抛出Exception异常");
    }

    // 取消以下注释，由当前类捕获MyException异常
    // @ExceptionHandler(MyException.class)
    // @ResponseBody
    // public Msg handleMyException(MyException e) {
    //     return Msg.getMsg(e.getErrorCode(), e.getErrorMsg(), "由PageController类处理");
    // }
}
