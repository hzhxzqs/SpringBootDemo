# SpringBoot MyBatis

## 目录

- [1. MyBatis概念](#1-MyBatis概念)
- [2. 项目依赖](#2-项目依赖)
- [3. 项目配置](#3-项目配置)
- [4. 项目说明](#4-项目说明)
  - [4.1 DAO(Mapper)接口定义](#41-DAOMapper接口定义)
  - [4.2 XML映射文件](#42-XML映射文件)
  - [4.3 动态SQL](#43-动态SQL)
    - [4.3.1 if](#431-if)
    - [4.3.2 choose (when, otherwise)](#432-choose-when-otherwise)
    - [4.3.3 trim (where, set)](#433-trim-where-set)
    - [4.3.4 foreach](#434-foreach)
    - [4.3.5 接口类中使用动态SQL](#435-接口类中使用动态SQL)
- [5. 项目操作](#5-项目操作)

## 1. MyBatis概念
MyBatis是一款优秀的持久层框架，它支持自定义SQL、存储过程以及高级映射。MyBatis免除了几乎所有的JDBC代码以及设置参数和获取结果集的工作。MyBatis可以通过简单的XML或注解来配置和映射原始类型、接口和Java POJO(Plain Ordinary Java Objects，普通Java对象)为数据库中的记录。

## 2. 项目依赖

```
<!-- 导入mybatis依赖 -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.4</version>
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
    url: jdbc:mysql://localhost:3306/demo_mybatis?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true
    username: root
    password: root
  jackson:
    # 日期格式
    date-format: yyyy-MM-dd HH:mm:ss
    # 时区
    time-zone: GMT+8

mybatis:
  # mapper文件位置
  mapper-locations: classpath:mapper/*.xml
  # 开启驼峰命名
  configuration:
    map-underscore-to-camel-case: true

# 显示sql (zam.hzh.mybatis.dao是DAO接口包名)
logging:
  level:
    zam:
      hzh:
        mybatis:
          dao: debug
```

## 4. 项目说明
### 4.1 DAO(Mapper)接口定义
在接口上添加`@Mapper`注解标识该接口用于MyBatis映射，之后可以在映射文件中指定该接口的全限定名作为命名空间，该接口的方法名作为id并编写相关的sql语句，即可使用该接口定义的方法进行数据库操作。

```
/**
 * 添加@Mapper注解标识MyBatis映射
 */
@Mapper
public interface DemoDao {

    int addDemo(Demo demo);
}
```

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<!-- 接口全限定名作为命名空间 -->
<mapper namespace="zam.hzh.mybatis.dao.DemoDao">

    <!-- 接口方法名作为id -->
    <insert id="addDemo">
        insert into demo (create_date, demo_desc) value (#{createDate}, #{demoDesc})
    </insert>
</mapper>
```

也可以在接口的方法上使用`@Insert`、`@Delete`、`@Select`、`@Update`等注解并编写相关的sql语句，即可进行数据库操作。

```
/**
 * 添加@Mapper注解标识MyBatis映射
 */
@Mapper
public interface DemoDao {

    @Insert("insert into demo (create_date, demo_desc) value (#{createDate}, #{demoDesc})")
    int addDemo(Demo demo);
}
```

> 注意：以上两种指定sql语句的方法不能在同一方法上同时使用。

### 4.2 XML映射文件
MyBatis XML映射文件以`mapper`为根节点，通过指定`namespace`(命名空间)即可与DAO(Mapper)接口进行关联。

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<!-- 指定命名空间 -->
<mapper namespace="zam.hzh.mybatis.dao.DemoDao">
</mapper>
```

使用`insert`、`delete`、`select`、`update`等子节点，将接口方法名设为id来关联接口方法，即可调用接口方法进行数据库操作。

```
<insert id="addDemo">
    insert into demo (create_date, demo_desc) value (#{createDate}, #{demoDesc})
</insert>
```

使用`select`子节点时，需要使用`resultType`或`resultMap`指定结果返回类型或映射关系，以便MyBatis进行结果映射，`resultType`和`resultMap`之间只能同时使用一个。

```
<select id="findDemoById" resultType="zam.hzh.mybatis.model.Demo">
    select id, create_date, demo_desc
    from demo
    where id = #{id}
</select>
```

使用`insert`或`update`子节点时，如果数据库支持自动生成主键(比如MySQL和SQL Server)，可以将`useGeneratedKeys`设置为true，`keyProperty`设置为目标属性，MyBatis就可以把生成的主键赋值给目标属性，对于多列插入也是一样的操作。如果生成的列不只一个，可以用逗号分隔多个属性名称。

```
<insert id="addDemo" useGeneratedKeys="true" keyProperty="id">
    insert into demo (create_date, demo_desc) value (#{createDate}, #{demoDesc})
</insert>
```

> `#{}`与`${}`可接受的参数符号：
>
> 方法参数未使用`@Param`注解时：
> - 按参数顺序标识`param1, param2, ...`
> - 方法的`参数名`
> - 方法参数类型中的`属性名`，当多个类型中的属性名重复时，使用`参数名.属性名`区分
> - 对于List集合，可接受`参数名`、`collection`、`list`
> - 对于Set集合，可接受`参数名`、`collection`
> - 对于Map，可使用Map中的`key`
>
> 方法参数使用`@Param`注解时：
> - 按参数顺序标识`param1, param2, ...`
> - `@Param`中指定的`名称`或`名称.属性名`或`名称.key`(对于Map对象)
> - 对于List、Set集合只能接受参数顺序标识(`param1, param2, ...`)或`@Param`指定的`名称`

> `#{}`与`${}`的区别：
>
> - 默认情况下，使用`#{}`参数语法时，MyBatis会创建PreparedStatement参数占位符，并通过占位符安全地设置参数(就像使用`?`一样)
> - 如果想在SQL语句中直接插入一个不转义的字符串，则可以使用`${}`，如`select * from ${tablename}`，此时`${}`的值将直接替换并插入到语句中
> - 总之，使用`#{}`会使用`?`预处理，而使用`${}`会直接替换
> - 使用`${}`作为语句参数时是不安全的，会导致潜在的SQL注入攻击

### 4.3 动态SQL
动态SQL是MyBatis的强大特性之一。如果使用JDBC或其它类似框架，当需要根据条件进行SQL语句拼接时是非常痛苦的，例如拼接时需要确保不要缺失必要的空格，还要注意去掉逗号或and/or等。

使用动态SQL，可以彻底摆脱这种痛苦。XML映射文件中通过使用以下节点实现动态SQL：

- `if`
- `choose` (`when`, `otherwise`)
- `trim` (`where`, `set`)
- `foreach`

#### 4.3.1 if
`if`节点通过`test`条件判断是否为true，当为true时将节点中的内容拼接到SQL语句中，通常在where子句或单个插入数据时使用。

```
<select id="findDemos" resultType="zam.hzh.mybatis.model.Demo">
    select id, create_date, demo_desc
    from demo
    where create_date >= curdate()
    <if test="id != null">
        and id = #{id}
    </if>
    <if test="demoDesc != null">
        and demo_desc like concat('%', #{demoDesc}, '%')
    </if>
</select>
```

#### 4.3.2 choose (when, otherwise)
有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，可以使用`choose`节点(结合`when`、`otherwise`使用)，其类似于Java的switch语句。

```
<select id="findDemos" resultType="zam.hzh.mybatis.model.Demo">
    select id, create_date, demo_desc
    from demo
    where create_date >= curdate()
    <choose>
        <when test="id != null">
            and id = #{id}
        </when>
        <when test="demoDesc != null">
            and demo_desc like concat('%', #{demoDesc}, '%')
        </when>
        <otherwise>
            and ifnull(demo_desc, '') != ''
        </otherwise>
    </choose>
</select>
```

以上例子拼接策略为：当传入id就按id查找，当传入demoDesc就按demoDesc查找，当两者都没有传入时就查找demoDesc不为空的数据。

#### 4.3.3 trim (where, set)
使用`trim`节点可以灵活地设置拼接SQL语句的前后缀，以及需要删掉的额外部分，当节点中返回任何内容时自动删掉额外部分并加上前后缀之后再拼接到SQL语句上。`where`节点和`set`节点是特殊的`trim`节点。

```
<select id="findDemos" resultType="zam.hzh.mybatis.model.Demo">
    select id, create_date, demo_desc
    from demo
    <where>
        <if test="id != null">
            and id = #{id}
        </if>
        <if test="demoDesc != null">
            and demo_desc like concat('%', #{demoDesc}, '%')
        </if>
    </where>
</select>
```

如以上例子，当`where`节点中没有返回内容时，不会拼接where子句，即执行如下语句：

```
select id, create_date, demo_desc
from demo
```

只有在`where`节点中返回任何内容时，才会拼接where子句，同时自动删掉额外的and/or，假设demoDesc不为空，即执行如下语句：

```
select id, create_date, demo_desc
from demo
where demo_desc like concat('%', #{demoDesc}, '%')
```

和`where`节点等价的自定义`trim`节点为：

```
<trim prefix="where" prefixOverrides="and|or">
    ...
</trim>
```

上述例子会移除`prefixOverrides`属性中指定的内容(多个用管道符分隔)，并且插入`prefix`属性中指定的内容。

与`where`节点一样，`set`节点会动态地在行首插入set关键字，并删掉额外的逗号。

```
<update id="updateDemo">
    update demo
    <set>
        <if test="createDate != null">
            create_date = #{createDate},
        </if>
        <if test="demoDesc != null">
            demo_desc = #{demoDesc},
        </if>
    </set>
    where id = #{id}
</update>
```

和`set`节点等价的自定义`trim`节点为：

```
<trim prefix="set" suffixOverrides=",">
    ...
</trim>
```

当需要满足更多需求时，可以自定义`trim`节点：

```
<insert id="addDemo" useGeneratedKeys="true" keyProperty="id">
    insert into demo
    <trim prefix="(" suffix=")" suffixOverrides=",">
        <if test="createDate != null">
            create_date,
        </if>
        <if test="demoDesc != null">
            demo_desc,
        </if>
    </trim>
    value
    <trim prefix="(" suffix=")" suffixOverrides=",">
        <if test="createDate != null">
            #{createDate},
        </if>
        <if test="demoDesc != null">
            #{demoDesc},
        </if>
    </trim>
</insert>
```

#### 4.3.4 foreach
动态SQL的另一个常见使用场景是对集合进行遍历，例如：

```
<select id="findDemos" resultType="zam.hzh.mybatis.model.Demo">
    select id, create_date, demo_desc
    from demo
    where id in
    <foreach collection="list" item="id" index="index" separator="," open="(" close=")">
        #{id}
    </foreach>
</select>
```

`foreach`节点的功能非常强大，它允许指定一个集合，声明可以在节点内使用的集合项`item`和索引`index`变量。它也允许指定开头`open`与结尾`close`的字符串以及集合项迭代之间的分隔符`separator`，并且不会错误地添加多余的分隔符。

可以将任何可迭代对象(如List、Set对象等)、Map对象或者数组对象作为集合参数传递给`foreach`节点。当使用可迭代对象或者数组时，`index`是当前迭代的序号，`item`的值是本次迭代获取到的元素。当使用Map对象(或者Map.Entry对象的集合)时，`index`是键，`item`是值。

#### 4.3.5 接口类中使用动态SQL
要在带注解的Mapper接口类中使用动态SQL，可以使用`script`节点。比如：

```
@Select("<script>" +
        "select id, create_date, demo_desc " +
        "from demo " +
        "<where>" +
        "   <if test='id != null'>" +
        "       and id = #{id}" +
        "   </if>" +
        "   <if test='demoDesc != null'>" +
        "       and demo_desc like concat('%', #{demoDesc}, '%')" +
        "   </if>" +
        "</where>" +
        "</script>")
List<Demo> findDemos(Demo demo);
```

## 5. 项目操作
运行项目之前先在数据库执行resources/sql目录下的mybatis.sql文件

运行之后，浏览器访问`http://localhost:9001/page/demo`
