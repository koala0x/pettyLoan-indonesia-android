package com.myLoan.br.base;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myLoan.br.MyApplication;
import com.myLoan.br.R;
import com.myLoan.br.listener.OnClickListener;
import com.myLoan.br.tools.view.StatusBarUtil;
import com.myLoan.br.tools.view.ToastUtil;
import com.myLoan.br.view.LoadingDialog;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements BaseViewInterface {
    protected T[] presenterBase;
    private Dialog loadingDialog;

    public Context mContext;
    protected FragmentManager mManager;
    FrameLayout mflBack;
    TextView mtvTitle;
    FrameLayout mflRight;
    TextView mtvRightTitle;
    protected StringBuilder msbTopClick = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mflBack = findViewById(R.id.fl_back);
        mtvTitle = findViewById(R.id.tv_title);
        mflRight = findViewById(R.id.fl_right);
        mtvRightTitle = findViewById(R.id.tv_right);
        View fl_back = findViewById(R.id.fl_back);
        if (fl_back != null) {
            findViewById(R.id.fl_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackClickListener(v);
                }
            });
        }
        MyApplication.regitActivity(this);
        presenterBase = (T[]) oncreatePresenter();
        if (presenterBase != null) {
            for (T t : presenterBase)
                t.attachView((V) this);
        }
        mContext = this;
        mManager = getSupportFragmentManager();
        StatusBarUtil.createStatusView(this, android.R.color.transparent);
        initView();
        initListener();
        intData();

    }

    public void onBackClickListener(View view) {
        msbTopClick.append(System.currentTimeMillis()).append(",");
        finish();
    }

    /**
     * 设置右边描述
     *
     * @param title
     */
    public void setRightTitle(String title) {
        if (mtvRightTitle != null) {
            mtvRightTitle.setText(title);
        }
    }

    /**
     * 设置右边点击事件
     *
     * @param onClickListener
     */
    public void setRightOnClickListener(final OnClickListener onClickListener) {
        if (mflRight != null) {
            mflRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v);
                }
            });
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (mtvTitle != null) {
            mtvTitle.setText(title);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public abstract int getLayoutId();

    public abstract void initListener();

    public abstract void initView();

    public abstract void intData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.unRegitActivity(this);
        if (presenterBase != null) {
            for (T t : presenterBase) {
                if (t.isViewAttach()) {
                    t.detachView((V) this);
                }
            }
        }
    }

    @Override
    public void showProgessDialog() {
        if (!isFinishing()) {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog.createLoadingDialog(this, getResources().getString(R.string.data_loading));
            }
            loadingDialog.show();
        }
    }

    @Override
    public void hiddenProgessDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showFailture(int code, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(message);
            }
        });
    }

    protected abstract BasePresenter<V>[] oncreatePresenter();

}
