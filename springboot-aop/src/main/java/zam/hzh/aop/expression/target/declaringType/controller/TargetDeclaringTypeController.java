package zam.hzh.aop.expression.target.declaringType.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.aop.expression.target.declaringType.service.TargetDeclaringTypeInterface;
import zam.hzh.aop.expression.target.declaringType.service.TargetDeclaringTypeService;
import zam.hzh.aop.expression.target.declaringType.service.TargetDeclaringTypeServiceImpl;

@RestController
@RequestMapping("/target/declaringType")
public class TargetDeclaringTypeController {

    @Autowired
    private TargetDeclaringTypeService targetDeclaringTypeService;

    @Autowired
    private TargetDeclaringTypeServiceImpl targetDeclaringTypeServiceImpl;

    @Autowired
    private TargetDeclaringTypeInterface targetDeclaringTypeInterface;

    @RequestMapping("/test1")
    public void test1() {
        targetDeclaringTypeService.test();
    }

    @RequestMapping("/test2")
    public void test2() {
        targetDeclaringTypeServiceImpl.test();
    }

    @RequestMapping("/test3")
    public void test3() {
        targetDeclaringTypeServiceImpl.iTest();
    }

    @RequestMapping("/test4")
    public void test4() {
        targetDeclaringTypeInterface.iTest();
    }
}
