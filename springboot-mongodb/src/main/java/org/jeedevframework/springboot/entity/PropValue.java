package org.jeedevframework.springboot.entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.jeedevframework.springboot.utils.StringUtil;

import java.io.Serializable;

/**
 * 属性数据
 */
public class PropValue implements Serializable {
    private Object value;
    private String label;
    private String prefix;
    private String suffix;
    private String sourceModelId;

    public PropValue() {
    }

    public PropValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSourceModelId() {
        return sourceModelId;
    }

    public void setSourceModelId(String sourceModelId) {
        this.sourceModelId = sourceModelId;
    }

    public String getStringValue() {
        if (value == null) {
            return StringUtils.EMPTY;
        }

        try {
            return StringUtil.format(value);
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
        }

        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
