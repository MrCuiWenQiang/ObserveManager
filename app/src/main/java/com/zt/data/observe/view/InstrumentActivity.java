package com.zt.data.observe.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orient.me.widget.rv.adapter.TableView;
import com.orient.me.widget.rv.layoutmanager.table.TableLayoutManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zt.data.observe.R;
import com.zt.data.observe.bean.TableIntCell;
import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.contract.InstrumentContract;
import com.zt.data.observe.presenter.InstrumentPresenter;
import com.zt.data.observe.view.adapter.INTableAdapter;
import com.zt.data.observe.view.adapter.InstrumentAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.SpaceItemDecoration;
import cn.faker.repaymodel.util.ToastUtility;
import cn.faker.repaymodel.widget.view.BaseRecycleView;
@Deprecated
public class InstrumentActivity extends BaseMVPAcivity<InstrumentContract.View, InstrumentPresenter> implements InstrumentContract.View, View.OnClickListener {

//    InstrumentAdapter adapter = new InstrumentAdapter();

    private RecyclerView rv_list;
    private TextView tv_add;
    private long projectId;
    private static final String TAG_PID = "TAG_PID";
    private TableView mTable;

    INTableAdapter adapter;

    public static Bundle newInstance(long pid) {
        Bundle args = new Bundle();
        args.putLong(TAG_PID, pid);
        return args;
    }

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_instrument;
    }

    @Override
    protected void initContentView() {
        setTitle("观测点列表", R.color.white);
        setToolBarBackgroundColor(R.color.t_rule);
        mTable = findViewById(R.id.mtable);
        rv_list = findViewById(R.id.rv_ll);
        tv_add = findViewById(R.id.tv_add);
//        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv_list.addItemDecoration(new SpaceItemDecoration(5));
//        rv_list.setAdapter(adapter);

        adapter = new INTableAdapter(null);
        mTable.setAdapter(adapter);
        mTable.setTitle(false,false);
        mTable.setModeAndValue(TableLayoutManager.MODE_A, 1, 2);
        adapter.setOnItemClickListener(new BaseRecycleView.OnItemClickListener<TableIntCell>() {
            @Override
            public void onItemClick(View view, TableIntCell data, int position) {
                InstrumentInfoActivity.newInstance(InstrumentActivity.this, data.getId(), -1, 100);
            }
        });
        setRightBtn("导出", R.color.white,new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                mPresenter.out(projectId);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showLoading();
        projectId = getIntent().getLongExtra(TAG_PID, -1);
        mPresenter.query(projectId);
    }

    String[] item = {"删除"};

    @Override
    protected void initListener() {
        super.initListener();
        tv_add.setOnClickListener(this);
/*        adapter.setOnItemClickListener(new BaseRecycleView.OnItemClickListener<TabInstrument>() {
            @Override
            public void onItemClick(View view, TabInstrument data, int position) {
                InstrumentInfoActivity.newInstance(InstrumentActivity.this, data.getId(), data.getProjectId(), 100);
            }
        });
        adapter.setOnItemLongClickListener(new BaseRecycleView.OnItemLongClickListener<TabInstrument>() {
            @Override
            public void onItemClick(View view, TabInstrument data, int position) {
                showOpenItem(position, data);
            }
        });*/
    }

    private void showOpenItem(int position, final TabInstrument data) {
        showListDialog(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0: {
                        dialogInterface.dismiss();
                        showDialog("是否删除该点", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                showLoading();
                                mPresenter.delete(data.getId());
                            }
                        });
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add: {
                InstrumentInfoActivity.newInstance(InstrumentActivity.this, -1, projectId, 101);
                break;
            }
        }
    }

    @Override
    public void queryProject_success(List<TabInstrument> datas) {
        dimiss();
//        adapter.setDatas(data);
        List<TableIntCell> tableIntCells = new ArrayList<>();
        tableIntCells.add(new TableIntCell("基准点名", "1", 1, 1, 1, 1, 1));
        tableIntCells.add(new TableIntCell("基准高程", "1", 1, 1, 2, 1, 1));
        tableIntCells.add(new TableIntCell("仪器高", "1", 1, 1, 3, 1, 1));
        tableIntCells.add(new TableIntCell("盘左", "1", 1, 1, 4, 1, 1));
        tableIntCells.add(new TableIntCell("盘右", "1", 1, 1, 5, 1, 1));
        tableIntCells.add(new TableIntCell("棱镜高", "1", 1, 1, 6, 1, 1));
        tableIntCells.add(new TableIntCell("平距", "1", 1, 1, 7, 1, 1));
        tableIntCells.add(new TableIntCell("创建时间", "1", 1, 1, 8, 1, 1));
        tableIntCells.add(new TableIntCell("修改时间", "1", 1, 1, 9, 1, 1));


        for (int i = 0; i < datas.size(); i++) {
            TabInstrument tab = datas.get(i);

            TableIntCell item1 = new TableIntCell();
            item1.setName("基准点名");
            item1.setValue(tab.getName());
            item1.setCol(1);
            item1.setRow(i+2);
            item1.setId(tab.getId());

            TableIntCell item2 = new TableIntCell();
            item2.setName("基准高程");
            item2.setValue(tab.getJzgc());
            item2.setCol(2);
            item2.setRow(i+2);
            item2.setId(tab.getId());


            TableIntCell item3 = new TableIntCell();
            item3.setName("仪器高");
            item3.setValue(tab.getYqg());
            item3.setCol(3);
            item3.setRow(i+2);
            item3.setId(tab.getId());


            TableIntCell item4 = new TableIntCell();
            item4.setName("垂直角 盘左");
            item4.setValue(tab.getCzjLeft());
            item4.setCol(4);
            item4.setRow(i+2);
            item4.setId(tab.getId());

            TableIntCell item5 = new TableIntCell();
            item5.setName("垂直角 盘右");
            item5.setValue(tab.getCzjRight());
            item5.setCol(5);
            item5.setRow(i+2);
            item5.setId(tab.getId());

            TableIntCell item6 = new TableIntCell();
            item6.setName("棱镜高");
            item6.setValue(tab.getLjg());
            item6.setCol(6);
            item6.setRow(i+2);
            item6.setId(tab.getId());

            TableIntCell item7 = new TableIntCell();
            item7.setName("平距");
            item7.setValue(tab.getPj());
            item7.setCol(7);
            item7.setRow(i+2);
            item7.setId(tab.getId());

            TableIntCell item8 = new TableIntCell();
            item8.setName("创建时间");
            item8.setValue(tab.getCreateDate());
            item8.setCol(8);
            item8.setRow(i+2);
            item8.setWidthSpan(1);
            item8.setId(tab.getId());

            TableIntCell item9 = new TableIntCell();
            item9.setName("修改时间");
            item9.setValue(tab.getUpdateDate());
            item9.setCol(9);
            item9.setRow(i+2);
            item9.setWidthSpan(1);
            item9.setId(tab.getId());

            tableIntCells.add(item1);
            tableIntCells.add(item2);
            tableIntCells.add(item3);
            tableIntCells.add(item4);
            tableIntCells.add(item5);
            tableIntCells.add(item6);
            tableIntCells.add(item7);
            tableIntCells.add(item8);
            tableIntCells.add(item9);
        }

        adapter.setdata(tableIntCells);
        mTable.post(new Runnable() {
            @Override
            public void run() {
                mTable.reMeasure();
            }
        });
    }

    @Override
    public void queryProject_fail(String msg) {
        dimiss();
//        ToastUtility.showToast(msg);
    }

    @Override
    public void delete_fail(String msg) {
        dimiss();
        ToastUtility.showToast(msg);
    }

    @Override
    public void delete_success() {
        showLoading();
        mPresenter.query(projectId);
    }

    @Override
    public void out_success(String path, String s) {
        dimiss();
        showDialog(s);
    }

    @Override
    public void out_fail(String s) {
        dimiss();
        showDialog(s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 || requestCode == 101) {
            if (resultCode == 200) {
                showLoading();
                mPresenter.query(projectId);
            }
        }
    }
}
