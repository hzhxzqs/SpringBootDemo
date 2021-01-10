package zam.hzh.aop.expression.args.methodParam.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - args表达式：method-param部分
 */
@Aspect
@Component
public class ArgsMethodParamAop {

    @After("args() && within(zam.hzh.aop.expression.args.methodParam.controller.ArgsMethodParamController)")
    public void advice1() {
        System.out.println("advice1执行，业务代码无参数方法");
    }

    @After("args(int) && within(zam.hzh.aop.expression.args.methodParam.controller.ArgsMethodParamController)")
    public void advice2() {
        System.out.println("advice2执行，业务代码只有一个int参数方法");
    }

    @After("args(String) && within(zam.hzh.aop.expression.args.methodParam.controller.ArgsMethodParamController)")
    public void advice3() {
        System.out.println("advice3执行，业务代码只有一个String参数方法");
    }

    @After("args(int, String) && within(zam.hzh.aop.expression.args.methodParam.controller.ArgsMethodParamController)")
    public void advice4() {
        System.out.println("advice4执行，业务代码有int参数、String参数方法");
    }

    @After("args(..) && within(zam.hzh.aop.expression.args.methodParam.controller.ArgsMethodParamController)")
    public void advice5() {
        System.out.println("advice5执行，业务代码不定参数方法");
    }

    @After("args(String, ..) && within(zam.hzh.aop.expression.args.methodParam.controller.ArgsMethodParamController)")
    public void advice6() {
        System.out.println("advice6执行，业务代码第一个参数为String参数的方法");
    }

    /**
     * 增强方法携带参数，method-param需包含该参数名
     */
    @After("args(s) && within(zam.hzh.aop.expression.args.methodParam.controller.ArgsMethodParamController)")
    public void advice7(String s) {
        System.out.println("advice7执行，业务代码有以参数s的类型为参数的方法，s：" + s);
    }
}
