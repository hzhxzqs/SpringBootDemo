package zam.hzh.aop.other.joinPoint.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * JoinPoint参数用法
 */
@Aspect
@Component
public class JoinPointAop {

    @After("execution(* zam.hzh.aop.other.joinPoint.controller.JoinPointController.*(..))")
    public void advice1(JoinPoint joinPoint) {
        System.out.println("获取连接点方法签名：");
        Signature signature = joinPoint.getSignature();
        System.out.println("-- 获取连接点方法修饰符：");
        System.out.println(signature.getModifiers());
        System.out.println(Modifier.toString(signature.getModifiers()));
        System.out.println("-- 获取连接点方法所在类的Class对象：");
        System.out.println(signature.getDeclaringType());
        System.out.println("-- 获取连接点方法所在类名:");
        System.out.println(signature.getDeclaringTypeName());
        System.out.println("-- 获取连接点方法名：");
        System.out.println(signature.getName());
        System.out.println("-- 获取连接点方法描述：");
        System.out.println(signature.toString());
        System.out.println("-- 获取连接点方法扩展描述：");
        System.out.println(signature.toLongString());
        System.out.println("-- 获取连接点方法简单描述：");
        System.out.println(signature.toShortString());

        System.out.println("=================================================");

        System.out.println("获取连接点方法参数值：");
        Object[] args = joinPoint.getArgs();
        System.out.println("方法参数值个数：" + args.length);
        for (Object arg : args) {
            System.out.println(arg);
        }

        System.out.println("=================================================");

        System.out.println("获取目标类对象：");
        Object target = joinPoint.getTarget();
        System.out.println(target);

        System.out.println("获取当前执行类/代理类对象：");
        Object aThis = joinPoint.getThis();
        System.out.println(aThis);

        System.out.println("=================================================");

        System.out.println("获取连接点描述：");
        String s = joinPoint.toString();
        System.out.println(s);

        System.out.println("获取连接点扩展描述：");
        String s1 = joinPoint.toLongString();
        System.out.println(s1);

        System.out.println("获取连接点简单描述：");
        String s2 = joinPoint.toShortString();
        System.out.println(s2);
    }
}
