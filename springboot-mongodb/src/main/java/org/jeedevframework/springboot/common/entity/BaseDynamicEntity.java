package org.jeedevframework.springboot.common.entity;

import javax.persistence.Transient;

/**
 * 动态表对应的实体基类
 */
public abstract class BaseDynamicEntity extends BaseEntity {
    @Transient
    private String modelCode;

    public BaseDynamicEntity() {
        super();
    }

    public BaseDynamicEntity(String modelCode) {
        super();
        this.modelCode = modelCode;
    }

    public String getModelCode() {
        if (modelCode != null) {
            return modelCode.toLowerCase();
        }
        return null;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }
}
