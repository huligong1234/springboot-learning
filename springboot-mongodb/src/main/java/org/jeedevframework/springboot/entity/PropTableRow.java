package org.jeedevframework.springboot.entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 多值列表行数据
 */
public class PropTableRow implements Serializable {
    public static final String ROW_STATUS_NORMAL = "0";
    public static final String ROW_STATUS_NEW = "1";
    public static final String ROW_STATUS_DELETE = "2";
    private String id;
    private String status;
    private Map<String, PropValue> props = new HashMap<>();

    public PropTableRow() {
        this(null);
    }

    public PropTableRow(String id) {
        this(id, ROW_STATUS_NORMAL);
    }

    public PropTableRow(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, PropValue> getProps() {
        return props;
    }

    public void setProps(Map<String, PropValue> props) {
        this.props = props;
    }

    public PropValue getPropValue(String propCode) {
        return props.get(propCode);
    }

    public PropTableRow setPropValue(String propCode, String value) {
        String propKey = propCode;
        String valueKey = "value";
        if (propCode.contains(".")) {
            propKey = propCode.split("\\.")[0];
            valueKey = propCode.split("\\.")[1];
        }

        PropValue propValue = props.get(propKey);
        if (propValue == null) {
            propValue = new PropValue();
            switch (valueKey) {
                case "label":
                    propValue.setLabel(value);
                    break;
                default:
                    propValue.setValue(value);
            }
            props.put(propKey, propValue);
        } else {
            switch (valueKey) {
                case "label":
                    propValue.setLabel(value);
                    break;
                default:
                    propValue.setValue(value);
            }
        }
        return this;
    }

    public PropTableRow setPropValue(String propCode, PropValue value) {
        props.put(propCode, value);
        return this;
    }

    public String getValue(String propCode) {
        PropValue propValue = getPropValue(propCode);
        if (propValue == null) {
            return StringUtils.EMPTY;
        }

        return propValue.getStringValue();
    }

    public String getPrefix(String propCode) {
        PropValue propValue = getPropValue(propCode);
        if (propValue == null) {
            return StringUtils.EMPTY;
        }
        return propValue.getPrefix();
    }

    public String getSuffix(String propCode) {
        PropValue propValue = getPropValue(propCode);
        if (propValue == null) {
            return StringUtils.EMPTY;
        }
        return propValue.getSuffix();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
