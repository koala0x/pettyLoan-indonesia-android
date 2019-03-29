package com.myLoan.br.modle;

import com.myLoan.br.Constant;
import com.myLoan.br.http.api.User;
import com.myLoan.br.base.BaseModel;
import com.myLoan.br.bean.LoginBean;
import com.myLoan.br.bean.RegitReq;
import com.myLoan.br.bean.ResponseMy;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.http.MediaTypeUtil;
import com.myLoan.br.http.RetrofitSingleton;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.http.GsonUtil;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 有关用户信息model(用户信息更新)
 */
public class UserModel extends BaseModel {
    /**
     * @param f             文件
     * @param modelCallBack
     */
    public void postHeadImage(File f, final ModelCallBack modelCallBack) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), f);
        builder.addFormDataPart("headPortraitFile", f.getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        RetrofitSingleton.getInstance().getRetrofit(30, 30).create(User.class).postHeadImage(builder.build()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<ResponseMy>() {
            @Override
            public boolean test(ResponseMy response) throws Exception {
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return false;
            }
        }).subscribe(new CusumeObserver<ResponseMy>() {
            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                addDisposable(s);
            }

            @Override
            public void onNext(ResponseMy response) {
                super.onNext(response);
                modelCallBack.callBackBean(response.getData());
            }
        });
    }

    public void updateLoginPw(String areaCode, String phoneNumber, String verification, String password, String area, final ModelCallBack modelCallBack) {
        RegitReq regitReq = new RegitReq();
        regitReq.setAreaCode(areaCode);
        regitReq.setPhoneNumber(phoneNumber);
        regitReq.setVerificationCode(verification);
        regitReq.setPassword(password);
        regitReq.setArea(area);
        RetrofitSingleton.getInstance().getRetrofit().create(User.class).
                forgetLoginPw(MediaTypeUtil.createJsonMediaType(GsonUtil.buildGosn().toJson(regitReq))).subscribeOn(Schedulers.io()).filter(new Predicate<LoginBean>() {
            @Override
            public boolean test(LoginBean loginBean) throws Exception {
                if(loginBean.getCode()==Constant.successCode){
                    return true;
                }
                modelCallBack.failture(loginBean.getCode(),loginBean.getMessage());
                return false;
            }
        }).map(new Function<LoginBean, LoginBean.DataBean>() {
            @Override
            public LoginBean.DataBean apply(LoginBean loginBean) throws Exception {
                return loginBean.getData();
            }
        }).subscribe(new CusumeObserver<LoginBean.DataBean>(this){
            @Override
            public void onNext(LoginBean.DataBean dataBean) {
                super.onNext(dataBean);
                modelCallBack.callBackBean(dataBean);
            }
        });
    }

}
