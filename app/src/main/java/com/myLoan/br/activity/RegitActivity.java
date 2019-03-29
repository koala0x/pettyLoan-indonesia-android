package com.myLoan.br.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.myLoan.br.presenter.RegitPresenter;
import com.myLoan.br.tools.map.MyLocation;
import com.myLoan.br.presenter.CheckPresenter;
import com.myLoan.br.presenter.EventRecordPresenter;
import com.myLoan.br.presenter.LocationPresener;
import com.myLoan.br.presenter.UserBahaviorPresenter;
import com.myLoan.br.tools.phonestatus.ContactUtil;
import com.myLoan.br.tools.view.BaseDialog;
import com.myLoan.br.tools.phonestatus.DeviceUtil;
import com.myLoan.br.tools.phonestatus.LocationUtil;
import com.myLoan.br.tools.view.StringUtil;
import com.myLoan.br.tools.view.ToastUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class RegitActivity extends BaseActivity implements CheckContract.IcheckPhoneView, RegiterContract.IRegirView, CheckContract.IVetifyCodeView {
    RegitPresenter regitPresenter;
    CheckPresenter checkPresenter;
    UserBahaviorPresenter userBahaviorPresenter;
    EventRecordPresenter eventRecordPresenter;
    LocationPresener locationPresener;
    EditText meditPhone;
    EditText meditCpf;
    CheckBox mcbProtrol;
    Button mbtnRegit;
    TextView mtvGoLogin;
    TextView mtvRegitProtrol;
    TextView mtvRegitProtrol2;
    private EditText mCodeVerify;

    private TextView mTextViewVerifyCode;
    private String phoneNum;
    private TextView TextViewArea;
    //    private String email;
//    private String cpf;
    private String area = Constant.area;
    private StringBuilder msbCheckClick = new StringBuilder();
    private StringBuilder msbPhoneClick = new StringBuilder();
    private StringBuilder msbEmailClick = new StringBuilder();
    private StringBuilder msbCpfClick = new StringBuilder();
    private StringBuilder msbRegitClick = new StringBuilder();
    private static StringBuilder msbTopClick = new StringBuilder();
    private static StringBuilder msbBottomClick = new StringBuilder();
    private RxPermissions rxPermissions;
    Dialog dialog;
    private int GPS_REQUEST_CODE = 10;
    private boolean isFirstOpenGps = true;

    private int type = 1;

    boolean isAdd = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_regiter;
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
        mcbProtrol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    msbCheckClick.append(System.currentTimeMillis()).append(",");
                }
            }
        });

        mtvRegitProtrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入协议
            }
        });
        findViewById(R.id.fl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClickListener(v);
            }
        });


        mTextViewVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum = meditPhone.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtil.showToast(getString(R.string.input_phone));
                } else if (phoneNum.length() == 12 || phoneNum.length() == 11 || phoneNum.length() == 10) {
                    onClickSendVetifyCode(v);
                } else {
                    ToastUtil.showToast(getString(R.string.phone_error));
                }
            }
        });


        meditPhone.addTextChangedListener(new TextWatcher() {
            int bhlong;
            int beforlong;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforlong = s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bhlong = s.toString().length();
                phoneNum = meditPhone.getText().toString().trim();
                if (beforlong > bhlong && TextUtils.isEmpty(s)) {//判断是否是清除状态
//                    TextViewArea.setText("+62");
                }

                if (!TextUtils.isEmpty(phoneNum)) {
                    if (beforlong > bhlong) {//判断是否是清除状态
//                        TextViewArea.setText("+62");
                    } else {
                        if (!phoneNum.startsWith("0")) {

                            phoneNum = "0" + s;
//                            TextViewArea.setText("+620");
                            meditPhone.setText(phoneNum);
                            meditPhone.setSelection(phoneNum.length());
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initView() {
        meditPhone = findViewById(R.id.edit_phone);
        mcbProtrol = findViewById(R.id.cb_protrol);
        mbtnRegit = findViewById(R.id.btn_regit);
        mtvGoLogin = findViewById(R.id.tv_go_login);
        mtvRegitProtrol = findViewById(R.id.tv_regit_protrol);
        mtvRegitProtrol2 = findViewById(R.id.tv_regit_protrol2);
        mTextViewVerifyCode = findViewById(R.id.tv_verify_code);
        mCodeVerify = findViewById(R.id.edit_code);
        TextViewArea = findViewById(R.id.tv_area);
    }

    @Override
    public void intData() {
        setTitle(getResources().getString(R.string.regit));
        rxPermissions = new RxPermissions(this);
        SpannableString goRegitSp = new SpannableString(getResources().getString(R.string.go_login));
        mtvGoLogin.setHighlightColor(getResources().getColor(android.R.color.transparent));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(RegitActivity.this, LoginActivity.class);
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
        if (language.equals("in-ID")) {
            goRegitSp.setSpan(clickableSpan, 15, goRegitSp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        } else if (language.equals("pt-BR")) {
            goRegitSp.setSpan(clickableSpan, 13, goRegitSp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        } else if (language.equals("zh-CN")) {
            goRegitSp.setSpan(clickableSpan, 5, goRegitSp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        mtvGoLogin.setText(goRegitSp);
        mtvGoLogin.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString proctrolSp = new SpannableString(getResources().getString(R.string.regit_proctol));
        SpannableString proctrolSpEnd = new SpannableString(getResources().getString(R.string.regit_proctol_end, getResources().getString(R.string.app_name)));
        mtvRegitProtrol.setHighlightColor(getResources().getColor(android.R.color.transparent));
        ClickableSpan clickableSpanProtrol1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(RegitActivity.this, WebViewActivity.class);
                intent.putExtra("urlPath", Constant.WebPath.REGIT_PROTROL1);
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
        proctrolSp.setSpan(clickableSpanProtrol1, 16, 29, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        ClickableSpan clickableSpanProtrol2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(RegitActivity.this, WebViewActivity.class);
                intent.putExtra("urlPath", Constant.WebPath.REGIT_PROTROL2);
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
        proctrolSpEnd.setSpan(clickableSpanProtrol2, 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mtvRegitProtrol.setText(proctrolSp);
        mtvRegitProtrol2.setText(proctrolSpEnd);
        mtvRegitProtrol.setMovementMethod(LinkMovementMethod.getInstance());
        mtvRegitProtrol2.setMovementMethod(LinkMovementMethod.getInstance());
        getLocation();
        readPhoneState();
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

    /**
     * @param view
     */
    public void onClickRegit(View view) {

        msbRegitClick.append(System.currentTimeMillis()).append(",");
        //电子邮件
//        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (mcbProtrol.isChecked()) {
            phoneNum = meditPhone.getText().toString().trim();
            if (TextUtils.isEmpty(phoneNum)) {
                ToastUtil.showToast(getString(R.string.input_phone));
                return;
            }

            //
            if (TextUtils.isEmpty(mCodeVerify.getText().toString().trim())) {
                ToastUtil.showToast("输入验证码");
                return;
            }

            //MyLocation.getInstance().longitude == 0 && MyLocation.getInstance().latitude == 0 &&
            if (isFirstOpenGps) {
                if (!LocationUtil.isGPSOPen(this)) {
                    isFirstOpenGps = false;
                    goIntentDialog(getResources().getString(R.string.no_open_gps), new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Constant.startOpenGps = true;
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, GPS_REQUEST_CODE);
                        }
                    });
                    return;
                }
            }
            if (phoneNum.length() == 12 || phoneNum.length() == 11 || phoneNum.length() == 10) {
                checkPresenter.checkIsRegit(area, phoneNum, "email", "cpf");
            } else {
                ToastUtil.showToast(getString(R.string.phone_error));
            }
        } else {
            ToastUtil.showToast(getString(R.string.agree_regit));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constant.startOpenGps) {
            Constant.startOpenGps = false;
            getLocation();
        }
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    public void onClickHinddenKeyword(View view) {
        hideSystemSofeKeyboard(this, meditCpf);
    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        regitPresenter = new RegitPresenter();
        checkPresenter = new CheckPresenter();
        userBahaviorPresenter = new UserBahaviorPresenter();
        eventRecordPresenter = new EventRecordPresenter();
        locationPresener = new LocationPresener();
        BasePresenter[] presenters = {regitPresenter, checkPresenter, userBahaviorPresenter, eventRecordPresenter, locationPresener};
        return presenters;
    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public void hideSystemSofeKeyboard(Context context, EditText editText) {
     /*   int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }*/
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public void regited() {
        eventRecordPresenter.regitEven();
        userBahaviorPresenter.regitClick(msbCheckClick, msbEmailClick, msbPhoneClick, msbRegitClick, msbTopClick, msbBottomClick);
        //=====================================String areaCode, String phoneNumber,
        regitPresenter.codeCheck(area, phoneNum, mCodeVerify.getText().toString().trim(), Constant.VETIFYCODE_TYPE_REGIT);

    }

    private void goIntentDialog(String goNote, final OnClickListener onClickListener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new BaseDialog.Build(RegitActivity.this).setCanceledOnTouchOutside(true).
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

    /**
     * 获取位置信息
     */
    public void getLocation() {
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new CusumeObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    // regitLocationInfo(callbackContext);
                    // isLocation=true;
                    locationPresener.onCreateGPS();
                } else {
//                    /isLocation=false;
                }
            }
        });
    }

    private void readPhoneState() {
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE).subscribe(new CusumeObserver<Permission>() {
            @Override
            public void onNext(Permission permission) {
                super.onNext(permission);
                if (permission.granted) {
                    DeviceUtil.getUniqueID();
                } else {
                    if (!permission.shouldShowRequestPermissionRationale) {
                        ToastUtil.showToast(getString(R.string.no_location_permission));

                    }
                }

            }
        });
    }

    /**
     * 发送短信
     *
     * @param view
     */
    public void onClickSendVetifyCode(View view) {
        regitPresenter.downTimer(60);
        checkPresenter.sendVetifyCode(area, phoneNum, type);
//        rxPermissions.requestEach(Manifest.permission.READ_SMS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CusumeObserver<Permission>() {
//                    @Override
//                    public void onNext(Permission permission) {
//                        super.onNext(permission);
//                        if (permission.granted) {
//                            regitPresenter.downTimer(60);
//                            checkPresenter.sendVetifyCode(area, phoneNum, type);
//                        } else {
//                            if (!permission.shouldShowRequestPermissionRationale) {
//                                goIntentDialog(getString(R.string.no_sms_permission), new OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                        intent.setData(uri);
//                                        startActivityForResult(intent, Constant.PERMISION_REQUEST_PHONSTATE);
//                                    }
//                                });
//                            } else {
//                                ToastUtil.showToast(getString(R.string.sms_send_failture));
//                            }
//                        }
//                    }
//                });
    }

    @Override
    public void regitSucess() {

    }

    @Override
    public void downTimker(long second) {

    }

    @Override
    public void finshDownTinker() {

    }

    @Override
    public void codeCheckSuccess() {
        ///////////

        Intent intent = new Intent(RegitActivity.this, SetLoginPasswdActivity.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("verificationCode", mCodeVerify.getText().toString().trim());
        intent.putExtra("area", Constant.area);
        intent.putExtra("type", Constant.VETIFYCODE_TYPE_REGIT);
        startActivity(intent);
    }

    @Override
    public void vetifySucee() {

    }
}
