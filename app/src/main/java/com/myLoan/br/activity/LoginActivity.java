package com.myLoan.br.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.Nullable;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
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
import com.myLoan.br.listener.contract.LoginContract;
import com.myLoan.br.tools.map.MyLocation;
import com.myLoan.br.tools.loginstate.LoginContext;
import com.myLoan.br.presenter.EventRecordPresenter;
import com.myLoan.br.presenter.LocationPresener;
import com.myLoan.br.presenter.LoginPrensenter;
import com.myLoan.br.presenter.UserBahaviorPresenter;
import com.myLoan.br.tools.view.BaseDialog;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.tools.phonestatus.DeviceUtil;
import com.myLoan.br.tools.phonestatus.LocationUtil;
import com.myLoan.br.tools.math.MD5Util;
import com.myLoan.br.tools.view.StringUtil;
import com.myLoan.br.tools.view.ToastUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class LoginActivity extends BaseActivity implements LoginContract.ILoginView {
    EditText meditPhone;
    EditText meditPasswd;
    Button mbtnLogin;
    TextView mtvForgetPw;
    TextView mtvGoRegit;
    private LoginPrensenter loginPrensenter;
    private LocationPresener locationPresener;
    private UserBahaviorPresenter userBahaviorPresenter;
    private EventRecordPresenter eventRecordPresenter;
    private String areaCode = Constant.area;
    private RxPermissions rxPermissions;
    private boolean isLocation = false;

    private StringBuilder msbPhoneClick = new StringBuilder();
    private StringBuilder msbPasswdClick = new StringBuilder();
    private StringBuilder msbLoginClick = new StringBuilder();
    private static StringBuilder msbackClick = new StringBuilder();
    private boolean phoneStatePermision = false;
    private boolean isStartLocationPermision = false;
    private int GPS_REQUEST_CODE = 10;
    Dialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initListener() {
        meditPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    msbPhoneClick.append(System.currentTimeMillis()).append(",");
                }
            }
        });
        meditPasswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    msbPasswdClick.append(System.currentTimeMillis()).append(",");
                }
            }
        });

    }


    @Override
    public void initView() {
        meditPhone = findViewById(R.id.edit_phone);
        meditPasswd = findViewById(R.id.edit_pw);
        mbtnLogin = findViewById(R.id.btn_login);
        mtvForgetPw = findViewById(R.id.tv_forget_pw);
        mtvGoRegit = findViewById(R.id.tv_go_regit);
        Log.i("wzxxxxxxx", StringUtil.getLanguage());

    }

    @Override
    public void intData() {
        rxPermissions = new RxPermissions(this);
        SpannableString goRegitSp = new SpannableString(getResources().getString(R.string.go_regit));
        mtvGoRegit.setHighlightColor(getResources().getColor(android.R.color.transparent));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, RegitActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.theme_color));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };

        String language = StringUtil.getLanguage();
        if (language.equals("in-ID")) { //印尼语
            goRegitSp.setSpan(clickableSpan, 16, goRegitSp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        } else if (language.equals("pt-BR")) {//葡萄牙语
            goRegitSp.setSpan(clickableSpan, 14, goRegitSp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        } else if (language.equals("zh-CN")) {//汉语
            goRegitSp.setSpan(clickableSpan, 14, goRegitSp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
//        goRegitSp.setSpan(clickableSpan, 14, goRegitSp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mtvGoRegit.setText(goRegitSp);
        mtvGoRegit.setMovementMethod(LinkMovementMethod.getInstance());
        getLocation();
        readPhoneState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isStartLocationPermision) {
            isStartLocationPermision = false;
            getLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constant.startOpenGps) {
            Constant.startOpenGps = false;
            getLocation();
        }
        //     getLocation();
    }

    public void onClickLogin(View view) {
        msbLoginClick.append(System.currentTimeMillis()).append(",");
        String phoneNum = meditPhone.getText().toString().trim();
        String passwd = meditPasswd.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showToast(getString(R.string.input_phone));
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            ToastUtil.showToast(getString(R.string.input_passwd));
            return;
        }
//        if (!isLocation) {
//            // ToastUtil.showToast(getString(R.string.location_permission));
//            getLocation();
//            return;
//        }
        DebugLog.i("wang", "====click====longitude=" + MyLocation.getInstance().longitude + "===latitude===" + MyLocation.getInstance().latitude);
//        if (MyLocation.getInstance().longitude == 0 && MyLocation.getInstance().latitude == 0) {
//            if (!LocationUtil.isGPSOPen(this)) {
//                goIntentDialog(getResources().getString(R.string.no_open_gps), new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Constant.startOpenGps = true;
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(intent, GPS_REQUEST_CODE);
//                    }
//                });
//                return;
//            }
//        }
        if (!phoneStatePermision && TextUtils.isEmpty(DeviceUtil.getUniqueID())) {
            readPhoneState();
            return;
        }
        if (phoneNum.length() == 10 || phoneNum.length() == 11 || phoneNum.length() == 12) {
            //todo 登录接口  是否Md 5
            loginPrensenter.login(areaCode, MD5Util.md5(passwd), phoneNum);

        } else {
            ToastUtil.showToast(getString(R.string.phone_error));
        }
    }


    @Override
    public void onBackPressed() {
        msbackClick.append(System.currentTimeMillis()).append(",");
        super.onBackPressed();
    }

    public void onClickForgetPw(View view) {
        //todo 忘记登录密码
        String phoneNum = meditPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showToast(getString(R.string.input_phone));
            return;
        }
        if (phoneNum.length() == 12 || phoneNum.length() == 11 || phoneNum.length() == 10) {
            //todo 登录接口  是否Md 5
//            Intent intent = new Intent(LoginActivity.this, SetLoginPasswdActivity.class);
//            intent.putExtra("phoneNum", phoneNum);
//            intent.putExtra("area", Constant.area);
//            intent.putExtra("type", Constant.VETIFYCODE_TYPE_RESET_LOGIN);
//            startActivity(intent);
            Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
            intent.putExtra("urlPath", Constant.WebPath.FORGETPASS + phoneNum);
            startActivity(intent);

        } else {
            ToastUtil.showToast(getString(R.string.phone_error));
        }
    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        loginPrensenter = new LoginPrensenter();
        locationPresener = new LocationPresener();
        userBahaviorPresenter = new UserBahaviorPresenter();
        eventRecordPresenter = new EventRecordPresenter();
        BasePresenter[] presenters = {loginPrensenter, locationPresener, userBahaviorPresenter, eventRecordPresenter};
        return presenters;
    }

    @Override
    public void loginSucess() {
        userBahaviorPresenter.loginClick(msbPhoneClick, msbPasswdClick, msbLoginClick, msbackClick);
        eventRecordPresenter.loginEven();
        if (LoginContext.getLoginContext().isLogin()) {
            LoginContext.getLoginContext().getState().jump(this);
        }
    }

    private void readPhoneState() {
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE).subscribe(new CusumeObserver<Permission>() {
            @Override
            public void onNext(Permission permission) {
                super.onNext(permission);
                if (permission.granted) {
                    DeviceUtil.getUniqueID();
                    phoneStatePermision = true;
                } else {
                    if (!permission.shouldShowRequestPermissionRationale) {
                        //  ToastUtil.showToast(getString(R.string.no_location_permission));

                        goIntentDialog(getResources().getString(R.string.no_phone_permission), new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, Constant.PERMISION_REQUEST_PHONSTATE);
                            }
                        });
                    }
                    phoneStatePermision = false;
                }

            }
        });
    }

    @Override
    public void logoutSucess() {

    }

    /**
     * 获取位置信息
     */
    public void getLocation() {
        rxPermissions.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new CusumeObserver<Permission>() {
            @Override
            public void onNext(Permission permission) {
                super.onNext(permission);
                DebugLog.i("wang", "==permission==" + permission.granted);
                if (permission.granted) {
                    // regitLocationInfo(callbackContext);
                    locationPresener.onCreateGPS();
                    isLocation = true;

                } else {
                    isLocation = false;
                    DebugLog.i("wang", "==shouldpermission==" + permission.shouldShowRequestPermissionRationale);
                    if (!permission.shouldShowRequestPermissionRationale) {
                        goIntentDialog(getResources().getString(R.string.no_location_permission), new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isStartLocationPermision = true;
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, Constant.PERMISION_REQUEST_LOCATION);
                            }
                        });
                    }
                }
            }
        });
    }

    private void goIntentDialog(String goNote, final OnClickListener onClickListener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new BaseDialog.Build(LoginActivity.this).setCanceledOnTouchOutside(true).
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PERMISION_REQUEST_PHONSTATE) {

        } else if (requestCode == Constant.PERMISION_REQUEST_LOCATION) {

        } else if (requestCode == GPS_REQUEST_CODE) {

        }
    }
}
