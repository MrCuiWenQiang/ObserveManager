package com.zt.data.observe.view;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zt.data.observe.R;
import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.bean.db.TabProject;
import com.zt.data.observe.contract.MainContract;
import com.zt.data.observe.presenter.MainPresenter;
import com.zt.data.observe.view.adapter.MainProjectAdapter;
import com.zt.data.observe.view.widget.dialog.RegistDialog;

import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.SpaceItemDecoration;
import cn.faker.repaymodel.util.ToastUtility;
import cn.faker.repaymodel.widget.view.BaseRecycleView;

public class MainActivity extends BaseMVPAcivity<MainContract.View, MainPresenter> implements MainContract.View, View.OnClickListener {

    String[] PERMISSIONS_STORAGE = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    private RegistDialog registDialog = new RegistDialog();
    private SmartTable table;
    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView() {
        setTitle("项目列表", R.color.white);
        setToolBarBackgroundColor(R.color.t_rule);
        isShowBackButton(false);



        setRightBtn("创建项目", R.color.white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

/*        adapter.setOnItemClickListener(new BaseRecycleView.OnItemClickListener<TabProject>() {
            @Override
            public void onItemClick(View view, TabProject data, int position) {
                Bundle bundle = InstrumentActivity.newInstance(data.getId());
//                Intent intent = new Intent(MainActivity.this,InstrumentActivity.class);
                Intent intent = new Intent(MainActivity.this,InstrumentNowActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/

        table = findViewById(R.id.table);
        table.setZoom(true);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(),R.color.select_color)));
        table.getConfig().setColumnTitleStyle(new FontStyle(DensityUtils.dp2px(getContext(),17),ContextCompat.getColor(getContext(),R.color.white)));
        table.getConfig().setContentStyle(new FontStyle(DensityUtils.dp2px(getContext(),17),ContextCompat.getColor(getContext(),R.color.Black)) );
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        int write_permission = ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        if (write_permission != 0) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
        }

        int read_permission = ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        if (read_permission != 0) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
        }
        showLoading();
        mPresenter.queryProject();
    }

    @Override
    protected void initListener() {
        super.initListener();
        registDialog.setRegisListener(new RegistDialog.onRegisListener() {
            @Override
            public void onRegistInfo(String... txt) {
                mPresenter.createProject(txt[0],txt[1],txt[2]);
            }
        });
    }

    @Override
    public void queryProject_success(final List<TabProject> datas) {
        dimiss();
        Column<String> column1 = new Column<>("项目名称", "name");
        Column<String> column2 = new Column<>("数量", "count");
        Column<String> column3 = new Column<>("创建人", "createUser");
        Column<String> column4 = new Column<>("上次修改", "updateDate");
        Column<String> column5 = new Column<>("创建时间", "createDate");
        Column<String> column6 = new Column<>("创建地点", "createAddress");
        Column<String> column7 = new Column<>("备注", "remarks");
        final TableData<TabProject> tableData = new TableData<>("", datas, column1,
                column2, column3, column4, column5, column6, column7);
        column1.setFixed(true);
        column1.setOnColumnItemClickListener(new OnColumnItemClickListener<String>() {
            @Override
            public void onClick(Column<String> column, String value, String s, int position) {
                Bundle bundle = InstrumentNowActivity.newInstance(datas.get(position).getId(),datas.get(position).getName());
                Intent intent = new Intent(MainActivity.this,InstrumentNowActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        table.setTableData(tableData);

    }

    @Override
    public void queryProject_fail(String msg) {
        dimiss();
        ToastUtility.showToast(msg);
    }

    @Override
    public void createProject_fail(String msg) {
        dimiss();
        ToastUtility.showToast(msg);
    }

    private void showEditDialog() {
        registDialog.show(getSupportFragmentManager(),"cr");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showLoading();
        mPresenter.queryProject();
    }
}
