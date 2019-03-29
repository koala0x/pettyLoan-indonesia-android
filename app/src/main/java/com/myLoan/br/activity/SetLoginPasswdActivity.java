package com.myLoan.br.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myLoan.br.Constant;
import com.myLoan.br.R;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.listener.OnClickListener;
import com.myLoan.br.listener.contract.CheckContract;
import com.myLoan.br.listener.contract.RegiterContract;
import com.myLoan.br.listener.contract.UserContract;
import com.myLoan.br.tools.loginstate.LoginContext;
import com.myLoan.br.presenter.CheckPresenter;
import com.myLoan.br.presenter.RegitPresenter;
import com.myLoan.br.presenter.UserBahaviorPresenter;
import com.myLoan.br.presenter.UserPresenter;
import com.myLoan.br.tools.view.BaseDialog;
import com.myLoan.br.tools.phonestatus.ContactUtil;
import com.myLoan.br.tools.math.MD5Util;
import com.myLoan.br.tools.view.ToastUtil;
import com.myLoan.br.view.VerificationCodeView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SetLoginPasswdActivity extends BaseActivity implements RegiterContract.IRegirView, UserContract.IForgetLoginPwView, CheckContract.IVetifyCodeView {
    private RegitPresenter regitPresenter;
    private CheckPresenter checkPresenter;
    private UserBahaviorPresenter userBahaviorPresenter;
    private UserPresenter userPresenter;
    EditText meditPasswd;
    EditText meditRePawsswd;
    Button mbtnFinshiSet;
    TextView mtvSendVertifyCode;
    private String mverifyCode;
    private String phoneNum;
    private String area;
    private String email;
    private String cpf;
    private StringBuilder msbFirstPwClick = new StringBuilder();
    private StringBuilder msbSecondPwClick = new StringBuilder();
    private StringBuilder msbNextClick = new StringBuilder();
    private int type;
    private RxPermissions rxPermissions;
    Dialog dialog;

    protected static StringBuilder msbTopClick = new StringBuilder();
    protected static StringBuilder msbBottomClick = new StringBuilder();

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_login_pass;
    }

    @Override
    public void initListener() {
        meditPasswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    msbFirstPwClick.append(System.currentTimeMillis()).append(",");
                }
            }
        });
        meditRePawsswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    msbSecondPwClick.append(System.currentTimeMillis()).append(",");
                }
            }
        });
        findViewById(R.id.fl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClickListener(v);
            }
        });
    }

    @Override
    public void initView() {
        meditPasswd = findViewById(R.id.edit_pw);
        meditRePawsswd = findViewById(R.id.edit_re_pw);
        mbtnFinshiSet = findViewById(R.id.btn_finshi_set);
//        mtvSendVertifyCode = findViewById(R.id.tv_send_vetifycode);
//        mtvPhone = findViewById(R.id.tv_phone);
    }

    @Override
    public void intData() {
        mverifyCode = getIntent().getStringExtra("verificationCode");
        phoneNum = getIntent().getStringExtra("phoneNum");
        area = getIntent().getStringExtra("area");
        email = getIntent().getStringExtra("email");
        cpf = getIntent().getStringExtra("cpf");
        type = getIntent().getIntExtra("type", 1);
        rxPermissions = new RxPermissions(this);
        if (type == Constant.VETIFYCODE_TYPE_REGIT) {
            setTitle(getString(R.string.set_login_passwd));
        } else {
            setTitle(getString(R.string.forget_login_passwd));
        }

//        mtvPhone.setText(getString(R.string.send_vetify_code_note, area, phoneNum));

    }

    public void onClickFinshiSet(View view) {
        msbNextClick.append(System.currentTimeMillis()).append(",");
        String passwd = meditPasswd.getText().toString().trim();
        String rePasswd = meditRePawsswd.getText().toString().trim();
        if (TextUtils.isEmpty(passwd) || TextUtils.isEmpty(rePasswd)) {
            ToastUtil.showToast(getString(R.string.input_passwd));
            return;
        }
//        if (TextUtils.isEmpty(mverifyCode)) {
//            ToastUtil.showToast(getString(R.string.input_vertify_code));
//            return;
//        }
        if (passwd.equals(rePasswd)) {
            if (passwd.matches(Constant.REGEX_PASSWD)) {
                //注册请求
                if (type == Constant.VETIFYCODE_TYPE_REGIT) {
                    regitPresenter.regit(area, phoneNum, MD5Util.md5(passwd), mverifyCode, getResources().getString(R.string.area));
                } else if (type == Constant.VETIFYCODE_TYPE_RESET_LOGIN) {
                    //忘记登录密码
                    userPresenter.forgetLogin(area, phoneNum, mverifyCode, MD5Util.md5(passwd), getResources().getString(R.string.area));
                }
            } else {
                ToastUtil.showToast(getString(R.string.pass_rule));
            }
        } else {
            ToastUtil.showToast(getString(R.string.input_passwd_match));
        }


    }


    @Override
    protected BasePresenter[] oncreatePresenter() {
        regitPresenter = new RegitPresenter();
        checkPresenter = new CheckPresenter();
        userBahaviorPresenter = new UserBahaviorPresenter();
        userPresenter = new UserPresenter();
        BasePresenter[] presenters = {regitPresenter, checkPresenter, userBahaviorPresenter, userPresenter};
        return presenters;
    }

    public void onBackClickListener(View view) {
        msbTopClick.append(System.currentTimeMillis()).append(",");
        finish();
    }

    @Override
    public void onBackPressed() {
        msbBottomClick.append(System.currentTimeMillis()).append(",");
        super.onBackPressed();

    }

    @Override
    public void regitSucess() {
        userBahaviorPresenter.modifyPasswdClick(msbBottomClick, msbTopClick, msbFirstPwClick, msbSecondPwClick, msbNextClick, Constant.modifyLoginPw);
        if (LoginContext.getLoginContext().isLogin()) {
            LoginContext.getLoginContext().getState().jump(this);
           /* Intent intent = new Intent(this, MainActivityKotlin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constant.Map.REGITED_KEY, Constant.Map.REGITED_VALUE);
            startActivity(intent);*/

        }
    }

    /**
     * 倒计时
     *
     * @param second
     */
    @Override
    public void downTimker(long second) {
        mtvSendVertifyCode.setText(second + "s");
        mtvSendVertifyCode.setEnabled(false);
        mtvSendVertifyCode.setClickable(false);
    }

    /**
     * 完成倒计时
     */
    @Override
    public void finshDownTinker() {
        mtvSendVertifyCode.setText(getString(R.string.send_vetify_code));
        mtvSendVertifyCode.setEnabled(true);
        mtvSendVertifyCode.setClickable(true);
    }

    @Override
    public void codeCheckSuccess() {

    }

    @Override
    public void forgetSucess() {
        finish();
    }

    @Override
    public void vetifySucee() {
        ToastUtil.showToast(getString(R.string.send_sucess));
    }

    private void goIntentDialog(String goNote, final OnClickListener onClickListener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new BaseDialog.Build(SetLoginPasswdActivity.this).setCanceledOnTouchOutside(true).
                setMessage(goNote).setPositiveButton(getResources().getString(R.string.go_seting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onClickListener.onClick(null);
            }
        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).createDialog();
        dialog.show();
    }
}
