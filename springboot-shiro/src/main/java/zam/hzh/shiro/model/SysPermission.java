package zam.hzh.shiro.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 权限表
 */
@Entity
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 3964681694964126354L;

    @Id
    @GenericGenerator(name = "uid", strategy = "uuid2")
    @GeneratedValue(generator = "uid")
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SysPermission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
