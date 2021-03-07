# SpringBoot 全局异常处理

## 目录

- [1. 概述](#1-概述)
- [2. 注解说明](#2-注解说明)
  - [2.1 @ExceptionHandler](#21-ExceptionHandler)
  - [2.2 @ControllerAdvice](#22-ControllerAdvice)
  - [2.3 @RestControllerAdvice](#23-RestControllerAdvice)
- [3. 注解使用](#3-注解使用)
- [4. 其它说明](#4-其它说明)
- [5. 项目操作](#5-项目操作)

## 1. 概述
在日常开发过程中，不可避免地需要处理各种异常，所以代码中会出现大量的`try{...}catch(...){...}finally{...}`代码块，不仅有大量的冗余代码，还影响代码的可读性。而且如果遗漏了某些异常，当出现异常时将会在页面直接显示一大片错误信息，对用户来说体验不好。

Spring提供的`@ExceptionHandler`和`@ControllerAdvice`等注解即可为异常处理提供解决方法。`@ExceptionHandler`，从字面上看，就是异常处理器的意思，其与`@ControllerAdvice`/`@RestControllerAdvice`结合使用，可以对不同阶段、不同异常进行处理，这就是统一/全局异常处理的原理。

## 2. 注解说明
### 2.1 @ExceptionHandler

- 注解属性：
  - `value`：指定方法处理的异常类型数组，如果为空，默认方法参数列表中的异常
- 在方法上标注，定义该方法为异常处理方法
- 可指定异常，当出现该指定异常时执行该方法
- 方法参数可添加指定异常类型的参数，接受异常对象
- 方法参数可使用Spring MVC提供的数据绑定，如`HttpServletRequest`等

### 2.2 @ControllerAdvice

- 注解属性：
  - `value`：同`basePackages`
  - `basePackages`：指定包名数组，用于处理指定的包下及其子包下Controller类产生的异常
  - `basePackageClasses`：指定类数组，用于处理指定的类所属的包及其子包下Controller类产生的异常
  - `assignableTypes`：指定Controller类数组，用于处理指定的Controller类产生的异常
  - `annotations`：指定注解数组，用于处理指定的注解标注的Controller类产生的异常
- 在类上标注，定义该类为全局异常处理类
- 结合异常处理方法可处理所有Controller类出现的异常
- 返回值为一个页面

### 2.3 @RestControllerAdvice

- 注解属性及作用同上
- 返回值为序列化对象，如json对象等

## 3. 注解使用
结合这些注解可以有两种异常处理方法：

- `@Controller`/`@RestController`和`@ExceptionHandler`

  在Controller类中定义一个或多个方法，在方法上添加`@ExceptionHandler`注解并指定异常。当该Controller类中出现指定的异常时，就会执行对应的方法。但是，这样一来，对于每个Controller类都需要定义一套异常处理方法，会造成大量的冗余代码。

- `@ControllerAdvice`/`@RestControllerAdvice`和`@ExceptionHandler`

  定义一个类，在类上添加`@ControllerAdvice`或`@RestControllerAdvice`注解，类中方法添加`@ExceptionHandler`注解并指定异常。当任意(或指定范围)的Controller类产生异常时，就会交给该类进行处理，如果无法处理才抛出异常。

> 注：当两种方法同时存在时，第一种异常处理方法优先于第二种异常处理方法

## 4. 其它说明
当请求没有匹配到控制器的情况下，会抛出`NoHandlerFoundException`(404 Not Found)异常。默认情况下，该异常会被Spring捕获并处理，并不会被`@ControllerAdvice`异常处理器处理，仍然会显示404 Not Found页面。那么，如何让404也抛出异常呢？只需要添加如下配置，就可以在异常处理器中捕获到它了：

```
# 抛出NoHandlerFoundException
spring:
  mvc:
    throw-exception-if-no-handler-found: true
  resources:
    add-mappings: false
```

## 5. 项目操作
运行项目之后，可以在浏览器访问以下链接：

- 正常页面：`http://localhost:9001/page/demo`
- `HttpRequestMethodNotSupportedException`异常处理：`http://localhost:9001/page/postMethod`
- `MyException`异常处理：`http://localhost:9001/page/myException`
- `Exception`异常处理：`http://localhost:9001/page/exception`
- 404页面，`NoHandlerFoundException`(404 Not Found)异常处理：其它链接
