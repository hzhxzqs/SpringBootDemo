package zam.hzh.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.common.result.MsgEnum;
import zam.hzh.shiro.common.shiro.util.PasswordHelper;
import zam.hzh.shiro.dao.*;
import zam.hzh.shiro.model.*;
import zam.hzh.shiro.service.SysUserService;
import zam.hzh.shiro.vo.UserRolesPermsVo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRolePermissionDao sysRolePermissionDao;


    @Override
    public Msg addUser(SysUser addUser) {
        SysUser existUser = sysUserDao.findByUsername(addUser.getUsername());
        if (null != existUser) {
            return Msg.error(MsgEnum.USERNAME_EXIST);
        }
        PasswordHelper.setPassword(addUser);
        sysUserDao.save(addUser);
        return Msg.success();
    }

    @Override
    public Msg findByUsername(String username) {
        SysUser user = sysUserDao.findByUsername(username);
        if (user == null) {
            return Msg.error(MsgEnum.USER_NOT_EXIST);
        }
        return Msg.success(user);
    }

    /**
     * 获取用户角色和权限
     *
     * @param username 用户名
     * @return 用户角色和权限
     */
    @Override
    public Msg getUserRolesPerms(String username) {
        SysUser user = sysUserDao.findByUsername(username);
        if (user == null) {
            return Msg.error(MsgEnum.USER_NOT_EXIST);
        }
        Set<String> userRoleIds = sysUserRoleDao.findByUserId(user.getId()).stream()
                .map(SysUserRole::getRoleId).collect(Collectors.toSet());
        List<SysRole> roles = sysRoleDao.findByIdIn(userRoleIds);
        List<String> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
        Set<String> userPermissionIds = sysRolePermissionDao.findByRoleIdIn(roleIds).stream()
                .map(SysRolePermission::getPermissionId).collect(Collectors.toSet());
        List<SysPermission> permissions = sysPermissionDao.findByIdIn(userPermissionIds);

        List<String> roleNames = roles.stream().map(SysRole::getName).collect(Collectors.toList());
        List<String> permissionNames = permissions.stream().map(SysPermission::getName).collect(Collectors.toList());
        UserRolesPermsVo userRolesPermsVo = new UserRolesPermsVo(username, roleNames, permissionNames);
        return Msg.success(userRolesPermsVo);
    }
}
