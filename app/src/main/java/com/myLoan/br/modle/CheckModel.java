package com.myLoan.br.modle;

import com.myLoan.br.Constant;
import com.myLoan.br.http.api.User;
import com.myLoan.br.base.BaseModel;
import com.myLoan.br.bean.Response;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.http.RetrofitSingleton;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.tools.DebugLog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class CheckModel extends BaseModel {
    /**
     * 检查手机是否注册
     */
    public void checkPhone(String area, String phoneNum, String email, String cpf, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(User.class).checkPhoneRegit(area, phoneNum, email, cpf).subscribeOn(Schedulers.io()).filter(new Predicate<Response>() {
            @Override
            public boolean test(Response response) throws Exception {
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return false;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<Response>(modelCallBack) {
            @Override
            public void onNext(Response response) {
                super.onNext(response);
                modelCallBack.callBackBean();
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                addDisposable(s);
            }
        });
    }

    /**
     *  发送短信
     */
    public void sendVetifyCode(String areaCode, String phone, int type, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(User.class).getVerifyCode(areaCode, phone, type).subscribeOn(Schedulers.io()).filter(new Predicate<Response>() {
            @Override
            public boolean test(Response response) throws Exception {
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return false;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<Response>(modelCallBack) {
            @Override
            public void onNext(Response response) {
                super.onNext(response);
                modelCallBack.callBackBean();
                DebugLog.i("短信发送成功");
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                addDisposable(s);
            }
        });
    }
}
