package com.myLoan.br.modle;

import com.myLoan.br.Constant;
import com.myLoan.br.http.api.User;
import com.myLoan.br.base.BaseModel;
import com.myLoan.br.bean.Response;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.http.MediaTypeUtil;
import com.myLoan.br.http.RetrofitSingleton;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.http.GsonUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class UserBehaviorModle<T> extends BaseModel {
    /**
     * 页面点击请求
     */
    public void clickTime(String path, T t, final ModelCallBack modelCallBack) {
        DebugLog.i("wang","===t=="+GsonUtil.buildGosn().toJson(t));
        RetrofitSingleton.getInstance().getRetrofit().create(User.class).clickTime(path, MediaTypeUtil.createJsonMediaType(GsonUtil.buildGosn().toJson(t))).subscribeOn(Schedulers.io()).filter(new Predicate<Response>() {
            @Override
            public boolean test(Response response) throws Exception {
                if(response.getCode()==Constant.successCode){
                    return true;
                }
                modelCallBack.failture(response.getCode(),response.getMessage());
                return false;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<Response>(){
            @Override
            public void onNext(Response response) {
                super.onNext(response);
            }
        });
    }

}
