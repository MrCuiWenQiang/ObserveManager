package com.zt.data.observe.contract;


import android.content.Context;

import com.zt.data.observe.bean.db.TabCJbean;
import com.zt.data.observe.bean.db.TabInstrument;

import java.util.List;

public class CJTabContract {
    public interface View {
        void queryProject_success(List<TabCJbean> data);

        void queryProject_fail(String msg);

        void out_success(String path, String s);

        void out_fail(String s);
    }

    public interface Presenter {
        void addToday(Context context);
        void query(String date);
        void save(TabCJbean data,String date);
    }

    public interface Model {

    }
}
