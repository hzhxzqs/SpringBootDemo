package zam.hzh.jpa.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * <p>
 * 使用@Entity注解，在程序启动时将自动创建数据库表结构
 */
@Entity
public class Demo implements Serializable {

    private static final long serialVersionUID = 7228138604175635058L;

    @Id
    @GenericGenerator(name = "uid", strategy = "uuid2")    // strategy指定id产生策略
    @GeneratedValue(generator = "uid")
    private String id;

    private String demoDesc;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate = new Date();

    @Transient
    private String transientText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemoDesc() {
        return demoDesc;
    }

    public void setDemoDesc(String demoDesc) {
        this.demoDesc = demoDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTransientText() {
        return transientText;
    }

    public void setTransientText(String transientText) {
        this.transientText = transientText;
    }
}
