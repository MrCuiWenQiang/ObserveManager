package com.zt.data.observe.presenter;

import android.text.TextUtils;

import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.bean.db.TabProject;
import com.zt.data.observe.contract.InstrumentContract;
import com.zt.data.observe.contract.InstrumentInfoContract;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;
import cn.faker.repaymodel.util.CustomUtility;
import cn.faker.repaymodel.util.DateUtils;
import cn.faker.repaymodel.util.db.DBThreadHelper;
import cn.faker.repaymodel.util.db.litpal.LitPalUtils;
import cn.faker.repaymodel.util.error.ErrorUtil;

public class InstrumentInfoPresenter extends BaseMVPPresenter<InstrumentInfoContract.View> implements InstrumentInfoContract.Presenter {


    @Override
    public void query(final long id) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<TabInstrument>() {

            @Override
            protected TabInstrument jobContent() throws Exception {
                return LitPalUtils.selectsoloWhere(TabInstrument.class, "id = ?", String.valueOf(id));
            }

            @Override
            protected void jobEnd(TabInstrument data) {
                if (data != null) {
                    getView().queryInfo(data);
                } else {
                    getView().queryInfo_Fail("没有该数据");
                }
            }
        });
    }

    @Override
    public void queryTop(final long pId) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<TabInstrument>() {

            @Override
            protected TabInstrument jobContent() throws Exception {
                return LitPalUtils.selectsoloOrderby(1, "updateDate DESC ", TabInstrument.class, "projectId = ?", String.valueOf(pId));
            }

            @Override
            protected void jobEnd(TabInstrument data) {
                getView().queryTop(data);
            }
        });
    }

    @Override
    public void saveOrRevise(final TabInstrument tabInstrument) {
        if (TextUtils.isEmpty(tabInstrument.getName())) {
            getView().save_fail("请输入基准点号");
            return;
        } else if (TextUtils.isEmpty(tabInstrument.getGcdno())) {
            getView().save_fail("请输入观测点点号");
            return;
        }

        final boolean isexit = (tabInstrument.getId() == -1);
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {

            @Override
            protected Boolean jobContent() throws Exception {
                String date = DateUtils.getCurrentDateTime();
                if (isexit) {
                    tabInstrument.setCreateDate(date);
                } else {
                    tabInstrument.setUpdateDate(date);
                }
                TabProject pj = LitPalUtils.selectsoloWhere(TabProject.class, "id=?", String.valueOf(tabInstrument.getProjectId()));
                pj.setUpdateDate(date);
                pj.save();
                return tabInstrument.save();
            }

            @Override
            protected void jobEnd(Boolean status) {
                if (status) {
                    if (isexit) {//新建
                        getView().save_new_success("新增成功");
                    } else {//修改
                        getView().save_success("修改成功");
                    }
                } else {
                    getView().save_fail("保存失败");
                }
            }
        });
    }

    private static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        a = s.reverse().toString();
        return a;
    }
}
