package org.jeedevframework.springboot.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.jeedevframework.springboot.common.RequestContextProxy;
import org.jeedevframework.springboot.utils.IdGenUtils;
import org.springframework.data.annotation.Version;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 实体基类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String DATA_VERSION = "dataVersion";
    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATED_DATE = "updatedDate";

    /**
     * 主键ID
     */
    @Id
    protected String id;

    /**
     * 数据版本
     */
    @Version
    @tk.mybatis.mapper.annotation.Version
    protected Long version;
    /**
     * 创建日期
     */
    protected Date createdDate;
    /**
     * 创建者名字
     */
    protected String createdBy;
    /**
     * 更新日期
     */
    protected Date updatedDate;
    /**
     * 更新者名字
     */
    protected String updatedBy;

    /**
     * 插入之前执行方法，需要手动调用
     */
    public void preInsert() {
        // 允许自定义ID
        if (StringUtils.isEmpty(this.getId())) {
            this.setId(IdGenUtils.uuid());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        this.updatedDate = calendar.getTime();
        this.createdDate = this.updatedDate;
        this.updatedBy = RequestContextProxy.getRequestUsername();
        this.createdBy = this.updatedBy;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    public void preUpdate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        this.updatedDate = calendar.getTime();
        this.updatedBy = RequestContextProxy.getRequestUsername();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
