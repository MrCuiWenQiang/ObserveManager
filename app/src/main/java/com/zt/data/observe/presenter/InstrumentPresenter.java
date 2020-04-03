package com.zt.data.observe.presenter;

import android.text.TextUtils;

import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.bean.db.TabProject;
import com.zt.data.observe.contract.InstrumentContract;
import com.zt.data.observe.util.out.ExcelUtil;
import com.zt.data.observe.util.out.POIExcelUtil;

import java.io.File;
import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;
import cn.faker.repaymodel.util.DateUtils;
import cn.faker.repaymodel.util.db.DBThreadHelper;
import cn.faker.repaymodel.util.db.litpal.LitPalUtils;

public class InstrumentPresenter extends BaseMVPPresenter<InstrumentContract.View> implements InstrumentContract.Presenter {


    @Override
    public void query(final long projectId) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<List<TabInstrument>>() {

            @Override
            protected List<TabInstrument> jobContent() throws Exception {
                return LitPalUtils.selectWhere("createDate DESC ",TabInstrument.class, "projectId=?", String.valueOf(projectId));
            }

            @Override
            protected void jobEnd(List<TabInstrument> data) {
                if (data != null && data.size() > 0) {
                    getView().queryProject_success(data);
                } else {
                    getView().queryProject_fail("暂无工程");
                }
            }
        });
    }

    @Override
    public void delete(final long id) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {

            @Override
            protected Boolean jobContent() throws Exception {
                return LitPalUtils.deleteData(TabInstrument.class, "id=?", String.valueOf(id)) > 0;
            }

            @Override
            protected void jobEnd(Boolean status) {
                if (status) {
                    getView().delete_success();
                } else {
                    getView().delete_fail("删除失败");
                }
            }
        });
    }

    public void out(final long projectId) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {
            private String path;
            @Override
            protected Boolean jobContent() throws Exception {
                List<TabInstrument> datas = LitPalUtils.selectWhere(TabInstrument.class, "projectId=?", String.valueOf(projectId));
                if (datas == null || datas.size() < 0) {
                    return false;
                }
                TabProject pj = LitPalUtils.selectsoloWhere(TabProject.class,"id=?",String.valueOf(projectId));
                String[] names = ExcelUtil.colName(TabInstrument.class);
                String dir = ExcelUtil.getWorkDir();
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }
                String fname = pj.getName();
                if (!TextUtils.isEmpty(pj.getCreateAddress())){
                    fname =fname+"-"+pj.getCreateAddress();
                }
                fname=fname+"-"+DateUtils.getCurrentDateTime();
                String m_fileName = fname + ".xls";
                String makerPath = dir + "/" + m_fileName;
                path = makerPath;
                POIExcelUtil e1 = new POIExcelUtil();
                e1.writeObjListToExcelddd(datas, makerPath, null,names);
                return true;
            }

            @Override
            protected void jobEnd(Boolean status) {
                if (status) {
                    getView().out_success(path,"项目已导出到"+path);
                } else {
                    getView().out_fail("导出失败:请检查权限或内存是否足够");
                }
            }
        });
    }
}
