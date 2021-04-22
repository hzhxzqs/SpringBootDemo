package zam.hzh.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zam.hzh.shiro.model.SysUserRole;

import java.util.List;

public interface SysUserRoleDao extends JpaRepository<SysUserRole, String> {

    List<SysUserRole> findByUserId(String userId);
}
