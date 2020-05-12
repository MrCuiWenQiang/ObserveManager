package com.zt.data.observe.view.widget.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.data.observe.R;
import com.zt.data.observe.bean.db.TabCJbean;

import cn.faker.repaymodel.widget.view.dialog.BasicDialog;


public class CJDialog extends BasicDialog {


    private EditText tv_kk;
    private EditText tv_sd;
    private EditText tv_remarks;

    private TextView tv_create;
    private TextView tv_clone;
    private TextView tv_title;

    private onRegisListener regisListener;
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public CJDialog setRegisListener(onRegisListener regisListener) {
        this.regisListener = regisListener;
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dg_cj;
    }

    @Override
    public void initview(View v) {

        tv_title = v.findViewById(R.id.tv_title);
        tv_kk = v.findViewById(R.id.tv_kk);
        tv_sd = v.findViewById(R.id.tv_sd);
        tv_remarks = v.findViewById(R.id.tv_remarks);

        tv_create = v.findViewById(R.id.tv_create);
        tv_clone = v.findViewById(R.id.tv_clone);
        tv_title.setText(title);

        tv_kk.setText(data.getKongkou());
        tv_sd.setText(data.getKkshendu());
        tv_remarks.setText(data.getBeizhu());
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @Override
    protected void initListener() {
        super.initListener();
        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                regisListener.onRegistInfo(getValue(tv_kk),getValue(tv_sd),getValue(tv_remarks));
            }
        });
        tv_clone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }



    protected String getValue(EditText text) {
        if (text == null) return null;
        if (TextUtils.isEmpty(text.getText())) {
            return "";
        } else {
            return text.getText().toString();
        }
    }
private TabCJbean data;
    public void setData(TabCJbean data) {
        this.data = data;
    }

    public interface onRegisListener {
        void onRegistInfo(String... txt);
    }
}
