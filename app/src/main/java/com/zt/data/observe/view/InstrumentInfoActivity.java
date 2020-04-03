package com.zt.data.observe.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zt.data.observe.R;
import com.zt.data.observe.bean.db.TabInstrument;
import com.zt.data.observe.contract.InstrumentInfoContract;
import com.zt.data.observe.presenter.InstrumentInfoPresenter;
import com.zt.data.observe.presenter.InstrumentPresenter;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.ToastUtility;

/**
 * 编辑详情
 */
public class InstrumentInfoActivity extends BaseMVPAcivity<InstrumentInfoContract.View, InstrumentInfoPresenter> implements InstrumentInfoContract.View, View.OnClickListener {

    private EditText ed_jzdm;
    private EditText ed_jzgc;
    private EditText ed_gcno;
    private EditText ed_yqg;
    private EditText ed_czjleft;
    private EditText ed_czjright;
    private EditText ed_ljg;
    private EditText ed_pj;

    private TextView tv_save;
    private TextView tv_exit;

    private TabInstrument instrument;
    private long projectId;//所属项目

    private static final String TAG_ID = "TAG_ID";
    private static final String TAG_PID = "TAG_PID";
    private int resultCode = 200;

    public static void newInstance(Activity ac, long id, long pid, int resultCode) {

        Bundle args = new Bundle();
        args.putLong(TAG_ID, id);
        args.putLong(TAG_PID, pid);
        Intent intent = new Intent(ac.getBaseContext(), InstrumentInfoActivity.class);
        intent.putExtras(args);
        ac.startActivityForResult(intent, resultCode);
    }

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_instrument_info;
    }

    @Override
    protected void initContentView() {
        setTitle("观测点详情", R.color.white);
        setToolBarBackgroundColor(R.color.t_rule);
        ed_jzdm = findViewById(R.id.ed_jzdm);
        ed_jzgc = findViewById(R.id.ed_jzgc);
        ed_yqg = findViewById(R.id.ed_yqg);
        ed_czjleft = findViewById(R.id.ed_czjleft);
        ed_czjright = findViewById(R.id.ed_czjright);
        ed_ljg = findViewById(R.id.ed_ljg);
        ed_pj = findViewById(R.id.ed_pj);
        ed_gcno = findViewById(R.id.ed_gcno);

        tv_save = findViewById(R.id.tv_save);
        tv_exit = findViewById(R.id.tv_exit);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        long id = getIntent().getLongExtra(TAG_ID, -1);
        showLoading();
        if (id != -1) {
            mPresenter.query(id);
        } else {
            projectId = getIntent().getLongExtra(TAG_PID, -1);
            mPresenter.queryTop(projectId);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_save.setOnClickListener(this);
        tv_exit.setOnClickListener(this);

       /* ed_czjleft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    float value = Float.valueOf(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save: {
                tosave();
                break;
            }
            case R.id.tv_exit: {
                finish();
                break;
            }
        }
    }

    private void tosave() {
        if (instrument == null) {
            instrument = new TabInstrument();
            instrument.setProjectId(projectId);
        }
        instrument.setName(getValue(ed_jzdm));
        instrument.setJzgc(getValuetofloat(ed_jzgc));
        instrument.setGcdno(getValue(ed_gcno));
        instrument.setYqg(getValuetofloat(ed_yqg));
        instrument.setCzjLeft(getValuetofloat(ed_czjleft));
        instrument.setCzjRight(getValuetofloat(ed_czjright));
        instrument.setLjg(getValuetofloat(ed_ljg));
        instrument.setPj(getValuetofloat(ed_pj));
        showLoading();
        mPresenter.saveOrRevise(instrument);
    }

    @Override
    public void queryTop(TabInstrument data) {
        dimiss();
        if (data == null) return;
        ed_jzdm.setText(String.valueOf(data.getName()));
        ed_jzgc.setText(String.valueOf(data.getJzgc()));
        ed_yqg.setText(String.valueOf(data.getYqg()));
        ed_gcno.setText(String.valueOf(data.getGcdno()));

        ed_czjleft.setText(String.valueOf(data.getCzjLeft()));
        ed_czjright.setText(String.valueOf(data.getCzjRight()));
        ed_ljg.setText(String.valueOf(data.getLjg()));
        ed_pj.setText(String.valueOf(data.getPj()));
    }

    @Override
    public void queryInfo(TabInstrument data) {
        instrument = data;
        ed_jzdm.setText(String.valueOf(data.getName()));
        ed_jzgc.setText(String.valueOf(data.getJzgc()));
        ed_yqg.setText(String.valueOf(data.getYqg()));
        ed_gcno.setText(String.valueOf(data.getGcdno()));


        ed_czjleft.setText(String.valueOf(data.getCzjLeft()));
        ed_czjright.setText(String.valueOf(data.getCzjRight()));
        ed_ljg.setText(String.valueOf(data.getLjg()));
        ed_pj.setText(String.valueOf(data.getPj()));
        dimiss();
    }

    @Override
    public void queryInfo_Fail(String msg) {
        dimiss();
        showDialog(msg, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public void save_success(String msg) {
        dimiss();
        ToastUtility.showToast(msg);
        setResult(resultCode);
        finish();
        /*showDialog(msg, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });*/
    }

    @Override
    public void save_fail(String msg) {
        dimiss();
        setResult(200);
        showDialog(msg);
    }

    @Override
    public void save_new_success(String msg) {
        dimiss();
        instrument = null;
        setResult(200);
        ToastUtility.showToast(msg);
    }
}
