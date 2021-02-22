package zam.hzh.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class Demo implements Serializable {

    private static final long serialVersionUID = 5833447033895943293L;

    private Long id;

    private Date createDate;

    private String demoDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDemoDesc() {
        return demoDesc;
    }

    public void setDemoDesc(String demoDesc) {
        this.demoDesc = demoDesc;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", demoDesc='" + demoDesc + '\'' +
                '}';
    }
}
