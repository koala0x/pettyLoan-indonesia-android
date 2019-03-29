package com.myLoan.br.presenter;

import android.annotation.SuppressLint;
import android.app.Dialog;

import com.myLoan.br.Constant;
import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.bean.LoginBean;
import com.myLoan.br.bean.MsgEvent;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.listener.contract.RegiterContract;
import com.myLoan.br.tools.loginstate.LoginContext;
import com.myLoan.br.tools.loginstate.LoginState;
import com.myLoan.br.modle.RegitModel;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.tools.RxBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册控制器
 */
public class RegitPresenter extends BaseActionPresenter implements RegiterContract.IRegi {
    private RegitModel regitModel;
    Disposable disposable;

    @Override
    public void initModle() {
        regitModel = new RegitModel();
        regitModel(regitModel);
    }


    @Override
    public void regit(String areaCode, String phoneNum, String passwd, String verityCode, String area) {
        regitModel.regit(areaCode, phoneNum, passwd, verityCode, area, new ModelCallBack<LoginBean.DataBean>() {
            @Override
            public void showDialoging() {
                showLoadDialogAction();
            }

            @Override
            public void hiddenDialoging() {
                disLoadDilogAction();
            }

            @Override
            public void failture(int code, String message) {
                showToastAction(code, message);
            }

            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(LoginBean.DataBean dataBean) {
                LoginContext.getLoginContext().setState(new LoginState());
                LoginContext.getLoginContext().setUser(dataBean);
                MsgEvent msgEvent = new MsgEvent(Constant.RxBus.REQUEST_LOGIN, Constant.Map.REGITED_VALUE, "");
                RxBus.getDefault().post(msgEvent);
                if (isViewAttach()) {
                    ((RegiterContract.IRegirView) getAttach()).regitSucess();
                }
            }

            @Override
            public void callBackList(List<LoginBean.DataBean> list) {

            }
        });
    }

    @Override
    public void saveInvateCode(String invateCode, final Dialog dialog) {
        regitModel.saveInvateCode(invateCode, new ModelCallBack() {
            @Override
            public void callBackBean() {
                //dialog
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void callBackBean(Object o) {

            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                showToastAction(code, message);
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void downTimer(final int second) {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS).take(second + 1).map(new Function<Long, Long>() {
            @Override
            public Long apply(Long aLong) throws Exception {
                return second - aLong;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {

            @Override
            public void accept(Long aLong) throws Exception {
                DebugLog.i("===timer==" + aLong);
                if (isViewAttach()) {
                    ((RegiterContract.IRegirView) getAttach()).downTimker(aLong);
                }
                if (aLong == 0) {
                    if (isViewAttach()) {
                        ((RegiterContract.IRegirView) getAttach()).finshDownTinker();
                    }
                }

            }
        });
    }

    @Override
    public void codeCheck(String areaCode, String phoneNumber, String verificationCode, int verificationType) {
        regitModel.codeCheck(areaCode, phoneNumber, verificationCode, verificationType, new ModelCallBack() {
            @Override
            public void callBackBean() {
                if (isViewAttach()) {
                    ((RegiterContract.IRegirView) getAttach()).codeCheckSuccess();
                }
            }

            @Override
            public void callBackBean(Object o) {

            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                showToastAction(code, message);
            }

            @Override
            public void showDialoging() {
                showLoadDialogAction();
            }

            @Override
            public void hiddenDialoging() {
                disLoadDilogAction();
            }
        });
    }

    @Override
    public void detachView(Object view) {
        super.detachView(view);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
