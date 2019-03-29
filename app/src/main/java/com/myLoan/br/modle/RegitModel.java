package com.myLoan.br.modle;

import com.myLoan.br.Constant;
import com.myLoan.br.bean.ResponseMy;
import com.myLoan.br.http.api.User;
import com.myLoan.br.base.BaseModel;
import com.myLoan.br.bean.LoginBean;
import com.myLoan.br.bean.RegitReq;
import com.myLoan.br.bean.Response;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.http.MediaTypeUtil;
import com.myLoan.br.http.RetrofitSingleton;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.http.GonsonUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RegitModel extends BaseModel {

    public void regit(String areaCode, String phoneNumber, String password, String verificationCode, String area, final ModelCallBack modelCallBack) {
        RegitReq regitReq = new RegitReq();
        regitReq.setAreaCode(areaCode);
        regitReq.setPhoneNumber(phoneNumber);
        regitReq.setPassword(password);
        regitReq.setArea(area);
        regitReq.setVerificationCode(verificationCode);
        RetrofitSingleton.getInstance().getRetrofit().create(User.class)
                .regit(MediaTypeUtil.createJsonMediaType(GonsonUtil.buildGosn().toJson(regitReq)))
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<LoginBean>() {
                    @Override
                    public boolean test(LoginBean loginBean) throws Exception {
                        if (loginBean.getCode() == Constant.successCode) {
                            return true;
                        }
                        modelCallBack.failture(loginBean.getCode(), loginBean.getMessage());
                        return false;
                    }
                })
                .map(new Function<LoginBean, LoginBean.DataBean>() {
                    @Override
                    public LoginBean.DataBean apply(LoginBean loginBean) throws Exception {
                        return loginBean.getData();
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new CusumeObserver<LoginBean.DataBean>(this) {
                    @Override
                    public void onNext(LoginBean.DataBean dataBean) {
                        super.onNext(dataBean);
                        modelCallBack.callBackBean(dataBean);
                    }

                    @Override
                    protected void onStart(Disposable s) {
                        super.onStart(s);
                        addDisposable(s);
                    }
                });
    }

    public void saveInvateCode(String invitationCode, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit()
                .create(User.class)
                .inviteCode(invitationCode)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Response>() {
                    @Override
                    public boolean test(Response response) throws Exception {
                        if (response.getCode() == Constant.successCode) {
                            return true;
                        }
                        modelCallBack.failture(response.getCode(), response.getMessage());
                        return false;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CusumeObserver<Response>(modelCallBack) {
                    @Override
                    protected void onStart(Disposable s) {
                        super.onStart(s);
                        addDisposable(s);
                    }

                    @Override
                    public void onNext(Response response) {
                        super.onNext(response);
                        modelCallBack.callBackBean();
                    }
                });
    }

    public void codeCheck(String areaCode, String phoneNumber, String verificationCode, int verificationType, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(User.class).codeCheck(areaCode, phoneNumber, verificationCode, verificationType).subscribeOn(Schedulers.io()).filter(new Predicate<ResponseMy>() {
            @Override
            public boolean test(ResponseMy response) throws Exception {
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return false;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<ResponseMy>(modelCallBack) {
            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                addDisposable(s);
            }

            @Override
            public void onNext(ResponseMy response) {
                super.onNext(response);
                modelCallBack.callBackBean();
            }
        });
    }

}
