package com.myLoan.br.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.myLoan.br.R;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.tools.view.ToastUtil;

import static com.myLoan.br.Constant.WebPath.FEEDBACK;

/**
 * @author wzx
 * @version 3.0.0 2019/2/11 16:53
 * @update wzx 2019/2/11 16:53
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class OnLineCheckActivity extends BaseActivity {

    private LinearLayout linearLayoutProduct;
    private LinearLayout linearLayoutLoan;

    @Override
    public int getLayoutId() {
        return R.layout.activity_onlinecheck;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        setTitle(getString(R.string.check_online));
        linearLayoutProduct = findViewById(R.id.linear_product);
        linearLayoutLoan = findViewById(R.id.linear_loan);
        linearLayoutLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(getString(R.string.no_action_open));
            }
        });

        linearLayoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OnLineCheckActivity.this, WebViewActivity.class);
                intent1.putExtra("urlPath", FEEDBACK);
                startActivity(intent1);
//                Intent intent = new Intent(OnLineCheckActivity.this, FeedbackActivity.class);
//                intent.putExtra("urlPath", MAIN_INFORMATION);
            }
        });
    }

    @Override
    public void intData() {

    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        return new BasePresenter[0];
    }
}
