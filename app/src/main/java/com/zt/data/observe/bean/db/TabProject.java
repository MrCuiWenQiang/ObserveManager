package com.zt.data.observe.bean.db;

import android.text.TextUtils;

import org.litepal.crud.LitePalSupport;

import cn.faker.repaymodel.util.DateUtils;

public class TabProject extends LitePalSupport {
    private long id;
    public long count;//数据数量
    private String name;//项目名
    private String createUser;//创建人
    private String createAddress;//创建地点
    private String remarks;//备注
    private String createDate;
    private String updateDate;
    public TabProject() {
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        if (TextUtils.isEmpty(createDate)) {
            return createDate;
        }
        return DateUtils.stringToDateString(createDate);
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateAddress() {
        return createAddress;
    }

    public void setCreateAddress(String createAddress) {
        this.createAddress = createAddress;
    }
}
