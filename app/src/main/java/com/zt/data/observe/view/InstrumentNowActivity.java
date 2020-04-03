package com.zt.data.observe.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;
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

import java.util.ArrayList;
import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.ToastUtility;
import cn.faker.repaymodel.widget.view.BaseRecycleView;

/**
 * 新版表格
 */
public class InstrumentNowActivity extends BaseMVPAcivity<InstrumentContract.View, InstrumentPresenter> implements InstrumentContract.View, View.OnClickListener {

//    InstrumentAdapter adapter = new InstrumentAdapter();

    private TextView tv_add;
    private TextView tv_count;
    private long projectId;
    private static final String TAG_PID = "TAG_PID";
    private static final String TAG_TITLE = "TAG_TITLE";
    private SmartTable table;
    int row = -1;

    public static Bundle newInstance(long pid,String name) {
        Bundle args = new Bundle();
        args.putLong(TAG_PID, pid);
        args.putString(TAG_TITLE, name);
        return args;
    }

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_nowinstrument;
    }

    @Override
    protected void initContentView() {
        setToolBarBackgroundColor(R.color.t_rule);
        tv_add = findViewById(R.id.tv_add);
        tv_count = findViewById(R.id.tv_count);

        table = findViewById(R.id.table);
        table.setZoom(true);

        setRightBtn("导出", R.color.white, new View.OnClickListener() {
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
        String title = getIntent().getStringExtra(TAG_TITLE);
        setTitle(title, R.color.white);
    }

    String[] item = {"编辑", "删除"};

    @Override
    protected void initListener() {
        super.initListener();
        tv_add.setOnClickListener(this);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(),R.color.tt)));
        table.getConfig().setColumnTitleStyle(new FontStyle(DensityUtils.dp2px(getContext(),17),ContextCompat.getColor(getContext(),R.color.white)));
        table.getConfig().setContentStyle(new FontStyle(DensityUtils.dp2px(getContext(),15),ContextCompat.getColor(getContext(),R.color.Black)) );

        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (row!=-1&&cellInfo.row == row) {
                    return ContextCompat.getColor(getContext(), R.color.blue_2);
                }
                return TableConfig.INVALID_COLOR;
            }
        });
        table.getConfig().setYSequenceCellBgFormat(
                new BaseCellBackgroundFormat<Integer>() {
                    @Override
                    public int getBackGroundColor(Integer position) {
                        if (row!=-1&&position-2 == row) {
                            return ContextCompat.getColor(getContext(), R.color.blue_2);
                        }
                        return TableConfig.INVALID_COLOR;

                    }

                    @Override
                    public int getTextColor(Integer position) {
                        if (row!=-1&&position-2 == row) {
                            return ContextCompat.getColor(getContext(), R.color.white);
                        }
                        return TableConfig.INVALID_COLOR;
                    }
                }
        );
    }

    private void showOpenItem(final TabInstrument data) {
        showListDialog("选中项:" + data.getName(), item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                row=-1;
                table.notifyDataChanged();
                switch (i) {
                    case 0: {
                        dialogInterface.dismiss();
                        InstrumentInfoActivity.newInstance(InstrumentNowActivity.this, data.getId(), -1, 100);
                        break;
                    }
                    case 1: {
                        dialogInterface.dismiss();
                        showDialog("是否删除点:" + data.getName(), new QMUIDialogAction.ActionListener() {
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
                InstrumentInfoActivity.newInstance(InstrumentNowActivity.this, -1, projectId, 101);
                break;
            }
        }
    }


    @Override
    public void queryProject_success(final List<TabInstrument> datas) {
        dimiss();
        Column<String> column1 = new Column<>("基准点名", "name");
        Column<String> column2 = new Column<>("基准高程", "jzgc");
        Column<String> column3 = new Column<>("仪器高", "yqg");
        Column<String> column4 = new Column<>("观测点点号", "gcdno");
        Column<String> column5 = new Column<>("垂直角 盘左", "czjLeft");
        Column<String> column6 = new Column<>("垂直角 盘右", "czjRight");
        Column<String> column7 = new Column<>("棱镜高", "ljg");
        Column<String> column8 = new Column<>("平距", "pj");
        Column<String> column9 = new Column<>("创建时间", "createDate");
        Column<String> column10 = new Column<>("修改时间", "updateDate");
        final TableData<TabInstrument> tableData = new TableData<>("", datas, column1,
                column2, column3, column4, column5, column6, column7, column8, column9, column10);
        column1.setFixed(true);
        column1.setOnColumnItemClickListener(new OnColumnItemClickListener<String>() {
            @Override
            public void onClick(Column<String> column, String value, String s, int position) {
                row = position;
                table.notifyDataChanged();
                showOpenItem(datas.get(position));

            }
        });

        table.setTableData(tableData);
        tv_count.setText("总数:" + datas.size());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(200);
    }
}
