package zam.hzh.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.common.result.MsgEnum;
import zam.hzh.shiro.dao.*;
import zam.hzh.shiro.model.*;
import zam.hzh.shiro.service.SysPermissionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRolePermissionDao sysRolePermissionDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public Msg addPermission(SysPermission addPermission) {
        SysPermission sysPermission = sysPermissionDao.findByName(addPermission.getName());
        if (sysPermission != null) {
            return Msg.error(MsgEnum.PERMISSION_EXIST);
        }
        sysPermissionDao.save(addPermission);
        return Msg.success();
    }

    @Override
    public Msg findAllPermissions() {
        return Msg.success(sysPermissionDao.findAll());
    }

    @Override
    public Msg giveRolePermission(String userId, List<String> permissionIds) {
        Optional<SysUser> optionalSysUser = sysUserDao.findById(userId);
        if (!optionalSysUser.isPresent()) {
            return Msg.error(MsgEnum.USER_NOT_EXIST);
        }
        List<SysUserRole> userRoles = sysUserRoleDao.findByUserId(userId);
        if (userRoles.size() == 0) {
            return Msg.error();
        }
        // 为了方便使用用户的第一个角色进行权限操作
        String roleId = userRoles.get(0).getRoleId();
        Optional<SysRole> optional = sysRoleDao.findById(roleId);
        if (!optional.isPresent()) {
            return Msg.error(MsgEnum.ROLE_NOT_EXIST);
        }
        List<SysRolePermission> sysRolePermissions = sysRolePermissionDao.findByRoleId(roleId);
        sysRolePermissionDao.deleteAll(sysRolePermissions);
        List<SysPermission> sysPermissions = sysPermissionDao.findByIdIn(permissionIds);
        List<SysRolePermission> sysRolePermissionList = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissions) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(sysPermission.getId());
            sysRolePermissionList.add(sysRolePermission);
        }
        sysRolePermissionDao.saveAll(sysRolePermissionList);
        return Msg.success();
    }
}
