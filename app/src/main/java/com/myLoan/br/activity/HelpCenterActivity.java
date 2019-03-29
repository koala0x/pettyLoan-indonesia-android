package com.myLoan.br.activity;

import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myLoan.br.R;
import com.myLoan.br.adapter.base.recyclerview.AddItemDecoration;
import com.myLoan.br.adapter.base.recyclerview.CommonAdapter;
import com.myLoan.br.adapter.base.recyclerview.base.HelpAdapter;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.bean.HelpContentBean;
import com.myLoan.br.listener.IHelpcenterView;
import com.myLoan.br.presenter.HelpCenterPresenter;
import com.myLoan.br.tools.view.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class HelpCenterActivity extends BaseActivity implements IHelpcenterView {

    private HelpCenterPresenter helpCenterPresenter;

    private RecyclerView mRecyclerView;
    private CommonAdapter commonAdapter;
    private LinearLayout callLinearLayout;
    private LinearLayout onlineLinearLayout;


    private List<HelpContentBean.DataBean> mlistHelpCenter = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    public void initListener() {
        callLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + 123456));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        onlineLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpCenterActivity.this, OnLineCheckActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initView() {

        callLinearLayout = findViewById(R.id.linear_call);
        onlineLinearLayout = findViewById(R.id.linear_online);

        setTitle(getResources().getString(R.string.help_center));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView = findViewById(R.id.center_RecyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new AddItemDecoration(DensityUtil.dip2px(this, 15), DensityUtil.dip2px(this, 15), DensityUtil.dip2px(this, 0), DensityUtil.dip2px(this, 15)));

    }

    @Override
    public void intData() {
        helpCenterPresenter.getHelpCenter();

    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        helpCenterPresenter = new HelpCenterPresenter();
        BasePresenter prestener[] = {helpCenterPresenter};
        return prestener;
    }

    TextView helpfulnessTextView;
    TextView unhelpfulnessTextView;
    String usefulEnum;

    @Override
    public void helpcenterSucess(List<HelpContentBean.DataBean> dataBeans) {
        Log.d("dataBeanss", dataBeans.size() + "");
        mlistHelpCenter = dataBeans;
        commonAdapter = new HelpAdapter(this, R.layout.item_help_center, dataBeans);
        ((HelpAdapter) commonAdapter).setOnclickUse(new HelpAdapter.OnclickUse() {
            @Override
            public void onClickUseful(int id) {
                helpCenterPresenter.getHelpCenterChose(id, "USEFUL");
            }

            @Override
            public void onClickUseless(int id) {
                helpCenterPresenter.getHelpCenterChose(id, "USELESS");
            }

            @Override
            public void onClickNotSelect(int id) {
                helpCenterPresenter.getHelpCenterChose(id, "NOT_SELECT");
            }
        });
        mRecyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void helpcenterFail(String massage) {

    }

    @Override
    public void helpcenterChoseSucess(String usefulEnum) {
    }

    @Override
    public void helpcenterChoseFail(String massage) {

    }
}
