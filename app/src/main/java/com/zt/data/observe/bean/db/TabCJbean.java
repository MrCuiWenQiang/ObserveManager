package com.zt.data.observe.bean.db;

import com.zt.data.observe.util.out.ExcelCount;
import com.zt.data.observe.util.out.ExcelName;

import org.litepal.crud.LitePalSupport;
@ExcelName(TabName = "深层沉降原始记录表")
public class TabCJbean extends LitePalSupport {
    private long id;
    @ExcelCount(order = 0, name = "区号")
    private String quhao;//区号
    @ExcelCount(order = 1, name = "点号")
    private String dianhao;//点号
    @ExcelCount(order = 2, name = "孔口标高")
    private String kongkou;//孔口标高
    @ExcelCount(order = 3, name = "深度")
    private String shendu;//深度
    @ExcelCount(order = 4, name = "至孔口深度")
    private String kkshendu;//至孔口深度
    @ExcelCount(order = 5, name = "备注")
    private String beizhu;//备注
    private String createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuhao() {
        return quhao;
    }

    public void setQuhao(String quhao) {
        this.quhao = quhao;
    }

    public String getDianhao() {
        return dianhao;
    }

    public void setDianhao(String dianhao) {
        this.dianhao = dianhao;
    }

    public String getKongkou() {
        return kongkou;
    }

    public void setKongkou(String kongkou) {
        this.kongkou = kongkou;
    }

    public String getShendu() {
        return shendu;
    }

    public void setShendu(String shendu) {
        this.shendu = shendu;
    }

    public String getKkshendu() {
        return kkshendu;
    }

    public void setKkshendu(String kkshendu) {
        this.kkshendu = kkshendu;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
