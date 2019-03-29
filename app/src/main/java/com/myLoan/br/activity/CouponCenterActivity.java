package com.myLoan.br.activity;

import android.app.Activity;
import android.os.Bundle;

import com.myLoan.br.R;
import com.myLoan.br.adapter.base.recyclerview.CommonAdapter;
import com.myLoan.br.adapter.base.recyclerview.base.CouponAdapter;
import com.myLoan.br.adapter.base.recyclerview.base.ViewHolder;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.bean.HelpContentBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CouponCenterActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private CommonAdapter commonAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_layout_coupon;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recyclerView_coupon);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> dataBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataBeans.add(i + "");
        }
        commonAdapter = new CouponAdapter(this, R.layout.item_coupon_canuse, dataBeans);
        mRecyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void intData() {

    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        return new BasePresenter[0];
    }


}
