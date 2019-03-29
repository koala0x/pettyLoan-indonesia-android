package com.myLoan.br.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.myLoan.br.R;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.listener.contract.RegiterContract;
import com.myLoan.br.presenter.RegitPresenter;

public class SplashActivity extends BaseActivity implements RegiterContract.IRegirView {
    RegitPresenter regitPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void intData() {
        regitPresenter.downTimer(3);
    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        regitPresenter = new RegitPresenter();
        BasePresenter[] presenters = {regitPresenter};
        return presenters;
    }

    @Override
    public void regitSucess() {

    }

    @Override
    public void downTimker(long second) {

    }

    @Override
    public void finshDownTinker() {
        Intent intent = new Intent(SplashActivity.this, MainActivityKotlin.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void codeCheckSuccess() {

    }
}
