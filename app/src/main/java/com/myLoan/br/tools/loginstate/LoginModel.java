package com.myLoan.br.tools.loginstate;

import com.myLoan.br.Constant;
import com.myLoan.br.http.api.User;
import com.myLoan.br.base.BaseModel;
import com.myLoan.br.bean.LoginBean;
import com.myLoan.br.bean.LoginReq;
import com.myLoan.br.bean.Response;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.http.MediaTypeUtil;
import com.myLoan.br.http.RetrofitSingleton;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.http.GsonUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class LoginModel extends BaseModel {
    public void login(String areaCode, String passwd, String phoneNum, final ModelCallBack modelCallBack) {
        LoginReq loginReq = new LoginReq();
        loginReq.setAreaCode(areaCode);
        loginReq.setPassword(passwd);
        loginReq.setPhoneNumber(phoneNum);
        RetrofitSingleton.getInstance().getRetrofit().create(User.class).login(MediaTypeUtil.createJsonMediaType(GsonUtil.buildGosn().toJson(loginReq))).subscribeOn(Schedulers.io()).filter(new Predicate<LoginBean>() {
            @Override
            public boolean test(LoginBean loginBean) throws Exception {
                if (loginBean.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(loginBean.getCode(), loginBean.getMessage());
                return false;
            }
        }).map(new Function<LoginBean, LoginBean.DataBean>() {
            @Override
            public LoginBean.DataBean apply(LoginBean loginBean) throws Exception {
                return loginBean.getData();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<LoginBean.DataBean>(modelCallBack) {
            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                addDisposable(s);
            }

            @Override
            public void onNext(LoginBean.DataBean dataBean) {
                super.onNext(dataBean);
                modelCallBack.callBackBean(dataBean);
            }
        });
    }

    public void logout(final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(User.class).logout().subscribeOn(Schedulers.io()).filter(new Predicate<Response>() {
            @Override
            public boolean test(Response response) throws Exception {
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return false;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<Response>() {
            @Override
            public void onNext(Response response) {
                super.onNext(response);
                modelCallBack.callBackBean();
                DebugLog.i("===logout_sucess===");
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                addDisposable(s);
            }
        });
    }
}
