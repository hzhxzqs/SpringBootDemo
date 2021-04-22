package zam.hzh.shiro.vo;

import java.io.Serializable;
import java.util.List;

public class UserRolesPermsVo implements Serializable {

    private static final long serialVersionUID = -2056327512812535578L;

    private String user;

    private List<String> roles;

    private List<String> permissions;

    public UserRolesPermsVo() {
    }

    public UserRolesPermsVo(String user, List<String> roles, List<String> permissions) {
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UserRolesPermsVo{" +
                "user='" + user + '\'' +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
