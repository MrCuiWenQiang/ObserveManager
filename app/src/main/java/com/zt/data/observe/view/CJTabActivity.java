package com.zt.data.observe.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;
import com.zt.data.observe.R;
import com.zt.data.observe.bean.db.TabCJbean;
import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.contract.CJTabContract;
import com.zt.data.observe.presenter.CJTabPresenter;
import com.zt.data.observe.view.widget.dialog.CJDialog;

import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.DateUtils;
import cn.faker.repaymodel.util.error.ErrorUtil;

/**
 * 沉降
 */
public class CJTabActivity extends BaseMVPAcivity<CJTabContract.View, CJTabPresenter> implements CJTabContract.View {
    private SmartTable table;
    private String date = DateUtils.getCurrentDate();
    int row = -1;

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_cj;
    }

    @Override
    protected void initContentView() {
        setTitle("深层沉降原始记录表", R.color.white);
        setToolBarBackgroundColor(R.color.t_rule);
        table = findViewById(R.id.table);
        table.setZoom(true);
        setRightBtn("导出", R.color.white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                mPresenter.out(date);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showLoading();
        mPresenter.addToday(getApplicationContext());
    }

    @Override
    protected void initListener() {
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(ContextCompat.getColor(getContext(), R.color.tt)));
        table.getConfig().setColumnTitleStyle(new FontStyle(DensityUtils.dp2px(getContext(), 17), ContextCompat.getColor(getContext(), R.color.white)));
        table.getConfig().setContentStyle(new FontStyle(DensityUtils.dp2px(getContext(), 15), ContextCompat.getColor(getContext(), R.color.Black)));

/*        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
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
        );*/
    }

    @Override
    public void queryProject_success(final List<TabCJbean> datas) {
        dimiss();
        Column<String> column1 = new Column<>("区号", "quhao");
        Column<String> column2 = new Column<>("点号", "dianhao");
        Column<String> column3 = new Column<>("孔口标高", "kongkou");
        Column<String> column4 = new Column<>("深度", "shendu");
        Column<String> column5 = new Column<>("至孔口深度", "kkshendu");
        Column<String> column6 = new Column<>("备注", "beizhu");
        final TableData<TabCJbean> tableData = new TableData<>("", datas, column1,
                column2, column3, column4, column5, column6);
        column1.setFixed(true);
        column1.setOnColumnItemClickListener(new OnColumnItemClickListener<String>() {
            @Override
            public void onClick(Column<String> column, String value, String s, int position) {
//                row = position;
//                table.notifyDataChanged();


//                showOpenItem(datas.get(position));
                final TabCJbean data = datas.get(position);
                final CJDialog dialog = new CJDialog();
                dialog.setTitle(data.getDianhao());
                dialog.setData(data);
                dialog.setRegisListener(new CJDialog.onRegisListener() {
                    @Override
                    public void onRegistInfo(String... txt) {
                        data.setKongkou(txt[0]);
                        data.setKkshendu(txt[1]);
                        data.setBeizhu(txt[2]);
                        dialog.dismiss();
                        showLoading();
                        mPresenter.save(data, date);
                    }
                });
                dialog.show(getSupportFragmentManager(), "1");
            }
        });

        table.setTableData(tableData);
    }

    @Override
    public void queryProject_fail(String msg) {
        dimiss();
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
}
