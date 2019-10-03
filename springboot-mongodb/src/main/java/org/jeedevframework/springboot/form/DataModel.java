package org.jeedevframework.springboot.form;

import javax.persistence.Transient;
import org.jeedevframework.springboot.entity.DataEntity;

import java.util.List;

public class DataModel extends DataEntity {
    @Transient
    private List<DataEntity> listData;

    public List<DataEntity> getListData() {
        return listData;
    }

    public void setListData(List<DataEntity> listData) {
        this.listData = listData;
    }
}
