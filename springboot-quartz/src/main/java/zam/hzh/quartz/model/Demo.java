package zam.hzh.quartz.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Demo implements Serializable {
    private static final long serialVersionUID = -4460967107378933141L;

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
}
