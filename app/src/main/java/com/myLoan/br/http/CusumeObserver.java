package com.myLoan.br.http;


import com.myLoan.br.base.BaseViewInterface;
import com.myLoan.br.bean.ResponeThrowable;
import com.myLoan.br.listener.LoadDialoginterface;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.tools.view.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

/**
 * Created by Administrator on 2018/7/21 0021.
 */

public class CusumeObserver<T> implements Observer<T> {
    // ProgressDialog progressDialog;
    private BaseViewInterface baseViewInterface;
    private LoadDialoginterface progessBaseModel;

    public CusumeObserver() {

    }

    public CusumeObserver(Object window) {
        if (window != null) {
            if (window instanceof BaseViewInterface) {
                baseViewInterface = (BaseViewInterface) window;
            }
            if (window instanceof LoadDialoginterface) {
                progessBaseModel = (LoadDialoginterface) window;
            }
        }
    }

    private Disposable s;

    @Override
    public final void onSubscribe(@NonNull Disposable s) {
        if (EndConsumerHelper.validate(this.s, s, getClass())) {
            this.s = s;
            onStart(s);
        }
    }

    /**
     * Cancels the upstream's disposable.
     */
    protected final void cancel() {
        Disposable s = this.s;
        this.s = DisposableHelper.DISPOSED;
        s.dispose();
    }


    protected void onStart(Disposable s) {
        if (baseViewInterface != null) {
            baseViewInterface.showProgessDialog();
        }
        if (progessBaseModel != null) {
            progessBaseModel.showDialoging();
        }

    }


    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        String message = "";
        if (baseViewInterface != null) {
            baseViewInterface.hiddenProgessDialog();
        }
        if (progessBaseModel != null) {
            progessBaseModel.hiddenDialoging();
        }

        final ResponeThrowable throwable = ExceptionHandle.handleException(e);
        if (throwable.isShow) {

            ToastUtil.showToast(throwable.message);
            //Toast.makeText(MyApplication.mContext,throwable.message,Toast.LENGTH_SHORT).show();

        }
        DebugLog.i("wang", "=====" + e);
    }

    @Override
    public void onComplete() {
        if (baseViewInterface != null) {
            baseViewInterface.hiddenProgessDialog();
        }
        if (progessBaseModel != null) {
            progessBaseModel.hiddenDialoging();
        }
    }
}
