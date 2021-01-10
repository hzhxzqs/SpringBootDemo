# SpringBoot AOP

## 目录

- [1. AOP概念](#1-AOP概念)
- [2. 项目结构](#2-项目结构)
- [3. 项目依赖](#3-项目依赖)
- [4. aop简单示例](#4-aop简单示例)
  - [4.1 aop使用](#41-aop使用)
  - [4.2 示例操作](#42-示例操作)
- [5. aop通知注解使用](#5-aop通知注解使用)
- [6. pointcut表达式](#6-pointcut表达式)
  - [6.1 execution表达式使用](#61-execution表达式使用)
    - [6.1.1 表达式语法](#611-表达式语法)
    - [6.1.2 语义说明](#612-语义说明)
  - [6.2 within表达式使用](#62-within表达式使用)
    - [6.2.1 表达式语法](#621-表达式语法)
    - [6.2.2 语义说明](#622-语义说明)
  - [6.3 args表达式使用](#63-args表达式使用)
    - [6.3.1 表达式语法](#631-表达式语法)
    - [6.3.2 语义说明](#632-语义说明)
  - [6.4 this表达式使用](#64-this表达式使用)
    - [6.4.1 表达式语法](#641-表达式语法)
    - [6.4.2 语义说明](#642-语义说明)
  - [6.5 target表达式使用](#65-target表达式使用)
    - [6.5.1 表达式语法](#651-表达式语法)
    - [6.5.2 语义说明](#652-语义说明)
  - [6.6 @within表达式使用](#66-within表达式使用)
    - [6.6.1 表达式语法](#661-表达式语法)
    - [6.6.2 语义说明](#662-语义说明)
  - [6.7 @annotation表达式使用](#67-annotation表达式使用)
    - [6.7.1 表达式语法](#671-表达式语法)
    - [6.7.2 语义说明](#672-语义说明)
  - [6.8 @args表达式使用](#68-args表达式使用)
    - [6.8.1 表达式语法](#681-表达式语法)
    - [6.8.2 语义说明](#682-语义说明)
- [7. aop其它用法](#7-aop其它用法)
  - [7.1 JoinPoint参数用法](#71-JoinPoint参数用法)
    - [7.1.1 参数说明](#711-参数说明)
    - [7.1.2 参数使用](#712-参数使用)
  - [7.2 ProceedingJoinPoint参数用法](#72-ProceedingJoinPoint参数用法)
    - [7.2.1 参数说明](#721-参数说明)
    - [7.2.2 参数使用](#722-参数使用)

## 1. AOP概念
AOP（即Aspect Oriented Programming，面向切面编程），指的是将一定的切面逻辑按照一定的方式编织到指定的业务模块中，使其在这些业务模块调用前后执行，以此增强业务模块逻辑。常用于权限检查、日志记录等。

## 2. 项目结构

```
aop
|—— demo   aop简单示例
|—— advice   aop通知注解使用
|  |—— before   @Before使用
|  |—— after   @After使用
|  |—— afterReturning   @AfterReturning使用
|  |—— afterThrowing   @AfterThrowing使用
|  |—— around   @Around使用
|
|—— expression   pointcut表达式
|  |—— execution   execution表达式使用
|  |  |—— modifier   execution表达式：modifier部分
|  |  |—— returnType   execution表达式：return-type部分
|  |  |—— classType   execution表达式：class-type部分
|  |  |—— methodName   execution表达式：method-name部分
|  |  |—— methodParam   execution表达式：method-param部分
|  |  |—— throwsException   execution表达式：throws-exception部分
|  |
|  |—— within   within表达式使用
|  |  |—— classType   within表达式：class-type部分
|  |
|  |—— args   args表达式使用
|  |  |—— methodParam   args表达式：method-param部分
|  |
|  |—— ethis   this表达式使用
|  |  |—— declaringType   this表达式：declaring-type部分
|  |
|  |—— target   target表达式使用
|  |  |—— declaringType   target表达式：declaring-type部分
|  |
|  |—— atWithin   @within表达式使用
|  |  |—— annotationType   @within表达式：annotation-type部分
|  |
|  |—— atAnnotation   @annotation表达式使用
|  |  |—— annotationType   @annotation表达式：annotation-type部分
|  |
|  |—— atArgs   @args表达式使用
|  |  |—— annotationType   @args表达式：annotation-type部分
|
|—— other   aop其它用法
|  |—— joinPoint   JoinPoint参数用法
|  |—— proceedingJoinPoint   ProceedingJoinPoint参数用法
```

## 3. 项目依赖

```
<!-- 导入aop依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

## 4. aop简单示例
### 4.1 aop使用

1. 定义切面类
   - 在类上添加`@Aspect`注解定义切面类
   - 添加`@Component`注解把切面类注入IOC容器中
2. 定义切入点
   - 在方法上添加`@Pointcut`注解定义切入点
   - 返回值`void`类型的空方法
3. 定义通知
   - 在逻辑方法上添加通知注解实现业务模块增强
   - 通知注解包括：`@Before`、`@After`、`@AfterReturning`、`@AfterThrowing`、`@Around`等

### 4.2 示例操作

> 本项目所有示例都是通过请求controller类来查看aop逻辑增强结果

运行项目后，请求`http://localhost:9001/demo/test`

## 5. aop通知注解使用

1. `@Before`
   - 前置通知
   - 在业务代码执行之前执行
   - 抛出异常时将阻止业务代码执行
   - 抛出异常后`@After`、`@AfterReturning`、`@AfterThrowing`无效
2. `@After`
   - 后置通知
   - 在业务代码执行之后执行，无论业务代码是否抛出异常
   - 类似于finally
3. `@AfterReturning`
   - 在业务代码正常执行之后执行，若业务代码抛出异常则不执行
   - 不带参数时，业务代码返回任意类型的返回值可执行
   - 携带参数时，可指定业务代码返回参数类型或`可转为参数类型`的返回值时才执行
     - 通过`returning`指定参数名，将业务代码返回值与该参数绑定
     - `可转为参数类型`包括基本类型与其包装类型互转、引用类型互转等，不包括基本类型互转
     - `可转为参数类型`例子见`AdviceAfterReturningController`中test4()、test5()、test6()
4. `@AfterThrowing`
   - 在业务代码抛出异常之后执行，若业务代码不抛出异常则不执行
   - 不带参数时，业务代码抛出任意异常可执行
   - 携带参数时，可指定业务代码抛出参数类型及其子类的异常时才执行
     - 通过`throwing`指定参数名，将业务代码抛出异常与该参数绑定
5. `@Around`
   - 环绕通知
   - 在业务代码执行前后执行
   - 携带`ProceedingJoinPoint`参数，调用`proceed()`方法可执行业务代码
   - 若不调用`proceed()`方法可阻止业务代码执行，同时`@Before`、`@After`、`@AfterReturning`、`@AfterThrowing`无效
   - 可以修改业务代码返回结果，也可以进行异常处理

## 6. pointcut表达式

> 表达式通配符：
> 1. `*`通配符：主要用于匹配单个单词，或者是以某个词为前缀或后缀的单词
> 2. `..`通配符：主要用于匹配0个或多个项
> 3. `+`通配符：主要用于匹配某种类型的子类，只能作为后缀放在类型后边

### 6.1 execution表达式使用
用于匹配某一些方法，对其进行逻辑增强操作

#### 6.1.1 表达式语法

```
execution(modifier? return-type class-type.method-name(method-param) throws-exception?)
```

#### 6.1.2 语义说明

1. `modifier`
   - 方法可见性修饰符，如：public、protected
   - 对private方法无效
   - 选填。不填写时，可匹配public、protected、package包可见方法
2. `return-type`
   - 方法返回值类型，如：int、java.lang.String等
   - 与方法声明的返回值类型匹配
   - 可使用`*`通配符匹配任意返回值类型
3. `class-type`
   - 方法所在类的类名，如：java.lang.String
   - 包名之后可使用`*`通配符匹配指定包中任意类
   - 包名之后可使用`..`通配符匹配指定包及其子包中任意类
4. `method-name`
   - 方法名，如：test1
   - 可使用`*`通配符匹配任意方法名
5. `method-param`
   - 方法参数类型，如：java.lang.String
   - 与方法声明的参数类型及参数顺序匹配
   - 可使用`..`通配符匹配0个或多个参数
6. `throws-exception`
   - 方法抛出异常类型，如：java.lang.Exception
   - 与方法声明的抛出异常类型匹配
   - 选填。不填写时可匹配任意方法，无论是否声明抛出异常
   - 可使用`*`通配符匹配声明抛出任意异常的方法，但无法匹配未声明抛出异常的方法

### 6.2 within表达式使用
用于指定某一些类，对类中的方法进行逻辑增强操作

#### 6.2.1 表达式语法

```
within(class-type)
```

#### 6.2.2 语义说明

1. class-type
   - 类名，如：java.lang.String
   - 对类中所有方法进行逻辑增强，private方法除外
   - 包名之后可使用`*`通配符匹配当前包中所有类
   - 包名之后可使用`..`及`*`通配符匹配当前包及其子包中所有类

### 6.3 args表达式使用
用于匹配特定参数类型及参数顺序的某一些方法，对其进行逻辑增强

#### 6.3.1 表达式语法

```
args(method-param)
```

#### 6.3.2 语义说明

1. method-param
   - 方法参数类型，如：java.lang.String
   - 与方法声明的参数类型及参数顺序匹配
   - 可使用`..`通配符匹配0个或多个参数，只能用一次
   - 一般与其它表达式组合使用
   - 增强方法携带参数时，method-param需包含该参数名，并按该参数类型进行匹配
   - 增强方法携带参数时可获取业务代码的参数值

### 6.4 this表达式使用
当代理类可转化为指定的类型时，对代理类的方法进行逻辑增强操作

#### 6.4.1 表达式语法

```
this(declaring-type)
```

#### 6.4.2 语义说明

1. declaring-type
   - 类名或接口，如：java.lang.String、java.lang.Runnable
   - 当代理类可转化为指定类型时，匹配该代理类

### 6.5 target表达式使用
当目标类可转化为指定的类型时，对目标类的方法进行逻辑增强操作

#### 6.5.1 表达式语法

```
target(declaring-type)
```

#### 6.5.2 语义说明

1. declaring-type
   - 类名或接口，如：java.lang.String、java.lang.Runnable
   - 当目标类可转化为指定类型时，匹配该目标类

### 6.6 @within表达式使用
用于指定某一些带有指定注解的类，对类中的方法进行逻辑增强操作

#### 6.6.1 表达式语法

```
@within(annotation-type)
```

#### 6.6.2 语义说明

1. annotation-type
   - 注解类型，如：org.aspectj.lang.annotation.Aspect
   - 匹配带有指定注解的类

### 6.7 @annotation表达式使用
用于指定某一些带有指定注解的方法，对其进行逻辑增强操作

#### 6.7.1 表达式语法

```
@annotation(annotation-type)
```

#### 6.7.2 语义说明

1. annotation-type
   - 注解类型，如：org.aspectj.lang.annotation.Aspect
   - 匹配带有指定注解的方法

### 6.8 @args表达式使用
匹配使用指定注解标注的类作为方法参数的方法，对其进行逻辑增强操作

#### 6.8.1 表达式语法

```
@args(annotation-type)
```

#### 6.8.2 语义说明

1. annotation-type
   - 注解类型，如：org.aspectj.lang.annotation.Aspect
   - 匹配使用指定注解标注的类作为参数的方法

## 7. aop其它用法

### 7.1 JoinPoint参数用法
#### 7.1.1 参数说明

- 任何通知方法都可使用，但`@Around`通知一般用`ProceedingJoinPoint`
- 作为第一个参数

#### 7.1.2 参数使用

1. `joinPoint.getSignature()`获取连接点方法签名`Signature`对象
   - `signature.getMidifiers()`获取连接点方法修饰符int值
   - `signature.getDeclaringType()`获取连接点方法所在类的Class对象
   - `signature.getDeclaringTypeName()`获取连接点方法所在类名
   - `signature.getName()`获取连接点方法名
   - `signature.toString()`获取连接点方法描述
   - `signature.toLongString()`获取连接点方法扩展描述
   - `signature.toShortString()`获取连接点方法简单描述
2. `joinPoint.getArgs()`获取连接点方法参数值数组
3. `joinPoint.getTarget()`获取目标类对象
4. `joinPoint.getThis()`获取当前执行类/代理类对象
5. 获取连接点相关
   - `joinPoint.toString()`获取连接点描述
   - `joinPoint.toLongString()`获取连接点扩展描述
   - `joinPoint.toShortString()`获取连接点简单描述

### 7.2 ProceedingJoinPoint参数用法
#### 7.2.1 参数说明

- 只允许在`@Around`通知使用
- 作为第一个参数
- 继承`JoinPoint`，可使用`JoinPoint`方法

#### 7.2.2 参数使用

1. `pjp.proceed()`执行业务代码
2. `pjp.proceed(Object[])`可向业务代码传参
   - 覆盖原业务代码参数
   - Object数组元素个数需与业务代码入参个数相同
   - Object数组元素可对应转为业务代码入参类型
