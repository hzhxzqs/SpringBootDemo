package zam.hzh.shiro.common.shiro.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.common.result.MsgEnum;
import zam.hzh.shiro.model.SysUser;
import zam.hzh.shiro.service.SysUserService;
import zam.hzh.shiro.vo.UserRolesPermsVo;

import javax.annotation.Resource;

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
