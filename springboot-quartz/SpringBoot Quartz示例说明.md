# SpringBoot Quartz

## 目录

- [1. 概述](#1-概述)
- [2. 项目依赖](#2-项目依赖)
- [3. Quartz基本使用](#3-Quartz基本使用)
  - [3.1 Job工厂类](#31-Job工厂类)
  - [3.2 Quartz配置类](#32-Quartz配置类)
  - [3.3 自定义Job作业](#33-自定义Job作业)
  - [3.4 启动Job](#34-启动Job)
- [4. Job注解](#4-Job注解)
- [5. cron表达式](#5-cron表达式)
  - [5.1 语法](#51-语法)
  - [5.2 字段含义](#52-字段含义)
  - [5.3 特殊字符](#53-特殊字符)
- [6. Quartz持久化](#6-Quartz持久化)
  - [6.1 Quartz存储](#61-Quartz存储)
  - [6.2 持久化配置](#62-持久化配置)
    - [6.2.1 创建数据库表](#621-创建数据库表)
    - [6.2.2 配置文件](#622-配置文件)
    - [6.2.3 配置属性](#623-配置属性)
    - [6.2.4 启动Job](#624-启动Job)
- [7. 使用集群](#7-使用集群)
  - [7.1 集群概念](#71-集群概念)
  - [7.2 集群配置](#72-集群配置)
- [8. 作业中断](#8-作业中断)
- [9. 作业事务](#9-作业事务)
- [10. 项目操作](#10-项目操作)

## 1. 概述
Quartz是一个功能强大的开源作业调度库，它几乎可以集成到任何Java应用程序中——小到独立的应用程序，大到电子商务系统。Quartz可用于创建各种调度，以执行成千上万的作业，这些作业可以执行编写的任何程序。Quartz还包含许多企业级功能，例如对JTA事务和集群的支持。

## 2. 项目依赖

```
<!-- 导入quartz依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
```

## 3. Quartz基本使用
下面通过几个步骤开始使用Quartz作业调度。

### 3.1 Job工厂类

```
/**
 * 继承SpringBeanJobFactory创建Job工厂类
 */
@Configuration
public class QuartzJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * 创建job实例
     *
     * @param bundle TriggerFiredBundle
     * @return Object
     * @throws Exception Exception
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类方法创建job实例
        Object jobInstance = super.createJobInstance(bundle);
        // 注入到spring容器中
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
```

### 3.2 Quartz配置类

```
/**
 * quartz配置类
 */
@Configuration
public class QuartzConfigure {

    @Autowired
    private QuartzJobFactory quartzJobFactory;

    /**
     * SchedulerFactoryBean提供对org.quartz.Scheduler的创建与配置，并且会管理它的生命周期与Spring同步。
     *
     * @return SchedulerFactoryBean
     * @throws IOException Exception
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // 设置自动启动
        factory.setAutoStartup(true);
        // 设置Job工厂类
        factory.setJobFactory(quartzJobFactory);
        return factory;
    }

    /**
     * 调度器，所有的调度都是由它控制
     *
     * @return Scheduler
     * @throws IOException Exception
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }
}
```

### 3.3 自定义Job作业
通过实现`Job`接口并重写`execute()`方法，即可编写需要该作业执行的逻辑。

```
/**
 * 自定义作业1
 *
 * <br>实现Job接口并重写execute()方法
 */
public class Demo1Job implements Job {

    /**
     * 作业执行体
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job1作业执行");
    }
}
```

使用`JobExecutionContext`参数可以获取创建作业调度时传入的附加数据，以此为作业逻辑提供必要的数据。

```
/**
 * 自定义作业2
 *
 * <br>使用JobExecutionContext获取JobDataMap来获取传入的数据
 */
public class Demo2Job implements Job {

    /**
     * 作业执行体
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job2正在执行");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        for (String key : jobDataMap.getKeys()) {
            System.out.print(key + " -> ");
            System.out.println(jobDataMap.get(key));
        }
        System.out.println("Job2执行完成");
    }
}
```

### 3.4 启动Job
在`QuartzUtil`已封装了启动/停止作业调度的方法，可以很简单地启动/停止作业。

## 4. Job注解
在Job作业类上可以添加一些注解，用来规定作业执行的行为，主要有以下几个注解：

- `@DisallowConcurrentExecution`
  - 表示对于同一个`JobKey`，该Job作业不能并行执行
  - 例如：Job每10秒执行新作业，但作业执行需要11秒，该Job的其它作业需要等待当前作业执行完才能执行，否则其它作业直接执行
  - 一般用于相同`JobKey`且多次或间隔执行的作业，对于只执行一次的作业没有意义
  - 注意：`org.quartz.threadPool.threadCount`线程池中线程的数量至少要多个，如果只设置一个那么该注解不能发挥作用，因为本身就只有一个线程
  - 例子见`Demo3Job`

- `@PersistJobDataAfterExecution`
  - 表示作业在执行过程中可以更新`JobDataMap`，并且在执行完成后重新保存`JobDataMap`
  - 该注解可以将本次执行过程的数据或执行结果传入下一次作业中，可以用于作业间的数据传递
  - 通常与`@DisallowConcurrentExecution`注解一起使用，可以避免并发性问题
  - 例子见`Demo4Job`

- `@ExecuteInJTATransaction`
  - 表示该Job作业将由JTA事务包裹执行
  - 当添加该注解时，Quartz将在`execute()`方法执行之前开启JTA事务，当方法没有抛出异常或没有调用事务的`setRollbackOnly()`方法时提交事务，否则回滚事务
  - 该注解的行为与`org.quartz.scheduler.wrapJobExecutionInUserTransaction`设置为true相似，不同点在于前者只影响添加了该注解的Job作业，后者影响所有Job作业。当后者已设置为true的情况下，就不需要再添加该注解了

## 5. cron表达式
### 5.1 语法

```
second minute hour dayOfMonth month dayOfWeek year?
```

### 5.2 字段含义

- 秒(`second`)，0-59整数，特殊字符：`, - * /`
- 分(`minute`)，0-59整数，特殊字符：`, - * /`
- 时(`hour`)，0-23整数，特殊字符：`, - * /`
- 日期(`dayOfMonth`)，1-31整数（视对应月份天数而定），特殊字符：`, - * / ? L W C`
- 月份(`month`)，1-12整数或JAN-DEC，特殊字符：`, - * /`
- 星期(`dayOfWeek`)，1-7整数或SUN-SAT，特殊字符：`, - * / ? # L C`
- 年(`year`)，1970-2099整数，特殊字符：`, - * /`，可选

### 5.3 特殊字符

- `,`：列出枚举值
  - 如`second`使用`10,30`表示第10秒和第30秒执行
- `-`：范围
  - 如`second`使用`10-30`表示从第10秒开始到第30秒结束每秒执行
- `*`：任意值
  - 如`second`使用`*`表示每秒执行
- `/`：起始时间触发，每间隔固定时间再次触发，直到其大一级单位结束才重新开始
  - 如`second`使用`10/25`表示每分钟从第10秒开始执行，之后每间隔25秒再次执行，直到当前分钟结束。也就是当前分钟在第10秒和第35秒执行，下一分钟也是在这两个时间执行，在第0秒时并不会执行，因为它属于下一分钟，并且每分钟在第10秒才开始执行
- `?`：任意值，只能在`dayOfMonth`和`dayOfWeek`使用，且必须有一个使用，但不能同时使用
  - 如`dayOfMonth`使用`20`，`dayOfWeek`使用`?`表示每月20号执行，不论当天是星期几
- `L`：最后，只能在`dayOfMonth`和`dayOfWeek`使用
  - 如`dayOfWeek`使用`5L`表示在每月最后一个星期四执行；
  - `dayOfMonth`通常使用`L`表示最后一天
- `W`：有效工作日(周一至周五)，将在离指定日期最近的工作日执行，只能在`dayOfMonth`使用
  - 如`dayOfMonth`使用`5W`
    - 若当月5号是星期六，则星期五执行，即4号执行
    - 若是星期日，则星期一执行，即6号执行
    - 若是工作日，则当天执行
  - 不会跨过月份
    - 如使用`30W`，30号是某月的最后一天而且是星期日，则在星期五执行，即28号执行
- `LW`：`L`和`W`可以连用，只能在`dayOfMonth`使用
  - 如`dayOfMonth`使用`LW`表示每月最后一个工作日执行
- `#`：每月第几个星期几，只能在`dayOfWeek`使用
  - 如`dayOfWeek`使用`4#2`表示在每月第2个星期三执行
- `C`：Calender的意思，只能在`dayOfMonth`和`dayOfWeek`使用
  - 需要关联日历，如果没有则相当于所有日历
  - 如`dayOfMonth`使用`5C`表示每月5号执行，`dayOfWeek`使用`1C`表示每个星期日执行

> 注：cron表达式对大小写不敏感

## 6. Quartz持久化
在前面Quartz基本使用中，已经可以通过几个步骤实现作业的调度。但是此时没有为Quartz配置数据库相关的数据源，Quartz使用的调度数据(Job、Trigger、Calender和相关数据)存储实现类是`RAMJobStore`，其产生的调度数据只保存在内存中，也就是说当程序停止后，所有的调度数据也一起清除了。

为了保存调度数据，以便下次启动程序时继续使用，可以为Quartz配置数据源，将调度数据持久化到数据库中。下面简单介绍Quartz持久化配置及使用。

### 6.1 Quartz存储
关于Quartz存储机制，其有一个统一的接口`JobStore`。该接口负责保存提供给调度器的调度数据(Job、Trigger、Calender和相关数据)，其有几个实现类，`RAMJobStore`就是该接口的实现类之一。

选择合适的实现类是非常重要的一步，可以在配置文件中配置`org.quartz.jobStore.class`指定实现类。这里简单介绍几个实现类：

- `org.quartz.simpl.RAMJobStore`：`RAMJobStore`的使用是最简单的，性能也是最好的。它把所有数据保存在内存中，因此它非常快速，但是这也导致了它的数据不能持久化，当程序停止时所有的数据都会丢失。

- `org.quartz.impl.jdbcjobstore.JobStoreTX`：`JobStoreTX`把数据保存在关系型数据库中。它在每次操作(如添加作业)后，由自身处理事务的提交和回滚。其适合用于独立应用或没有使用JTA事务的Servlet容器中。

- `org.quartz.impl.jdbcjobstore.JobStoreCMT`：`JobStoreCMT`把数据保存在关系型数据库中。它的事务操作依赖于应用管理的事务，在定义或取消作业之前必须运行JTA事务。`JobStoreCMT`通常需要使用两个数据源，一个是由应用管理JTA事务的数据源，另一个是不参与全局JTA事务的数据源。其适合用于使用JTA事务的应用。

### 6.2 持久化配置
实现持久化配置主要有几个步骤：

1. 创建数据库表
2. 配置Quartz配置文件
3. `SchedulerFactoryBean`设置配置属性

#### 6.2.1 创建数据库表
为了实现持久化，所以需要将Quartz运行过程中产生的数据保存到数据库中。Quartz提供了所有被支持的数据库平台的sql脚本，具体见： [quartz数据库脚本](https://github.com/quartz-scheduler/quartz/tree/master/quartz-core/src/main/resources/org/quartz/impl/jdbcjobstore)

这里使用`tables_mysql_innodb.sql`创建数据库表。

#### 6.2.2 配置文件
默认情况下，`StdSchedulerFactory`会从当前工作目录下加载`quartz.properties`文件，如果加载失败，就从org/quartz包下加载默认的`quartz.properties`文件。为了自定义配置，可以在项目的类加载路径下创建`quartz.properties`文件，主要配置线程池、JobStore、和数据源。

```
#==================================================
# 配置线程池
#==================================================

# ThreadPool实现类
org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
# 线程数量
org.quartz.threadPool.threadCount=20
# 线程优先级（1~10）
org.quartz.threadPool.threadPriority=5

#==================================================
# 配置JobStore
#==================================================

# 容许的最大作业延迟时间
org.quartz.jobStore.misfireThreshold=60000
# JobStore实现类
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
# 数据库代理类
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
# Quartz内部表的前缀
org.quartz.jobStore.tablePrefix=QRTZ_
# 数据源名称
org.quartz.jobStore.dataSource=quartzDS

#==================================================
# 配置数据源
#==================================================

# 数据库驱动
org.quartz.dataSource.quartzDS.driver=com.mysql.cj.jdbc.Driver
# 数据库url
org.quartz.dataSource.quartzDS.URL=jdbc:mysql://localhost:3306/demo_quartz?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true
# 数据库用户
org.quartz.dataSource.quartzDS.user=root
# 数据库密码
org.quartz.dataSource.quartzDS.password=root
# 数据库最大连接数
org.quartz.dataSource.quartzDS.maxConnections=10
# 数据库连接池实现，Quartz内置有c3p0和hikaricp
org.quartz.dataSource.quartzDS.provider=hikaricp
```

#### 6.2.3 配置属性
在Quartz配置类中读取配置文件属性，并为`SchedulerFactoryBean`设置配置属性。

```
@Configuration
public class QuartzConfigure {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setAutoStartup(true);
        factory.setJobFactory(quartzJobFactory);
        // 设置属性
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    /**
     * 从quartz.properties读取属性配置
     *
     * @return Properties
     * @throws IOException Exception
     */
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // 设置配置文件位置
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
    
    //...省略其它内容...
}
```

#### 6.2.4 启动Job
启动项目后，启动任意Job作业，可以在数据库中Quartz表看到记录了相关的调度数据。

停止项目并重新启动后，未执行完的Job作业仍然继续执行。

## 7. 使用集群
上面介绍了Quartz持久化，但是此时Quartz还是单机模式，当作业量比较大或作业比较耗时时，比较消耗机器资源。对此，Quartz提供了集群部署，通过故障转移和负载均衡功能，为调度程序提供高可用性和可扩展性。下面简单介绍Quartz集群。

### 7.1 集群概念

目前Quartz集群只适用于`JDBC-JobStore`(`JobStoreTX`或`JobStoreCMT`)。在Quartz集群中，独立的Quartz节点并不与其它节点或管理节点通信，而是通过相同的数据库表来感知到其它的Quartz应用，简单地说，就是Quartz集群中的节点通过连接到同一数据库上，以此形成集群。

Quartz集群自动进行负载均衡，使集群中的每个节点尽快执行作业。当作业触发时间到达时，第一个节点将会获得作业并执行。每次触发时只有一个节点执行作业，执行的节点也具有一定的随机性。举个例子，一个作业每隔10秒触发，这一次是这节点执行，下一次可能是其它节点执行。也就是说，同一个作业并不是总是由同一节点执行的，它或多或少具有一定的随机性。对于特别繁忙的调度，负载均衡几乎是随机的；对于不太繁忙的调度，其更倾向于使用相同节点。

当节点在执行一个或多个作业时发生故障，就会进行故障转移。当节点发生故障时，其它节点检测到该情况，会确定数据库中正在由故障节点执行的作业，并根据执行情况进行标记。被标记为恢复的作业会由其它节点重新执行，没有标记的作业将会在下一次触发时被释放。

Quartz集群适合长期运行或占用大量CPU的作业，其可以将工作负载分配到多个节点上。如果需要扩展以支持数千个短时作业，可以考虑使用多个不同的调度程序对作业进行分区。

> 注意：
> - 不要将集群部署到不同的机器上，除非可以将机器之间的时间差控制在1秒内。
> - 不要在集群使用的数据库表中加入非集群实例，可能会产生严重的数据损坏，并且会遇到不稳定的行为。

### 7.2 集群配置
只需要在配置文件中加入以下配置，就可以使用集群了。

```
#==================================================
# 配置集群
#==================================================
# 调度标识名，集群中每一个实例都必须使用相同的名称
org.quartz.scheduler.instanceName=DefaultQuartzScheduler
# ID，设置为自动获取，集群中每一个实例id必须不同
org.quartz.scheduler.instanceId=AUTO
# 加入集群
org.quartz.jobStore.isClustered=true
# 集群实例间互相检测的时间（以毫秒为单位）
org.quartz.jobStore.clusterCheckinInterval=15000
```

## 8. 作业中断
有时候，需要停止一个正在执行的作业，不想让它执行完成。但是，执行了停止作业方法后，作业依然会直到执行完成才从调度作业中移除。这时候，可以在Job类中设置一个中断标记，在作业逻辑中通过中断标记判断作业是否需要中断，以此进行对应的操作。

那么，怎么去操作这个中断标记呢？可以让Job类实现`InterruptableJob`接口。该接口继承了`Job`接口，除了可以实现作业逻辑之外，还提供了`interrupt()`方法，这个方法就可以用来操作中断标记。当`Scheduler`调用了`interrupt(JobKey)`方法时，就会调用对应`JobKey`的`InterruptableJob`实现类中的`interrupt()`方法，此时可以修改中断标记了。

> 注意：对于非`InterruptableJob`实现类，对其使用`Scheduler.interrupt(JobKey)`方法会报错，所以在调用之前可以判断Job执行类是否实现了`InterruptableJob`接口：`InterruptableJob.class.isAssignableFrom(scheduler.getJobDetail(jobKey).getJobClass())`

```
/**
 * 自定义作业5
 *
 * <br>实现InterruptableJob接口及其方法，进行作业中断
 */
public class Demo5Job implements InterruptableJob {

    // 中断标记
    private boolean isInterrupted = false;

    /**
     * 当调用Scheduler.interrupt(JobKey)方法时，执行该方法
     *
     * @throws UnableToInterruptJobException 中断异常
     */
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        isInterrupted = true;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job5正在执行");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 根据中断标记判断是否中断
        if (isInterrupted) {
            System.out.println("Job5作业中断");
        } else {
            System.out.println("Job5执行完成");
        }
    }
}
```

使用作业中断可以实现额外的操作，例如进行事务控制(通过中断标记判断是提交或回滚)等。

## 9. 作业事务
在以上的Quartz使用中，使用过`RAMJobStore`和`JobStoreTX`两种`JobStore`。`RAMJobStore`本身与事务没有任何关联，而`JobStore`虽然说有自动提交或回滚事务的行为，但它仅仅只限于操作Quartz表(如添加作业)时才有效。也就是说，对于作业逻辑并没有事务控制，当作业逻辑产生异常时不能对操作的业务表进行回滚操作，这将导致数据出错或产生其它影响。

为了能进行事务控制，有一种方法是额外定义一个Service类，作业逻辑由Service类中的方法实现，再结合`@Transactional`注解即可进行事务控制。但是这种方法在作业逻辑比较多的情况下，Service类中需要定义各种方法，违背了单一职责原则。

实际上，我们可以使用Spring的事务对作业逻辑进行手动事务控制。这里结合之前的作业中断，实现事务管理。

```
/**
 * 自定义作业6
 *
 * <br>结合作业中断和手动事务，实现事务管理
 */
@Component
public class Demo6Job implements InterruptableJob {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Autowired
    private DemoDAO demoDAO;

    private boolean isInterrupted = false;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        isInterrupted = true;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job6正在执行");
        // 开启事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        demoDAO.save(new Demo());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isInterrupted) {
            // 提交事务
            platformTransactionManager.commit(transaction);
            System.out.println("Job6正常完成");
        } else {
            // 回滚事务
            platformTransactionManager.rollback(transaction);
            System.out.println("Job6异常回滚");
        }
    }
}
```

对于产生异常时的事务管理，只需要在适当的地方进行commit或rollback即可。

## 10. 项目操作
项目启动之前创建`demo_quartz`数据库，并执行`tables_mysql_innodb.sql`

启动之后可以请求类似链接`http://localhost:9001/demo/start1`

从`start1`到`start6`分别对应启动作业1到作业6，`stopAll`停止所有作业。其中，请求`start5`或`start6`之后，可以在作业执行过程中停止作业，查看作业中断或事务回滚
