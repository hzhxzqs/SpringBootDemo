package zam.hzh.aop.expression.target.declaringType.service;

import org.springframework.stereotype.Service;

@Service
public class TargetDeclaringTypeService {

    public void test() {
        System.out.println("正在执行test方法，未实现接口的类的方法");
    }
}
