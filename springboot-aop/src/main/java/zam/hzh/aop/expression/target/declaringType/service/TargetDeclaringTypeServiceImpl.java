package zam.hzh.aop.expression.target.declaringType.service;

import org.springframework.stereotype.Service;

@Service
public class TargetDeclaringTypeServiceImpl implements TargetDeclaringTypeInterface {

    public void test() {
        System.out.println("正在执行test方法，实现接口的类的方法");
    }

    @Override
    public void iTest() {
        System.out.println("正在执行iTest方法，接口方法");
    }
}
