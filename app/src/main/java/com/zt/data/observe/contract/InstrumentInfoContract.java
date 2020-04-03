package com.zt.data.observe.contract;


import com.zt.data.observe.bean.db.TabInstrument;

import java.util.List;

public class InstrumentInfoContract {
    public interface View {

        void queryTop(TabInstrument data);

        void queryInfo(TabInstrument data);

        void queryInfo_Fail(String msg);

        void save_success(String msg);

        void save_fail(String msg);

        void save_new_success(String msg);
    }

    public interface Presenter {
        void query(long id);
        void queryTop(long pId);
        void saveOrRevise(TabInstrument tabInstrument);
    }

    public interface Model {

    }
}
