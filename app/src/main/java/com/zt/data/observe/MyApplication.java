package com.zt.data.observe;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.zt.data.observe.util.db.DBUtil;

import org.litepal.LitePal;

import cn.faker.repaymodel.BasicApplication;
import cn.faker.repaymodel.util.LocImageUtility;
import cn.faker.repaymodel.util.LogUtil;
import cn.faker.repaymodel.util.ToastUtility;

public class MyApplication extends BasicApplication {

    public static String userId = null;

    @Override
    public void onCreate() {
        super.onCreate();

        setting();
    }


    private void setting() {
        LogUtil.isShow = true;
        ToastUtility.setToast(getApplicationContext());
        LitePal.initialize(this);
        LogUtil.isShow = true;
        LocImageUtility.setImageUtility(this);
//        HttpHelper.setOnFailedAll(new OnFileClass());
        DBUtil.init(getContext());
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
