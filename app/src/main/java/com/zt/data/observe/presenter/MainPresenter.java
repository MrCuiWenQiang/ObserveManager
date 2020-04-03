package com.zt.data.observe.presenter;

import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.bean.db.TabProject;
import com.zt.data.observe.contract.MainContract;
import com.zt.data.observe.util.db.DBUtil;

import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;
import cn.faker.repaymodel.util.DateUtils;
import cn.faker.repaymodel.util.db.DBThreadHelper;
import cn.faker.repaymodel.util.db.litpal.LitPalUtils;

public class MainPresenter extends BaseMVPPresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void queryProject() {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<List<TabProject>>() {

            @Override
            protected List<TabProject> jobContent() throws Exception {
                // TODO: 2020/4/2 修改为多表查询
                return DBUtil.getProjectList();
            }

            @Override
            protected void jobEnd(List<TabProject> sysProjectEntities) {
                if (sysProjectEntities != null && sysProjectEntities.size() > 0) {
                    getView().queryProject_success(sysProjectEntities);
                } else {
                    getView().queryProject_fail("暂无工程");
                }
            }
        });
    }

    @Override
    public void createProject(final String name,final String createAddress,final String remarks) {
        {
            if (name==null){
                getView().createProject_fail("创建失败:名称不能为空");
                return;
            }
            DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {

                @Override
                protected Boolean jobContent() throws Exception {
                    int count = LitPalUtils.selectCount(TabProject.class, "name = ?", name);
                    if (count > 0) {
                        return false;
                    }
                    String createTimer = DateUtils.getCurrentDateTime();
                    TabProject entity = new TabProject();
                    entity.setName(name);
                    entity.setRemarks(remarks);
                    entity.setCreateDate(createTimer);
                    entity.setCreateAddress(createAddress);
                    return entity.save();
                }

                @Override
                protected void jobEnd(Boolean aBoolean) {
                    if (aBoolean){
                        queryProject();
                    }else {
                        getView().createProject_fail("创建失败:已存在相同名称");
                    }
                }
            });
        }
    }
}
