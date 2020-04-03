package com.zt.data.observe.contract;


import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.bean.db.TabProject;

import java.util.List;

public class InstrumentContract {
    public interface View {
        void queryProject_success(List<TabInstrument> data);

        void queryProject_fail(String msg);

        void delete_fail(String msg);

        void delete_success();

        void out_success(String path, String s);

        void out_fail(String s);
    }

    public interface Presenter {
        void query(long projectId);
        void delete(long id);
    }

    public interface Model {

    }
}
