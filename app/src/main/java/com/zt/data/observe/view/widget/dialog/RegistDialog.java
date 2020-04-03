package com.zt.data.observe.view.widget.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.zt.data.observe.R;

import cn.faker.repaymodel.widget.view.dialog.BasicDialog;

/**
 * 发送注册信息
 */
public class RegistDialog extends BasicDialog {


    private EditText tv_name;
    private EditText tv_address;
    private EditText tv_remarks;

    private TextView tv_create;
    private TextView tv_clone;

    private onRegisListener regisListener;


    public RegistDialog setRegisListener(onRegisListener regisListener) {
        this.regisListener = regisListener;
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dg_regist;
    }

    @Override
    public void initview(View v) {

        tv_name = v.findViewById(R.id.tv_name);
        tv_address = v.findViewById(R.id.tv_address);
        tv_remarks = v.findViewById(R.id.tv_remarks);

        tv_create = v.findViewById(R.id.tv_create);
        tv_clone = v.findViewById(R.id.tv_clone);
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
                regisListener.onRegistInfo(getValue(tv_name),getValue(tv_address),getValue(tv_remarks));
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
            return null;
        } else {
            return text.getText().toString();
        }
    }

    public interface onRegisListener {
        void onRegistInfo(String... txt);
    }
}
