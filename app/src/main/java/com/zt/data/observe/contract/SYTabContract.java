package com.zt.data.observe.contract;


import android.content.Context;

import com.zt.data.observe.bean.db.TabCJbean;
import com.zt.data.observe.bean.db.TabSYbean;

import java.util.List;

public class SYTabContract {
    public interface View {
        void queryProject_success(List<TabSYbean> data);

        void queryProject_fail(String msg);

        void out_success(String path, String s);

        void out_fail(String s);
    }

    public interface Presenter {
        void addToday(final Context context);
        void query(final String date);
        void save(TabSYbean data,String date);
    }

    public interface Model {

    }
}
