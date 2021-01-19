# SpringBoot Thymeleaf

## 目录

- [1. 项目依赖](#1-项目依赖)
- [2. 项目配置](#2-项目配置)
- [3. 基础语法](#3-基础语法)
  - [3.1 页面使用](#31-页面使用)
  - [3.2 表达式](#32-表达式)
    - [3.2.1 链接表达式](#321-链接表达式)
    - [3.2.2 变量表达式](#322-变量表达式)
    - [3.2.3 选择表达式](#323-选择表达式)
    - [3.2.4 消息表达式](#324-消息表达式)
    - [3.2.5 片段表达式](#325-片段表达式)
  - [3.3 字面量](#33-字面量)
    - [3.3.1 字符串字面量](#331-字符串字面量)
    - [3.3.2 数字字面量](#332-数字字面量)
    - [3.3.3 布尔字面量](#333-布尔字面量)
    - [3.3.4 空值字面量](#334-空值字面量)
    - [3.3.5 字面量标记](#335-字面量标记)
  - [3.4 操作和运算符](#34-操作和运算符)
    - [3.4.1 字符串操作](#341-字符串操作)
    - [3.4.2 算术运算符](#342-算术运算符)
    - [3.4.3 布尔运算符](#343-布尔运算符)
    - [3.4.4 比较运算符](#344-比较运算符)
    - [3.4.5 条件运算符](#345-条件运算符)
  - [3.5 循环和条件](#35-循环和条件)
    - [3.5.1 循环](#351-循环)
    - [3.5.2 条件](#352-条件)
    - [3.5.3 分支控制](#353-分支控制)
- [4. 示例操作](#4-示例操作)

## 1. 项目依赖

```
<!-- 导入thymeleaf依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

## 2. 项目配置

```
spring:
  thymeleaf:
    # 设置编码
    encoding: UTF-8
    # 页面默认前缀
    prefix: classpath:/templates/
    # 页面默认后缀
    suffix: .html
    # 关闭缓存
    cache: false
```

## 3. 基础语法
### 3.1 页面使用
在html页面上使用需要先导入标签库，才能使用`th:*`形式的标签

```
<!-- 导入标签库 -->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

### 3.2 表达式
Thymeleaf包含以下5种类型的表达式：

- `@{...}`：链接(URL)表达式
- `${...}`：变量表达式
- `*{...}`：选择表达式
- `#{...}`：消息(i18n)表达式
- `~{...}`：片段表达式

#### 3.2.1 链接表达式
链接表达式旨在构建URL并向其添加有用的上下文和会话信息(通常称为URL重写)

链接表达式使用例子：

```
<link type="text/css" rel="stylesheet" th:href="@{/css/demo.css}">
<a th:href="@{/page/demo}"></a>
```

链接表达式可以根据web应用程序部署的服务器上下文进行自动转换，假设应用程序部署在服务器的`/myapp`下，将转换为：

```
<a th:href="/myapp/page/demo"></a>
```

当浏览器未启用cookie但我们需要保持会话时，可以转换为：

```
<a th:href="/myapp/page/demo;jsessionid=3a76b12e2ff43213"></a>
```

URL也可以携带参数，例如：

```
<a th:href="@{/page/demo(id=${demoId},type=${demoType})}"></a>
```

其可以转换为：

```
<a th:href="/myapp/page/demo?id=11&amp;type=type1"></a>
```

链接表达式可以是相对的，此时URL将不会添加应用上下文前缀，如：

```
<a th:href="@{../page/demo}"></a>
```

也可以相对于服务器，同样不会添加前缀：

```
<a th:href="@{~/page/demo}"></a>
```

还有绝对协议：

```
<a th:href="@{https://www.company.com/main}"></a>
```

以及相对协议，和绝对协议一样，但浏览器将使用与显示页面相同的http或https协议：

```
<a th:href="@{//www.company.com/main/sub}"></a>
```

在绝对协议(或相对协议)的URL中，可以通过响应过滤器`response filters`自定义URL重写。在基于Servlet的web应用中，对于每个输出的URL，Thymeleaf总是在显示URL之前调用`HttpServletResponse.encodeUrl(...)`机制。这意味着通过包装`HttpServletResponse`对象，过滤器可以进行自定义的URL重写。

#### 3.2.2 变量表达式
变量表达式是OGNL表达式(如果与Spring整合则是EL表达式)，其作用于上下文变量`context variables`(在Spring术语中也称为模型属性`model attributes`)

变量表达式使用例子：

```
<span th:text="${user.name}"></span>
```

变量表达式不仅能在涉及输出的地方使用，在一些比较复杂的操作中，例如条件、循环等也可以使用，例如：

```
<div th:each="user : ${users}">
    <span th:text="${user.name}"></span>
</div>
```

> 表达式基本对象：
> - `#ctx`：上下文对象
> - `#vars`：上下文变量
> - `#locale`：上下文语言环境
> - `#request`：HttpServletRequest对象
> - `#response`：HttpServletResponse对象
> - `#session`：HttpSession对象
> - `#servletContext`：ServletContext对象

> 表达式工具对象：
> - `#execInfo`：有关正在处理的模板的信息
> - `#messages`：用于获取变量表达式内的外部化消息，与使用`#{...}`语法获取消息的方法相同
> - `#uris`：用于转义部分URL/URI
> - `#conversions`：用于执行已配置的转换服务(如果有)
> - `#dates`：java.util.Date对象工具类
> - `#calendars`：java.util.Calendar对象工具类
> - `#numbers`：用于格式化数字
> - `#strings`：String对象工具类
> - `#objects`：一般对象工具类
> - `#bools`：用于判断布尔值
> - `#arrays`：数组工具类
> - `#lists`：List集合工具类
> - `#sets`：Set集合工具类
> - `#maps`：Map集合工具类
> - `#aggregates`：用于在数组或集合上进行聚合操作
> - `#ids`：用于处理可能重复的id属性

#### 3.2.3 选择表达式
选择表达式与变量表达式类似，不同点在于选择表达式操作的是先前选择的对象，而不是上下文变量

选择表达式使用例子：

```
<div th:object="${user}">
    <span th:text="*{name}"></span>
    <span th:text="*{age}"></span>
</div>
```

通过`th:object`标签选择对象，之后即可使用选择表达式获取该对象的属性

#### 3.2.4 消息表达式
消息表达式(通常称为`text externalization`，`internationalization`或`i18n`)允许我们从外部源文件(`.properties`文件)检索特定区域的消息，通过键引用它们，并(可选)应用一组参数

在Spring应用中，它将自动与Spring的`MessageSource`机制自动整合

消息表达式使用例子：

```
<div th:text="#{address.city}"></div>
<div th:text="#{address.cityseleted(${cityId})}"></div>
```

如果想要用上下文变量确定消息键或指定参数变量，可以在消息表达式里使用变量表达式，如：

```
<div th:text="#{${config.welcomeKey}(${user.name})}"></div>
```

#### 3.2.5 片段表达式
片段表达式可用于展示标记的片段并且可在模板中移动它们。通过使用片段表达式，我们可以进行片段复制、作为参数传递给其它模板等操作。

最常用的用法是进行片段插入，可使用`th:insert`或`th:replace`，如：

```
<div th:insert="~{commons :: main}"></div>
```

### 3.3 字面量
Thymeleaf包含以下5种类型的字面量：

- 字符串字面量
- 数字字面量
- 布尔字面量
- 空值字面量
- 字面量标记

#### 3.3.1 字符串字面量
字符串字面量需要使用单引号`'`引用，它可以包含任何字符(如果是单引号需要使用转义符`\'`)，例子：

```
<div th:text="'text'"></div>
```

#### 3.3.2 数字字面量
数字字面量不需要任何语法，直接使用就行，还可以进行算术运算，例子：

```
<div th:text="2020"></div>
<div th:text="2020+2"></div>
```

#### 3.3.3 布尔字面量
布尔字面量就是`true`和`false`，例子：

```
<div th:if="true"></div>
```

#### 3.3.4 空值字面量
空值字面量就是`null`，例子：

```
<div th:if="${user.happy} == null"></div>
```

#### 3.3.5 字面量标记
字面量标记允许在标准表达式中进行一些简化。它的工作方式与字符串字面量完全相同，但它只允许使用字母(`A-Z`和`a-z`)，数字(`0-9`)，方括号(`[`和`]`)，点(`.`)，连字符(`-`)和下划线(`_`)，没有空格和逗号等。数字、布尔、空值字面量实际上是字面量标记的特殊情况。

字面量标记不需要使用引号，例子：

```
<div th:class="content"></div>
```

### 3.4 操作和运算符
#### 3.4.1 字符串操作

- 字符串拼接：使用`+`操作符进行拼接

  ```
  <div th:text="'哈哈哈：' + ${user.name}"></div>
  ```

- 字符串替换：替换项必须用竖线`|`包围，只有变量/消息表达式(`${...}`，`*{...}`，`#{...}`)可以进行替换

  ```
  <div th:text="|哈哈哈：${user.name}|"></div>
  ```

#### 3.4.2 算术运算符
算术运算符包括：`+`，`-`，`*`，`/(div)`和`%(mod)`

#### 3.4.3 布尔运算符
布尔运算符包括：`&(and)`，`|(or)`，`!(not)`

#### 3.4.4 比较运算符
比较运算符包括：`>(gt)`，`<(lt)`，`>=(ge)`，`<=(le)`，`==(eq)`，`!=(neq/ne)`

#### 3.4.5 条件运算符

- `if-then：(if) ? (then)`

  简化的三元表达式，当if为false时，返回null，例子：

  ```
  <tr th:class="${row.even} ? 'even'"></tr>
  ```

- `if-then-else：(if) ? (then) : (else)`

  三元表达式，例子：

  ```
  <tr th:class="${row.even} ? 'even' : 'odd'"></tr>
  ```

- default：(value) ?: (defaultValue)

  默认值表达式，当value为null时，使用defaultValue的值，例子：

  ```
  <div th:text="${user} ?: 'nobody'"></div>
  ```

### 3.5 循环和条件
#### 3.5.1 循环
Thymeleaf提供了`th:each`属性用来进行循环操作，例子：

```
<div th:each="user, status : ${users}">
    <span th:text="${user.name}"></span>
    ->
    <span th:text="${status.index}"></span>
</div>
```

`th:each`属性可以迭代以下对象：

- 实现了java.util.Iterable的类
- 实现了java.util.Enumeration的类(枚举类)
- 实现了java.util.Iterator的类
- 实现了java.util.Map的类，当迭代Map时，迭代变量是java.util.Map.Entry类型
- 任何数组
- 其它可视为包含该对象本身的单值列表

使用`th:each`时，Thymeleaf提供了一种用于跟踪迭代状态的机制：`status变量`

`status变量`在`th:each`属性中定义，并且包含以下数据：

- `index`：当前的迭代索引，从0开始
- `count`：当前的迭代索引，从1开始
- `size`：迭代变量中的元素总数
- `current`：当前迭代的对象
- `even/odd`：当前迭代是偶数还是奇数
- `first/last`：当前迭代是第一个还是最后一个

#### 3.5.2 条件
Thymeleaf提供了`th:if`和`th:unless`属性用来进行条件判断，`th:unless`是与`th:if`相反的属性，例子：

```
<div th:if="true">true</div>
<div th:unless="false">true</div>
```

`th:if`除了按照表达式判断之外，还将按照以下规则进行判断：

- 判断为true：
  - 如果value是布尔值并且是true
  - 如果value是数字并且不为0
  - 如果value是一个字符并且不为'0'
  - 如果value是String类型并且不为"false"，"off"或"no"
  - 如果value不是以上种类但不为null
- 判断为false：
  - 如果value为null

#### 3.5.3 分支控制
Thymeleaf还提供了`th:switch`和`th:case`进行分支控制，例子：

```
<div th:switch="${user.sex}">
    <div th:case="1">男</div>
    <div th:case="0">女</div>
    <div th:case="*">未知</div>
</div>
```

其中`th:case="*"`为默认选项

## 4. 示例操作
运行项目后，浏览器访问`http://localhost:9001/page/demo`
