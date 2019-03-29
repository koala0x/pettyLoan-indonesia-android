package com.myLoan.br.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.myLoan.br.R;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.tools.view.StatusBarUtil;


public class LoadLoginActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.acctivity_login_load;
    }

    @Override
    public void initListener() {


    }

    @Override
    public void initView() {

    }

    @Override
    public void intData() {
        StatusBarUtil.setColor(this,Color.WHITE,0);
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(LoadLoginActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRegitClick(View view) {
        Intent intent = new Intent(LoadLoginActivity.this, RegitActivity.class);
        startActivity(intent);
    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        return new BasePresenter[0];
    }
}
