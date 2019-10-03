package org.jeedevframework.springboot.entity;

import org.jeedevframework.springboot.common.Constants;
import org.jeedevframework.springboot.common.entity.BaseDynamicEntity;
import org.jeedevframework.springboot.utils.StringUtil;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.annotation.ColumnType;
import org.jeedevframework.springboot.common.mybatis.handler.JsonTypeHandler;

import javax.persistence.Transient;
import java.util.*;

/**
 * 数据实体
 *
 */
@Document(collection = "jeedev_${modelCode}_data")
public class DataEntity extends BaseDynamicEntity {
    public static final String SERIAL_NUM = "sn";
    public static final String REVIEW_STATUS = "reviewStatus";
    public static final String CHANGE_STATUS = "changeStatus";
    public static final String DATA_STATUS = "dataStatus";
    public static final String PARENT_SN = "parentSn";
    public static final String CATEGORY_SN = "categorySn";
    public static final String MODEL_CODE = "modelCode";
    public static final String MODEL_VERSION = "modelVersion";

    private String sn;
    private Integer modelVersion;
    private Integer dataVersion;
    private Integer dataLevel;
    /**
     * 父级Sn
     */
    private String parentSn;
    /**
     * 父级label
     */
    private String parentLabel;
    /**
     * 父级数据版本
     */
    private Integer parentDataVersion;
    /**
     * 父级sn集合，用于无限层级，几代同堂，将所有的祖辈的sn都放入到一个字段中
     */
    private String parentSnPath;
    /**
     * 引用层级被引用的层级的sn
     */
    private String categorySn;
    /**
     * 引用层级被引用的层级的label
     */
    private String categoryLabel;
    /**
     * 引用层级被引用层级的数据版本
     */
    private Integer categoryDataVersion;
    /**
     * 引用层级被引用的层级数据Sn的集合
     */
    private String categorySnPath;
    /**
     * 临时变量不入数据库
     */
    @Transient
    private String dataLabel;
    private String errorMsg;
    private String dataStatus;
    private String reviewStatus;
    private String changeStatus;

    /**
     * 归属组织
     */
    private String ownerOrg;
    /**
     * 归属人
     */
    @ColumnType(typeHandler = JsonTypeHandler.class)
    private List<String> ownerUser;
    private Map<String, PropValue> props = new HashMap<>();
    private Map<String, List<PropTableRow>> propTables = new HashMap<>();

    public DataEntity() {
    }

    public DataEntity(String modelCode) {
        super(modelCode);
    }

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.getDataVersion() == null) {
            this.setDataVersion(1);
        }
        if (this.getDataStatus() == null) {
            this.setDataStatus(Constants.DATA_STATUS__NORMAL);
        }
        this.setChangeStatus(Constants.DATA_CHANGE__UNCHANGED);
        this.setReviewStatus(Constants.DATA_VALID__APPROVED);
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(Integer modelVersion) {
        this.modelVersion = modelVersion;
    }

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Integer getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(Integer dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getParentSn() {
        return parentSn;
    }

    public void setParentSn(String parentSn) {
        this.parentSn = parentSn;
    }

    public String getParentLabel() {
        return parentLabel;
    }

    public void setParentLabel(String parentLabel) {
        this.parentLabel = parentLabel;
    }

    public Integer getParentDataVersion() {
        return parentDataVersion;
    }

    public void setParentDataVersion(Integer parentDataVersion) {
        this.parentDataVersion = parentDataVersion;
    }

    public String getParentSnPath() {
        return parentSnPath;
    }

    public void setParentSnPath(String parentSnPath) {
        this.parentSnPath = parentSnPath;
    }

    public String getCategorySn() {
        return categorySn;
    }

    public void setCategorySn(String categorySn) {
        this.categorySn = categorySn;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public Integer getCategoryDataVersion() {
        return categoryDataVersion;
    }

    public void setCategoryDataVersion(Integer categoryDataVersion) {
        this.categoryDataVersion = categoryDataVersion;
    }

    public String getCategorySnPath() {
        return categorySnPath;
    }

    public void setCategorySnPath(String categorySnPath) {
        this.categorySnPath = categorySnPath;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
    }

    public String getOwnerOrg() {
        return ownerOrg;
    }

    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public List<String> getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(List<String> ownerUser) {
        this.ownerUser = ownerUser;
    }

    public Map<String, PropValue> getProps() {
        return props;
    }

    public void setProps(Map<String, PropValue> props) {
        this.props = props;
    }

    public Map<String, List<PropTableRow>> getPropTables() {
        return propTables;
    }

    public void setPropTables(Map<String, List<PropTableRow>> propTables) {
        this.propTables = propTables;
    }

    public PropValue getPropValue(String propCode) {
        return props.get(propCode);
    }

    public void setPropValue(String propCode, String value) {
        //TODO 代码优化
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
    }

    public void setPropValue(String propCode, PropValue value) {
        props.put(propCode, value);
    }

    public List<PropTableRow> getPropTable(String tableCode) {
        return propTables.get(tableCode);
    }

    public PropTableRow getPropTableRow(String tableCode, String rowId) {
        List<PropTableRow> list = this.getPropTable(tableCode);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Optional<PropTableRow> optional = list.stream().filter(row -> Objects.equals(row.getId(), rowId)).findFirst();

        return optional.orElse(null);
    }

    public void addPropTableRow(String tableCode, PropTableRow row) {
        List<PropTableRow> rows = propTables.computeIfAbsent(tableCode, k -> new ArrayList<>());
        rows.add(row);
    }

    public String getValue(String propCode) {
        PropValue propValue = getPropValue(propCode);
        if (propValue == null) {
            return StringUtil.EMPTY;
        }
        return propValue.getStringValue();
    }

    public String getPrefix(String propCode) {
        PropValue propValue = getPropValue(propCode);
        if (propValue == null) {
            return StringUtil.EMPTY;
        }
        return propValue.getPrefix();
    }

    public String getSuffix(String propCode) {
        PropValue propValue = getPropValue(propCode);
        if (propValue == null) {
            return StringUtil.EMPTY;
        }
        return propValue.getSuffix();
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }
}
