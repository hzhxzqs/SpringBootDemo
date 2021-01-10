# SpringBoot JSP

## 目录

- [1. 项目依赖](#1-项目依赖)
- [2. 项目配置](#2-项目配置)
- [3. 示例操作](#3-示例操作)

## 1. 项目依赖

```
<!-- 导入jsp依赖 -->
<!-- jsp引擎 -->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
    <scope>provided</scope>
</dependency>
<!-- servlet依赖 -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <scope>provided</scope>
</dependency>
<!-- jsp页面标签库，可选，需要在jsp中使用标签库时添加 -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
</dependency>
```

## 2. 项目配置

```
server:
  servlet:
    encoding:
      # 设置编码
      charset: UTF-8

spring:
  mvc:
    view:
      # 页面默认前缀
      prefix: /WEB-INF/jsp/
      # 页面默认后缀
      suffix: .jsp
```

## 3. 示例操作
本项目只包含一个简单的jsp页面，运行项目后，浏览器访问`http://localhost:9001/page/demo`
