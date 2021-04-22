package zam.hzh.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zam.hzh.shiro.model.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, String> {

    SysUser findByUsername(String username);
}
