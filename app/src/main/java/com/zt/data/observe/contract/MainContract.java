package com.zt.data.observe.contract;


import com.zt.data.observe.bean.db.TabProject;

import java.util.List;

public class MainContract {
    public interface View {
        void queryProject_success(List<TabProject> sysProjectEntities);

        void queryProject_fail(String msg);

        void createProject_fail(String s);
    }

    public interface Presenter {
        void queryProject();

        void createProject(final String name,final String createAddress,final String remarks);
    }

    public interface Model {

    }
}
