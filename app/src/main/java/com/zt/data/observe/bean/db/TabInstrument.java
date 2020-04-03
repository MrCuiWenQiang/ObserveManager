package com.zt.data.observe.bean.db;

import android.text.TextUtils;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
import com.orient.me.data.table.ICellItem;
import com.zt.data.observe.util.out.ExcelCount;
import com.zt.data.observe.util.out.ExcelName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

import cn.faker.repaymodel.util.DateUtils;

/**
 * 观测点
 */
@ExcelName(TabName = "管点表")
public class TabInstrument extends LitePalSupport implements Serializable {
    private long id=-1;
    private long projectId;//所属项目
    @ExcelCount(order = 0, name = "基准点名")
    private String name;//基准点名

    @ExcelCount(order = 1, name = "基准高程")
    private float jzgc;//基准高程

    @ExcelCount(order = 2, name = "仪器高")
    private float yqg;//仪器高



    @ExcelCount(order = 3, name = "观测点点号")
    private String gcdno="";//观测点点号

    @ExcelCount(order = 4, name = "垂直角 盘左")
    private float czjLeft;//垂直角 盘左


    @ExcelCount(order = 5, name = "垂直角 盘右")
    private float czjRight;//垂直角 盘右


    @ExcelCount(order = 6, name = "棱镜高")
    private float ljg;//棱镜高

    @ExcelCount(order = 7, name = "平距")
    private float pj;//平距

    private String createUser;//创建人用户名

    @ExcelCount(order = 8, name = "创建时间")
    private String createDate;

    @ExcelCount(order = 9, name = "修改时间")
    private String updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getJzgc() {
        return jzgc;
    }

    public void setJzgc(float jzgc) {
        this.jzgc = jzgc;
    }

    public float getYqg() {
        return yqg;
    }

    public void setYqg(float yqg) {
        this.yqg = yqg;
    }

    public float getCzjLeft() {
        return czjLeft;
    }

    public void setCzjLeft(float czjLeft) {
        this.czjLeft = czjLeft;
    }

    public float getCzjRight() {
        return czjRight;
    }

    public void setCzjRight(float czjRight) {
        this.czjRight = czjRight;
    }

    public float getLjg() {
        return ljg;
    }

    public String getGcdno() {
        return gcdno;
    }

    public void setGcdno(String gcdno) {
        this.gcdno = gcdno;
    }

    public void setLjg(float ljg) {
        this.ljg = ljg;
    }

    public float getPj() {
        return pj;
    }

    public void setPj(float pj) {
        this.pj = pj;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        if (TextUtils.isEmpty(createDate)){
            return createDate;
        }
        return DateUtils.stringToDateString(createDate);
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        if (TextUtils.isEmpty(updateDate)){
            return updateDate;
        }
        return DateUtils.stringToDateString(updateDate);
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

}
