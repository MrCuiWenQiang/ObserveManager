package com.zt.data.observe.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.zt.data.observe.R;
import com.zt.data.observe.bean.db.TabCJbean;
import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.bean.db.TabProject;
import com.zt.data.observe.bean.db.TabSYbean;
import com.zt.data.observe.contract.CJTabContract;
import com.zt.data.observe.contract.SYTabContract;
import com.zt.data.observe.util.db.DBUtil;
import com.zt.data.observe.util.out.ExcelUtil;
import com.zt.data.observe.util.out.POIExcelUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;
import cn.faker.repaymodel.util.DateUtils;
import cn.faker.repaymodel.util.db.DBThreadHelper;
import cn.faker.repaymodel.util.db.litpal.LitPalUtils;

public class SYTabPresenter extends BaseMVPPresenter<SYTabContract.View> implements SYTabContract.Presenter {

    String date = DateUtils.getCurrentDate();

    @Override
    public void addToday(final Context context) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback() {
            @Override
            protected Object jobContent() throws Exception {
                int count = LitPalUtils.selectCount(TabSYbean.class,"createDate = ?",date);
                if (count>0){
                    return null;
                }
                InputStream inputStream = context.getResources().openRawResource(R.raw.tab_sy);
                InputStreamReader insr = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bReader = new BufferedReader(insr);
                String str;
                List<String> sqls = new ArrayList<>();
                while ((str = bReader.readLine()) != null) {
                    str = str.trim();
                    String sql = str.replace("{date}",date);

                    sqls.add(sql);
                }
                DBUtil.install(sqls);
                return null;
            }

            @Override
            protected void jobEnd(Object o) {
                query(date);
            }
        });


    }
    @Override
    public void query(final String date) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<List<TabSYbean>>() {

            @Override
            protected List<TabSYbean> jobContent() throws Exception {
                return LitPalUtils.selectWhere(TabSYbean.class,"createdate=?",date);
            }

            @Override
            protected void jobEnd(List<TabSYbean> data) {
                if (data != null && data.size() > 0) {
                    getView().queryProject_success(data);
                } else {
                    getView().queryProject_fail("暂无工程");
                }
            }
        });
    }

    @Override
    public void save(TabSYbean data,String date) {
        data.save();
        query(date);
    }
    public void out(final String date) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {
            private String path;
            @Override
            protected Boolean jobContent() throws Exception {
                List<TabSYbean> datas = LitPalUtils.selectWhere(TabSYbean.class,"createdate=?",date);
                if (datas == null || datas.size() < 0) {
                    return false;
                }
                String[] names = ExcelUtil.colName(TabSYbean.class);
                String dir = ExcelUtil.getWorkDir();
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }
                String fname = "孔隙水压力记录表"+date;

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
