# SpringBoot JPA

## 目录

- [1. JPA概念](#1-JPA概念)
- [2. 项目依赖](#2-项目依赖)
- [3. 项目配置](#3-项目配置)
- [4. 项目说明](#4-项目说明)
  - [4.1 DDL模式](#41-DDL模式)
  - [4.2 实体类定义](#42-实体类定义)
  - [4.3 DAO定义](#43-DAO定义)
- [5. 示例操作](#5-示例操作)

## 1. JPA概念
JPA(Java Persistence API)，Java持久层API，是JDK 5.0注解或XML描述对象－关系表的映射关系，并将运行期的实体对象持久化到数据库中。

## 2. 项目依赖

```
<!-- 导入jpa依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!-- 导入mysql依赖 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

## 3. 项目配置

```
spring:
  # 配置数据库
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo_jpa?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=utf-8
    username: root
    password: root
  jpa:
    hibernate:
      # ddl模式
      ddl-auto: update
    # 显示sql
    show-sql: true
  jackson:
    # 日期格式
    date-format: yyyy-MM-dd HH:mm:ss
    # 时区
    time-zone: GMT+8
```

## 4. 项目说明
### 4.1 DDL模式
`spring.jpa.hibernate.ddl-auto`是Hibernate的配置属性，其主要作用是：自动创建、更新、验证数据库表结构，包含以下几种配置：

```
none：不进行任何操作
validate：检查表结构是否一致，否则报错
update：自动创建及更新表结构，保留已存在数据
create：自动创建表结构，丢弃原有表结构及数据
create-drop：自动创建表结构，会话结束时丢弃表结构及数据
```

### 4.2 实体类定义
在类上添加`@Entity`注解标识该类是实体类，在首次运行项目时，将会在数据库中自动创建与该类相对应的数据库表。

实体类中常用注解：

- `@Entity`：表示该类是实体类，首次运行项目时可自动创建与该类相对应的数据库表，如果没有该注解将不会创建表。创建表时表名是该类名的下划线形式，也可以通过注解的`name`属性指定表名，如：`@Entity(name = "table_name")`

- `@Table`：可以指定表名、唯一性约束、索引等

- `@Id`：标识该属性为表的主键，使用`@Entity`注解时必须标识主键

- `@GenericGenerator`：为生成器指定唯一名称，以便`@GeneratedValue`注解使用。通过`name`属性指定生成器名称，通过`strategy`属性指定生成策略，生成策略可使用Hibernate提供的默认生成器，也可自定义生成器。Hibernate提供的默认生成器如下：

  ```
  public DefaultIdentifierGeneratorFactory() {
      register( "uuid2", UUIDGenerator.class );
      register( "guid", GUIDGenerator.class );         // can be done with UUIDGenerator + strategy
      register( "uuid", UUIDHexGenerator.class );      // "deprecated" for new use
      register( "uuid.hex", UUIDHexGenerator.class );  // uuid.hex is deprecated
      register( "assigned", Assigned.class );
      register( "identity", IdentityGenerator.class );
      register( "select", SelectGenerator.class );
      register( "sequence", SequenceStyleGenerator.class );
      register( "seqhilo", SequenceHiLoGenerator.class );
      register( "increment", IncrementGenerator.class );
      register( "foreign", ForeignGenerator.class );
      register( "sequence-identity", SequenceIdentityGenerator.class );
      register( "enhanced-sequence", SequenceStyleGenerator.class );
      register( "enhanced-table", TableGenerator.class );
  }
  ```

- `@GeneratedValue`：为主键指定id生成策略，结合`@Id`注解使用。通过`strategy`属性指定id生成策略，通过`generator`属性指定id生成器，可使用`@GenericGenerator`注解的`name`属性指定的名称以启用对应的id生成器。id生成策略默认有以下四种：

  ```
  public enum GenerationType {
  
      /**
       * 使用特定的数据库表保存其它数据库表的主键并为其生成主键，
       * 结合@TableGenerator注解使用
       */
      TABLE,
  
      /**
       * 使用底层数据库序列生成主键，需要数据库支持序列，Oracle支持，MySQL不支持，
       * 结合@SequenceGenerator注解使用
       */
      SEQUENCE,
  
      /**
       * 使用数据库自增主键，MySQL支持，Oracle不支持
       */
      IDENTITY,
  
      /**
       * 默认的id生成策略，由JPA根据数据库选择合适的id生成策略，
       * Oracle是序列化，MySQL是自增主键
       */
      AUTO
  }
  ```

- `@Column`：可以指定属性名、属性定义等

- `@Temporal`：保存日期(Date)类型属性时调整精度，包含`DATE`、`TIME`、`TIMESTAMP`三种精度

- `@Transient`：创建数据库表及保存数据时忽略该属性

### 4.3 DAO定义
通过定义DAO接口并继承`JpaRepository`接口即可使用继承的方法进行数据库表操作，也可以自定义方法进行数据库表操作，如：

```
List<Demo> findByDemoDesc(String demoDesc);
```

DAO接口中常用注解：

- `@Query`：用于自定义查询语句，如：

  ```
  @Query("select d from Demo d where d.demoDesc = ?1")
  List<Demo> findByDemoDescQuery(String demoDesc);
  ```

  默认情况下，JPA使用基于位置的参数绑定，如以上例子所示。当重构有关参数位置的查询时，容易导致查询方法出错。为了解决此问题，可以使用`@Param`注解为方法参数命名，然后在查询中绑定该名称，如：

  ```
  @Query("select d from Demo d where d.demoDesc = :demoDesc")
  List<Demo> findByDemoDescNamed(@Param("demoDesc") String demoDesc);
  ```

  自定义的查询语句还可以使用对应数据库的自然语言，只需将`@Query`注解的`nativeQuery`设置为`true`，如：
  ```
  @Query(value = "select * from demo where demo_desc = ?1", nativeQuery = true)
  List<Demo> findByDemoDescNative(String demoDesc);
  ```

- `@Modifying`：当自定义的查询语句是`INSERT`、`UPDATE`、`DELETE`和`DDL`语句时，需要使用该注解标记，结合`@Query`使用，如：

  ```
  @Modifying
  @Query("update Demo d set d.demoDesc = ?1 where d.id = ?2")
  int updateDemoDescById(String demoDesc, String id);
  
  @Modifying
  @Query("delete from Demo d where d.id = ?1")
  void deleteDemoById(String id);
  ```

## 5. 示例操作
运行项目之前先创建`demo_jpa`数据库，首次启动时该项目自动创建数据库表

运行之后，浏览器访问`http://localhost:9001/page/demo`
