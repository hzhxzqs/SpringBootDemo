# SpringBoot Shiro

## 目录

- [1. Shiro概念](#1-Shiro概念)
  - [1.1 什么是Shiro](#11-什么是Shiro)
  - [1.2 Shiro功能](#12-Shiro功能)
  - [1.3 Shiro架构](#13-Shiro架构)
    - [1.3.1 高层概述](#131-高层概述)
    - [1.3.2 详细架构](#132-详细架构)
- [2. 项目依赖](#2-项目依赖)
- [3. 项目说明](#3-项目说明)
  - [3.1 表说明](#31-表说明)
  - [3.2 用户密码加密](#32-用户密码加密)
- [4. Shiro核心配置](#4-Shiro核心配置)
  - [4.1 Realm配置](#41-Realm配置)
    - [4.1.1 认证匹配器](#411-认证匹配器)
    - [4.1.2 缓存管理器](#412-缓存管理器)
    - [4.1.3 配置Realm](#413-配置Realm)
    - [4.1.4 权限注解支持](#414-权限注解支持)
  - [4.2 SecurityManager配置](#42-SecurityManager配置)
  - [4.3 Shiro过滤器](#43-Shiro过滤器)
    - [4.3.1 过滤器和过滤链](#431-过滤器和过滤链)
    - [4.3.2 默认配置](#432-默认配置)
    - [4.3.3 自定义配置](#433-自定义配置)
- [5. Shiro使用](#5-Shiro使用)
  - [5.1 用户登录/退出](#51-用户登录退出)
  - [5.2 密码重试限制](#52-密码重试限制)
  - [5.3 权限控制](#53-权限控制)
- [6. 项目操作](#6-项目操作)

## 1. Shiro概念
### 1.1 什么是Shiro
Apache Shiro是一种功能强大且易于使用的Java安全框架，可执行身份验证、授权、加密和会话管理，可用于保护任何应用程序的安全——从小型的命令行应用程序、移动应用程序到大型的Web应用和企业级应用程序。

Shiro提供了应用程序安全性API来操作以下方面(应用程序安全性的4个基石)：

- 身份验证 - 证明用户身份，通常称为用户“登录”
- 授权 - 访问控制
- 加密 - 保护或隐藏数据以防窥视
- 会话管理 - 每个用户的时间敏感状态

Shiro还支持一些辅助功能，例如Web应用程序安全，单元测试和多线程支持，但它们的存在是为了加强上述四个主要方面。

### 1.2 Shiro功能

![Shiro功能](assets/image/ShiroFeatures.png)

- `Authentication`(身份验证)：也称为“登录”
- `Authorization`(授权)：访问控制的过程，即确定“谁”有权访问“什么”
- `Session Management`(会话管理)：即使在非Web或EJB应用程序中，也可以管理用户特定的会话
- `Cryptography`(加密)：使用密码算法保证数据安全，同时仍然易于使用

在不同的应用程序环境中，还具有其他功能来支持和加强这些关系，例如：

- `Web Support`(Web支持)：Shiro的Web支持API可帮助轻松保护Web应用程序
- `Caching`(缓存)：缓存是Apache Shiro API的第一层公民，可确保安全操作保持快速有效
- `Concurrency`(并发性)：Apache Shiro的并发功能支持多线程应用程序
- `Testing`(测试)：测试支持可以帮助编写单元测试和集成测试，并确保代码受到预期保护
- `"Run As"`(运行方式)：一种允许用户使用其他用户的身份（如果允许）的功能，有时在管理方案中很有用
- `Remember Me`(记住我)：在各个会话中记住用户的身份，因此他们仅在必要时登录

### 1.3 Shiro架构
#### 1.3.1 高层概述

在最高概念层次，Shiro的架构有3个主要的概念：`Subject`、`SecurityManager`和`Realm`。下图是这些组件如何交互的高级概述，我们将在下面介绍每个概念：

![Shiro基本架构](assets/image/ShiroBasicArchitecture.png)

- `Subject`：本质上是当前正在执行的用户的安全特定“视图”。“用户”通常表示一个人；`Subject`可以是一个人，但它也可以表示第三方服务、守护程序帐户、cron作业或任何类似的东西——基本上是当前与该软件交互的任何东西。

  `Subject`实例需要绑定到(必须)一个`SecurityManager`。当与`Subject`交互时，这些交互将会转化为与`SecurityManager`之间的特定`Subject`的交互。

- `SecurityManager`：Shiro架构的核心，充当一种“伞”对象，协助内部安全组件共同组成对象图。一旦为应用程序配置了`SecurityManager`及其内部对象图，开发人员就可以忽视它了，把时间用在Subject API开发上。

- `Realm`：在Shiro和应用程序的安全数据之间充当“桥”或“连接器”的角色。当操作安全数据时，例如用户账号认证和授权，Shiro会在应用程序中配置的一个或多个`Realm`查找这些数据。

  从这方面来看，`Realm`本质上是一个特定安全操作的DAO：它封装了与数据源之间的连接细节，并给Shiro提供所需的相关数据。配置Shiro时，至少需要定义一个`Realm`以便使用认证和授权操作。`SecurityManager`可以配置多个`Realm`，但必须至少配置一个。

  Shiro提供了开箱即用的`Realm`用以连接各种安全数据源，例如LDAP、JDBC，文本配置源例如INI和配置文件，以及其它数据源。如果默认的`Realm`不能满足需要时，可以添加自己的`Realm`实现以支持自定义数据源。

  与其它内部组件一样，Shiro `SecurityManager`管理`Realm`如何为`Subject`实例提供安全数据。

#### 1.3.2 详细架构

![Shiro架构](assets/image/ShiroArchitecture.png)

- `Subject`：当前与系统交互的实体(用户、第三方服务、cron任务等)的一个特定安全视图。
- `Security Manager`：Shiro架构的核心，它协调它所管理的组件，确保它们正常工作。它也管理每个用户的Shiro视图，所以它知道如何为每个用户提供安全操作。
- `Authenticator`：负责执行用户认证操作。当用户尝试登录，`Authenticator`将处理它的逻辑操作。`Authenticator`知道如何协调存储用户相关信息的一个或多个`Realm`。从`Realm`得到的数据将用于验证用户的身份并确保身份正确。
  - `Authentication Strategy`：当配置了超过一个的`Realm`，`Authentication Strategy`将根据`Realm`的结果去决定认证操作是否成功。(例如，当一个`Realm`成功而其它的失败，这个操作是否成功？需要所有的`Realm`都成功吗？还是只要一个成功就行？)
- `Authorizer`：负责决定用户的访问控制，换句话说，就是用户是否被允许操作。`Authorizer`也知道如何去协调多终端数据源，以便获取角色和权限信息。`Authorizer`使用这些信息去决定用户是否允许执行给定的操作。
- `Session Manager`：负责创建和管理用户的会话生命周期，并在所有的环境中提供强大的会话体验。这是安全领域中的一个独特特性——Shiro可以在任何环境中管理用户会话，即使没有Web/Servlet或EJB容器支持。默认情况下，Shiro将使用已有的会话机制，如果没有，例如独立的应用或非Web环境，它将使用内置的企业级会话管理提供相同的编程体验。
  - `Session DAO`：负责`Session Manager`的会话持久化操作，允许任何数据保存到会话管理结构中。
- `Cache Manager`：负责创建和管理缓存实例的生命周期。因为Shiro可以访问多种终端数据源来进行认证、授权和会话管理，当使用这些数据源时，缓存通常是用于提高性能的一级架构。任何开源或企业级的缓存产品都可以嵌入到Shiro中以提供一个快速又有效的用户体验。
- `Cryptography`：企业级安全框架的补充。Shiro的`crypto`包包含易于使用和理解的密码学编码、哈希和不同的编解码实现。Shiro加密API简化了复杂的Java机制并使加密更容易使用。
- `Realm`：当操作与安全相关的数据时，Shiro从配置的一个或多个`Realm`中获取这些信息。可以按需配置多个`Realm`，Shiro将会协调它们的认证和授权工作。

## 2. 项目依赖

```
<!-- 导入shiro依赖 -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-web-starter</artifactId>
    <version>1.6.0</version>
</dependency>
```

## 3. 项目说明
### 3.1 表说明
该项目至少需要建立5张表，用户表、角色表、权限表、用户和角色关联表、角色和权限关联表。其中用户和角色之间、角色和权限之间是多对多关系。(JPA会自动建表，相关表内容可以看`model`包下的实体类)

### 3.2 用户密码加密
通常情况下，保存到数据库的用户密码需要加密之后才能保存，以保证账号的安全。Shiro提供了一套方便使用的加密算法，这里使用`SimpleHash`类对密码进行加密，见`encryptPassword()`方法，使用MD5加密并加密2次。

```
/**
 * 密码操作类
 */
public class PasswordHelper {

    // 加密算法
    public static final String ALGORITHM_NAME = "md5";

    // 加密次数
    public static final int HASH_ITERATIONS = 2;

    /**
     * 加密密码
     *
     * @param password 密码
     * @param salt     盐值
     * @return 已加密密码
     */
    public static String encryptPassword(String password, String salt) {
        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    }

    /**
     * 设置用户密码
     *
     * @param user 用户
     */
    public static void setPassword(SysUser user) {
        RandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        // 使用随机值做盐值
        user.setSalt(secureRandomNumberGenerator.nextBytes().toHex());
        String newPassword = encryptPassword(user.getPassword(), user.getCredentialSalt());
        user.setPassword(newPassword);
    }

    /**
     * 检查用户密码是否匹配
     *
     * @param user          用户
     * @param checkPassword 检查密码
     * @return 是否匹配
     */
    public static boolean checkPassword(SysUser user, String checkPassword) {
        String encryptPassword = encryptPassword(checkPassword, user.getCredentialSalt());
        return encryptPassword.equals(user.getPassword());
    }

    /**
     * 更新用户密码
     *
     * @param user            用户
     * @param oldPassword     原密码
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @throws MyException 原密码错误或密码不一致异常
     */
    public static void updatePassword(SysUser user, String oldPassword, String newPassword, String confirmPassword) 
            throws MyException {
        if (checkPassword(user, oldPassword)) {
            throw new MyException(MsgEnum.OLD_PASSWORD_ERROR);
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new MyException(MsgEnum.PASSWORD_CONFIRM_ERROR);
        }
        user.setPassword(newPassword);
        setPassword(user);
    }
}
```

## 4. Shiro核心配置
前面简单介绍了Shiro，知道它有3个主要部分：`Subject`、`SecurityManager`和`Realm`。`Subject`是由Shiro生成的，不需要进行配置，而`SecurityManager`需要绑定`Realm`，`Realm`需要提供相关安全数据，这两部分就需要我们进行配置了，下面将介绍如何配置。

### 4.1 Realm配置
`SecurityManager`需要绑定`Realm`，以便通过`Realm`获取所需的安全数据，这里就先对`Realm`进行配置。

`Realm`可以向Shiro提供用户的认证信息和授权信息，但是Shiro并不知道从哪获取用户认证信息及授权信息，这就需要我们实现这部分逻辑。它是Shiro中完全需要我们自己实现的模块。

在这里就定义了`MyShiroRealm`类，其继承了`AuthorizingRealm`抽象类，通过实现`doGetAuthenticationInfo()`方法(从`AuthenticatingRealm`抽象类继承)和`doGetAuthorizationInfo()`方法，就可以为Shiro提供用户认证信息和授权信息。

```
/**
 * realm配置类
 * <p>
 * <br>shiro登录认证及授权由realm处理，这里继承AuthorizingRealm抽象类，
 * <br>并重写doGetAuthenticationInfo()和doGetAuthorizationInfo()方法，
 * <br>分别进行用户登录认证和用户授权操作
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    /**
     * 获取用户认证信息，验证用户账号和密码
     *
     * @param token 用户认证token
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("=================正在登录===================");
        // 通过token获取用户名
        String username = (String) token.getPrincipal();
        Msg msg = sysUserService.findByUsername(username);
        if (msg.getCode().equals(MsgEnum.USER_NOT_EXIST.getCode())) {
            // 抛出账号不存在异常
            throw new UnknownAccountException();
        }
        SysUser user = (SysUser) msg.getData();
        if (!user.getEnabled()) {
            // 抛出账号已禁用异常
            throw new DisabledAccountException();
        }
        // 封装用户认证信息，并自动使用设置的CredentialsMatcher进行密码匹配
        // 第一个参数user将作为委托对象，之后可通过SecurityUtils.getSubject().getPrincipal()、
        // PrincipalCollection.getPrimaryPrincipal()等方法获取该对象
        // 抛出UnknownAccountException（账号不存在）和IncorrectCredentialsException（密码错误）异常
        return new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialSalt()), getName());
    }

    /**
     * 获取用户授权信息
     * <p>
     * <br>以下情况将会调用该方法：
     * <br>1. 过滤链配置了"roles[]"、"perms[]"等需要进行授权判断时
     * <br>2. 控制器类使用了@RequiresRoles、@RequiresPermissions等注解时
     * <br>3. 调用checkPermission()、checkPermissions()、checkRole()、checkRoles()等方法时
     * <p>
     * <br>通常需要使用缓存保存用户授权信息，否则每次发生以上情况时都会执行该方法
     * <br>使用缓存之后该方法就只执行一次，直到缓存失效
     * <br>用户修改授权时可以清除该缓存，以使权限修改即时生效
     *
     * @param principals 与Subject相关的委托对象集合
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("=================正在检查权限===================");
        // 通过getPrimaryPrincipal()方法获取SysUser对象
        String username = ((SysUser) principals.getPrimaryPrincipal()).getUsername();
        Msg msg = sysUserService.getUserRolesPerms(username);
        if (!msg.getCode().equals(MsgEnum.MSG_SUCCESS.getCode())) {
            return null;
        }
        UserRolesPermsVo userRolesPermsVo = (UserRolesPermsVo) msg.getData();
        // 封装用户授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 添加用户角色
        simpleAuthorizationInfo.addRoles(userRolesPermsVo.getRoles());
        // 添加用户权限
        simpleAuthorizationInfo.addStringPermissions(userRolesPermsVo.getPermissions());
        return simpleAuthorizationInfo;
    }
}
```

到这里，已经可以为Shiro提供用户认证信息和授权信息了。但是，仅仅配置了`Realm`实际上还不够完善，下面将一一补充。

#### 4.1.1 认证匹配器
通常情况下，为了安全起见，用户的密码都会进行加密处理，在本项目中为了举例也进行了加密处理。但是，这里并没有告知`Realm`如何去处理用户密码，当用户登录认证时，将会使用默认的`SimpleCredentialsMatcher`认证匹配器进行密码匹配：

```
public class SimpleCredentialsMatcher extends CodecSupport implements CredentialsMatcher {

    /**
     * 密码匹配
     * 
     * @param token 登录操作时产生的token
     * @param info Realm配置的用户认证信息
     * @return 是否匹配
     */
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object tokenCredentials = getCredentials(token);
        Object accountCredentials = getCredentials(info);
        return equals(tokenCredentials, accountCredentials);
    }

    /**
     * 获取token中的密码
     * 
     * @param token 登录操作时产生的token
     * @return 密码
     */
    protected Object getCredentials(AuthenticationToken token) {
        return token.getCredentials();
    }

    // ...省略其它内容...
}
```

从上面的代码中可以看到，默认的认证匹配器获取登录token的密码时并没有进行任何处理(继续深入`token.getCredentials()`方法可以发现其返回登录时的密码字符数组)，而用户认证信息中的密码是加密的，也就是说，它会用明文的密码与加密的密码进行匹配，这就会产生密码错误异常。

在前面的用户密码加密中，使用了Shiro提供的`SimpleHash`类进行密码加密。同样的，Shiro也提供了对应的`HashedCredentialsMatcher`认证匹配器进行密码匹配，看下它的代码：

```
public class HashedCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 密码匹配
     *
     * @param token 登录操作时产生的token
     * @param info Realm配置的用户认证信息
     * @return 是否匹配
     */
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object tokenHashedCredentials = hashProvidedCredentials(token, info);
        Object accountCredentials = getCredentials(info);
        return equals(tokenHashedCredentials, accountCredentials);
    }

    /**
     * 对token中的密码进行哈希处理
     *
     * @param token 登录操作时产生的token
     * @param info Realm配置的用户认证信息
     * @return 已处理的密码（已加密）
     */
    protected Object hashProvidedCredentials(AuthenticationToken token, AuthenticationInfo info) {
        Object salt = null;
        if (info instanceof SaltedAuthenticationInfo) {
            salt = ((SaltedAuthenticationInfo) info).getCredentialsSalt();
        } else {
            //retain 1.0 backwards compatibility:
            if (isHashSalted()) {
                salt = getSalt(token);
            }
        }
        return hashProvidedCredentials(token.getCredentials(), salt, getHashIterations());
    }

    /**
     * 对token中的密码进行哈希处理
     *
     * @param credentials token中的密码
     * @param salt 盐值
     * @param hashIterations 加密次数
     * @return 已处理的密码（已加密）
     */
    protected Hash hashProvidedCredentials(Object credentials, Object salt, int hashIterations) {
        String hashAlgorithmName = assertHashAlgorithmName();
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
    }

    // ...省略其它内容...
}
```

可以看到`HashedCredentialsMatcher`认证匹配器也使用`SimpleHash`类对token中的密码进行加密，才进行密码匹配。那么，可以为`Realm`配置`HashedCredentialsMatcher`作为认证匹配器。

```
/**
 * shiro核心配置类
 */
@Configuration
public class MyShiroConfig {

    /**
     * 配置Realm
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        // 设置认证匹配器，用户登录时使用
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 配置认证匹配器
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME);
        // 设置加密次数
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }
    
    // ...省略其它内容...
}
```

配置之后，就可以正常地进行登录操作了。

#### 4.1.2 缓存管理器
用户登录之后，就会使用系统进行所需的操作。对于许多系统来说，对用户进行权限控制是非常必要的。在`Realm`中，通过重写`doGetAuthorizationInfo()`方法，告诉了Shiro如何去获取用户权限，之后的权限控制就交给Shiro进行控制。当发生以下情况时，就会调用该方法：

- 过滤链配置了`roles[]`、`perms[]`等需要进行授权判断时
- 控制器类使用了`@RequiresRoles`、`@RequiresPermissions`等注解时
- 调用`checkPermission()`、`checkPermissions()`、`checkRole()`、`checkRoles()`等方法时

但是，一般情况下，用户的权限并不会经常改变，如果每次调用需要权限的方法都需要到数据库中获取用户的权限，这将增加额外的消耗，影响性能。那么，这里可以为`Realm`配置缓存，将用户的权限保存在缓存中，当下次需要使用用户权限时，就能直接从缓存中获取了。

这里使用Ehcache缓存，先引入依赖：

```
<!-- 导入shiro ehcache缓存 -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-ehcache</artifactId>
    <version>1.6.0</version>
</dependency>
```

配置Ehcache配置文件，具体见`shiro-ehcache.xml`。

配置缓存管理器：

```
/**
 * shiro核心配置类
 */
@Configuration
public class MyShiroConfig {

    /**
     * 配置缓存管理器
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        // 设置ehcache配置文件
        ehCacheManager.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
        return ehCacheManager;
    }

    /**
     * 配置SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置缓存管理器，启用缓存
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    // ...省略其它内容...
}
```

这里直接将缓存管理器设置到`SecurityManager`中，这样`SecurityManager`和它的子组件就可以使用该缓存管理器。`Realm`将用户权限保存到缓存中，不需要每次都从数据库获取，以此提高系统性能。

#### 4.1.3 配置Realm
`Realm`配置的最后一步就是将它设置到`SecurityManager`中，以便`SecurityManager`对它进行管理并让它发挥作用。

```
/**
 * shiro核心配置类
 */
@Configuration
public class MyShiroConfig {

    /**
     * 配置Realm
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        // 设置认证匹配器，用户登录时使用
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 配置SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置Realm
        securityManager.setRealm(myShiroRealm());
        // 设置缓存管理器，启用缓存
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    // ...省略其它内容...
}
```

#### 4.1.4 权限注解支持
Shiro提供了一些权限注解，只需要在控制器类中的方法上标注，那么用户需要具有一定的权限才能访问被标注的方法。权限注解主要有以下几个：

- `@RequiresRoles`：需要指定角色
- `@RequiresPermissions`：需要指定权限
- `@RequiresAuthentication`：需要用户已登录
- `@RequiresUser`：需要用户已登录或通过记住我登录的
- `@RequiresGuest`：通过游客身份访问

为了使用这些注解，可以在Shiro核心配置类中开启权限注解支持：

```
/**
 * shiro核心配置类
 */
@Configuration
public class MyShiroConfig {

    /**
     * 开启自动代理
     * <p>
     * <br>当控制类中使用@RequiresRoles、@RequiresPermissions等注解时需要设置，
     * <br>否则将导致控制器请求url出现404 Not Found异常
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启@RequiresRoles、@RequiresPermissions等权限注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }

    // ...省略其它内容...
}
 ```

当访问标注了权限注解的方法时，对于未登录的用户，将抛出`UnauthenticatedException`异常，对于已登录但不具有权限的用户，将抛出`UnauthorizedException`异常。

### 4.2 SecurityManager配置
前面已经配置了`Realm`，这里将会配置`SecurityManager`，并将`Realm`配置到`SecurityManager`中。

```
/**
 * shiro核心配置类
 */
@Configuration
public class MyShiroConfig {

    /**
     * 配置SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置Realm
        securityManager.setRealm(myShiroRealm());
        // 设置缓存管理器，启用缓存
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }
    
    // ...省略其它内容...
}
```

`SecurityManager`中除了必须至少配置一个`Realm`之外，还可以按需配置`CacheManager`(缓存管理器)、`SessionManager`(会话管理器)、`SubjectFactory`(Subject工厂)、`Authenticator`(认证器)、`Authorizer`(授权器)等。

### 4.3 Shiro过滤器
为项目引入Shiro之后，Shiro就会使用默认的过滤器和过滤链对请求url进行过滤，但是默认的过滤器和过滤链并不能满足项目的需求，所以需要我们自定义配置过滤器和过滤链。这里先简单介绍以下过滤器和过滤链，再看一下默认的配置，之后我们自定义配置过滤器和过滤链。

#### 4.3.1 过滤器和过滤链
过滤器，简单来说就是将不符合条件的过滤掉，只留下符合条件的。Shiro提供了以下过滤器：

```
public enum DefaultFilter {

    anon(AnonymousFilter.class),
    authc(FormAuthenticationFilter.class),
    authcBasic(BasicHttpAuthenticationFilter.class),
    authcBearer(BearerHttpAuthenticationFilter.class),
    logout(LogoutFilter.class),
    noSessionCreation(NoSessionCreationFilter.class),
    perms(PermissionsAuthorizationFilter.class),
    port(PortFilter.class),
    rest(HttpMethodPermissionFilter.class),
    roles(RolesAuthorizationFilter.class),
    ssl(SslFilter.class),
    user(UserFilter.class),
    invalidRequest(InvalidRequestFilter.class);

    // 省略其它部分。。。
}
```

这里简单介绍一下常用的过滤器：

- `anon`：允许匿名访问
- `authc`：需要用户已通过认证，否则跳转到`loginUrl`页面
- `logout`：进行用户退出操作
- `perms`：需要用户已认证并具有某种权限，否则跳转到`unauthorizedUrl`页面
  - 语法：`perms[perm1, perm2, ...]`，"[]"内填写所需权限，多个用","隔开
- `roles`：需要用户已认证并具有某种角色，否则跳转到`unauthorizedUrl`页面
  - 语法：`roles[role1, role2, ...]`，"[]"内填写所需角色，多个用","隔开
- `user`：需要用户已认证或通过记住我登录，否则跳转到`loginUrl`页面
- `invalidRequest`：阻止恶意的请求，如果请求URI中包含分号、反斜杠和非ASCII字符将视为无效的请求，返回400响应码(Shiro通常将它作为全局过滤器)

过滤链就是为一个或多个资源(请求URI)绑定一个或多个过滤器，从上到下进行资源匹配，满足匹配时使用所绑定的过滤器，通过过滤器的就可以访问资源。一般表现形式如下：

```
/uri1 -> filter1[optional_config1]
/uri2 -> filter2[optional_config2], filter3[optional_config3]
/uri3 -> filter3[optional_config3]
/uri4 -> filter4[optional_config4]
/** -> filter5[optional_config5]
```

可以使用`**`作为通配符匹配一批资源

#### 4.3.2 默认配置
Shiro中使用Map保存过滤链，之后调用`ShiroFilterFactoryBean.setFilterChainDefinitionMap(Map<String, String>)`方法将Map设置到Shiro过滤器中，Shiro就可以使用该过滤链进行资源访问控制了。这里看一下Shiro中默认的配置：

```
@Configuration
@ConditionalOnProperty(name = "shiro.web.enabled", matchIfMissing = true)
public class ShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Override
    protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
        return super.shiroFilterFactoryBean();
    }

    // ...省略其它内容...
}
```

这里调用父类的`shiroFilterFactoryBean()`方法创建`ShiroFilterFactoryBean`对象，继续看父类的方法：

```
public class AbstractShiroWebFilterConfiguration {

    @Autowired
    protected SecurityManager securityManager;

    @Autowired
    protected ShiroFilterChainDefinition shiroFilterChainDefinition;

    @Autowired
    protected Map<String, Filter> filterMap;

    @Value("#{ @environment['shiro.loginUrl'] ?: '/login.jsp' }")
    protected String loginUrl;

    @Value("#{ @environment['shiro.successUrl'] ?: '/' }")
    protected String successUrl;

    @Value("#{ @environment['shiro.unauthorizedUrl'] ?: null }")
    protected String unauthorizedUrl;

    protected List<String> globalFilters() {
        return Collections.singletonList(DefaultFilter.invalidRequest.name());
    }

    protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        filterFactoryBean.setLoginUrl(loginUrl);
        filterFactoryBean.setSuccessUrl(successUrl);
        filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setGlobalFilters(globalFilters());
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
        filterFactoryBean.setFilters(filterMap);

        return filterFactoryBean;
    }
}
```

这里创建了`ShiroFilterFactoryBean`对象，主要关注`loginUrl`和过滤链。`loginUrl`设置了`/login.jsp`，而过滤链调用了自动注入对象的方法，继续查看：

```
@Configuration
@AutoConfigureBefore(ShiroAutoConfiguration.class)
@ConditionalOnProperty(name = "shiro.web.enabled", matchIfMissing = true)
public class ShiroWebAutoConfiguration extends AbstractShiroWebConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Override
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        return super.shiroFilterChainDefinition();
    }

    // ...省略其它内容...
}
```

这里又调用了父类的方法创建`ShiroFilterChainDefinition`对象，继续看父类的方法：

```
public class AbstractShiroWebConfiguration extends AbstractShiroConfiguration {

    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    // ...省略其它内容...
}
```

到这里可以看到Shiro默认的过滤链只有一个，但是它会把所有的请求URI都拦截下来，并交给`authc`过滤器，而`authc`过滤器需要用户通过认证才能通过该过滤器，否则跳转到`loginUrl`页面，也就是`/login.jsp`页面。

项目启动之后访问任何请求，肯定都没有通过认证，就都会跳转到`/login.jsp`页面。这里可以编写`/login.jsp`页面进行登录认证，也可以通过配置文件修改跳转页面。但是，如果需要使用自定义的过滤链，还是需要自定义配置`ShiroFilterFactoryBean`对象。

#### 4.3.3 自定义配置
这里自定义配置`ShiroFilterFactoryBean`对象，具体配置如下：

```
/**
 * shiro核心配置类
 */
@Configuration
public class MyShiroConfig {

    /**
     * 配置shiro过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());

        // 设置用户登录后，未授权时跳转界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/page/unauthorized.html");
        // 设置登录界面，当用户未登录且访问资源不符合过滤链定义时跳转到该界面
        shiroFilterFactoryBean.setLoginUrl("/page/login.html");

        // 添加过滤链定义，从上到下匹配
        // key值表示访问的资源，value表示过滤器（多个过滤器用','隔开）
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 需要认证
        filterChainDefinitionMap.put("/page/authc.html", "authc");
        // 需要admin角色
        filterChainDefinitionMap.put("/page/admin1.html", "roles[admin]");
        // 需要look权限
        filterChainDefinitionMap.put("/page/look1.html", "perms[look]");
        // 可匿名访问
        filterChainDefinitionMap.put("/page/**", "anon");

        // 配置静态资源
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        // 其它资源
        filterChainDefinitionMap.put("/**", "anon");

        // 设置过滤链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    // ...省略其它内容...
}
```

## 5. Shiro使用
配置了Shiro之后，下面开始使用Shiro提供的一些功能。

### 5.1 用户登录/退出
用户登录一般按照以下方式进行：

```
// 封装用户数据
UsernamePasswordToken token = new UsernamePasswordToken(username, password);
// 获取Subject
Subject subject = SecurityUtils.getSubject();
// 通过Subject执行登录，此处将会调用MyShiroRealm的doGetAuthenticationInfo()方法进行登录认证
subject.login(token);
```

最后一步执行登录时，会调用`MyShiroRealm`的`doGetAuthenticationInfo()`方法，可能会抛出异常，可以根据异常进行相应的处理。

登录成功后，该方法会将用户信息与`Subject`实例关联起来，可以通过`SecurityUtils.getSubject().getPrincipal()`、`PrincipalCollection.getPrimaryPrincipal()`等方法获取关联的用户信息，例如：

```
@RequestMapping("/getRolesPerms")
public Msg getUserRolesPerms() {
    SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
    if (user == null) {
        return Msg.error(MsgEnum.USER_NOT_EXIST);
    }
    String username = user.getUsername();
    return sysUserService.getUserRolesPerms(username);
}
```

用户退出登录只需要执行以下一句代码：

```
// 执行退出
SecurityUtils.getSubject().logout();
```

### 5.2 密码重试限制
很多时候，为了安全考虑，会对密码输入错误次数进行限制，当达到一定次数之后账号锁定一段时间不能登录。这里可以使用Shiro登录时抛出的`IncorrectCredentialsException`（密码错误）异常来统计密码错误次数，以及缓存的失效时间作为账号锁定时间，就可以实现密码重试限制。

首先在EhCache配置文件中配置一个缓存，用于保存用户密码重试次数：

```
<!-- 登录记录缓存锁定5分钟 -->
<cache name="passwordRetryCache"
       eternal="false"
       overflowToDisk="false"
       timeToIdleSeconds="300"
       timeToLiveSeconds="0"
       maxEntriesLocalHeap="2000"
/>
```

缓存的允许闲置时间设置为5分钟，也就是账号锁定5分钟之后才能重新登录。

执行登录之前先检查是否超过限制次数，不超过才进行登录，如果密码错误就增加一次密码错误次数，登录成功就清除记录缓存，具体代码如下：

```
/**
 * 用户登录
 *
 * @param username 用户名
 * @param password 密码
 * @return 登录结果
 */
@RequestMapping("/login")
public Msg login(String username, String password) {
    // 检查账号锁定
    if (PasswordRetryUtil.checkPasswordRetry(username)) {
        return Msg.error(MsgEnum.USER_LOCK);
    }
    // 封装用户数据
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    // 获取Subject
    Subject subject = SecurityUtils.getSubject();
    try {
        // 通过Subject执行登录，此处将会调用MyShiroRealm的doGetAuthenticationInfo()方法进行登录认证
        subject.login(token);
        // 登录成功后清除锁定记录
        PasswordRetryUtil.clearPasswordRetry(username);
    } catch (UnknownAccountException e) {
        return Msg.error(MsgEnum.USER_NOT_EXIST);
    } catch (IncorrectCredentialsException e) {
        // 密码错误增加错误次数
        if (PasswordRetryUtil.addPasswordRetry(username)) {
            return Msg.error(MsgEnum.USER_LOCK);
        }
        return Msg.error(MsgEnum.PASSWORD_ERROR);
    } catch (DisabledAccountException e) {
        return Msg.error(MsgEnum.USER_DISABLED);
    } catch (Exception e) {
        e.printStackTrace();
        return Msg.error();
    }
    return Msg.success();
}
```

增加密码错误次数实现如下：

```
/**
 * 增加密码错误次数，当次数达到5次时返回true
 *
 * @param username 用户名
 * @return 是否出错达到5次
 */
public static boolean addPasswordRetry(String username) {
    DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
    Cache<Object, AtomicInteger> cache = securityManager.getCacheManager().getCache(PASSWORD_RETRY_CACHE);
    AtomicInteger atomicInteger = cache.get(username);
    if (atomicInteger == null) {
        atomicInteger = new AtomicInteger(1);
        cache.put(username, atomicInteger);
    } else {
        atomicInteger.incrementAndGet();
    }
    return atomicInteger.get() >= 5;
}
```

这里使用原子操作，对并发提供更好的支持。

### 5.3 权限控制
前面在配置过滤链的时候，就使用到了权限控制，代码如下：

```
// 需要admin角色
filterChainDefinitionMap.put("/page/admin1.html", "roles[admin]");
// 需要look权限
filterChainDefinitionMap.put("/page/look1.html", "perms[look]");
```

以上使用了`roles`和`perms`过滤器，那么访问以上两个链接就需要用户具有admin角色或look权限，否则将跳转到`unauthorizedUrl`页面。除了通过过滤链进行权限控制之外，还可以使用注解，只需要在控制器类方法上标注，就可以进行权限控制，例如：

```
@RequiresRoles("admin")
@RequestMapping("/admin2.html")
public String admin2() {
    return "/role/admin2";
}

@RequiresPermissions("look")
@RequestMapping("/look2.html")
public String look2() {
    return "/permission/look2";
}
```

通过`@RequiresRoles`和`@RequiresPermissions`注解，也指定了用户需要具有admin角色或look权限。当用户未登录或无权限时，将抛出异常，这里使用全局异常进行处理：

```
/**
 * 未认证异常，用户未登录但访问@RequiresRoles、@RequiresPermissions等注解标记的页面时抛出
 *
 * @param e 异常
 * @return 页面
 */
@ExceptionHandler(UnauthenticatedException.class)
public String handleUnauthenticatedException(UnauthenticatedException e) {
    logger.error("捕获未认证异常：", e);
    return "/authc/unauthc";
}
/**
 * 未授权异常，用户无授权但访问@RequiresRoles、@RequiresPermissions等注解标记的页面时抛出
 *
 * @param e 异常
 * @return 页面
 */
@ExceptionHandler(UnauthorizedException.class)
public String handleUnauthorizedException(UnauthorizedException e) {
    logger.error("捕获未授权异常：", e);
    return "/authc/unauthorized";
}
```

当修改用户角色和权限时，为了让授权即时生效，可以将缓存清空，这样在下一次需要授权时就可以重新获取最新的权限信息了。

```
/**
 * 修改用户角色或权限时清空缓存，否则授权无法即时生效
 *
 * @return Msg
 */
@RequestMapping("/clearCache")
public Msg clearCache() {
    DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
    // Shiro自动为Realm创建权限缓存，缓存名为“Realm的全限定类名.authorizationCache”
    String cacheName = "zam.hzh.shiro.common.shiro.config.MyShiroRealm.authorizationCache";
    // 获取权限缓存
    Cache<Object, Object> cache = securityManager.getCacheManager().getCache(cacheName);
    // 清空缓存
    cache.clear();
    return Msg.success();
}
```

## 6. 项目操作
运行项目之前先创建`demo_shiro`数据库

运行之后，浏览器访问`http://localhost:9001/page/index.html`
