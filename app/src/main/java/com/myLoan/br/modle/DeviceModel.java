package com.myLoan.br.modle;

import android.util.Log;

import com.myLoan.br.Constant;
import com.myLoan.br.http.api.DeviceMessApi;
import com.myLoan.br.base.BaseModel;
import com.myLoan.br.bean.Response;
import com.myLoan.br.bean.ResponseMy;
import com.myLoan.br.bean.UserDeviceDetailVO;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.http.MediaTypeUtil;
import com.myLoan.br.http.RetrofitSingleton;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.http.GonsonUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wzx
 * @version 3.0.0 2018/12/10 17:16
 * @update wzx 2018/12/10 17:16
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class DeviceModel extends BaseModel {
    private static final String TAG = "wang";

    //保存手机通讯录信息
    public void getInstalledApk(String encodeJson, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(DeviceMessApi.class).getInstalledApp(MediaTypeUtil.createJsonMediaType(encodeJson)).subscribeOn(Schedulers.io()).filter(new Predicate<Response>() {
            @Override
            public boolean test(Response response) throws Exception {
                Log.i(TAG, "test: getInstalledApk" + response);
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
                Log.i(TAG, "onNext: getInstalledApk" + response.getCode());
                modelCallBack.callBackBean(response);
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                Log.i(TAG, "onStart: getInstalledApk");
                addDisposable(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "onError: getInstalledApk");
                modelCallBack.failture(1, e.getMessage());
            }
        });
    }


    //保存手机硬件信息
    public void hardwareMess(String encodeJson, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(DeviceMessApi.class).hardwareMess(MediaTypeUtil.createJsonMediaType(encodeJson)).subscribeOn(Schedulers.io()).filter(new Predicate<ResponseMy>() {
            @Override
            public boolean test(ResponseMy response) throws Exception {
                Log.i(TAG, "hardwareMess test: " + response);
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return false;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<ResponseMy>() {
            @Override
            public void onNext(ResponseMy response) {
                super.onNext(response);
                Log.i(TAG, "hardwareMess onNext: " + response);
                modelCallBack.callBackBean(response);
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                Log.i(TAG, "hardwareMess onStart: ");
                addDisposable(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "hardwareMess onError: ");
                modelCallBack.failture(1, e.getMessage());
            }
        });
    }

    public void getContact(String encodeJson, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(DeviceMessApi.class).getContact(MediaTypeUtil.createJsonMediaType(encodeJson)).subscribeOn(Schedulers.io()).filter(new Predicate<ResponseMy>() {
            @Override
            public boolean test(ResponseMy response) throws Exception {
                Log.i(TAG, "getContact test: " + response);
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return true;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<ResponseMy>() {
            @Override
            public void onNext(ResponseMy response) {
                super.onNext(response);
                Log.i(TAG, "getContact onNext: " + response);
                modelCallBack.callBackBean(response);
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                Log.i(TAG, "getContact onStart: ");
                addDisposable(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "getContact onError: ");
                modelCallBack.failture(1, e.getMessage());
            }
        });
    }

    public void getSms(String encodeJson, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(DeviceMessApi.class).getSms(MediaTypeUtil.createJsonMediaType(encodeJson)).subscribeOn(Schedulers.io()).filter(new Predicate<ResponseMy>() {
            @Override
            public boolean test(ResponseMy response) throws Exception {
                Log.i(TAG, "getSms test: " + response);
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return true;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<ResponseMy>() {
            @Override
            public void onNext(ResponseMy response) {
                super.onNext(response);
                Log.i(TAG, "getSms onNext: " + response);
                modelCallBack.callBackBean(response);
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                Log.i(TAG, "getSms onStart: ");
                addDisposable(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "getSms onError: ");
                modelCallBack.failture(1, e.getMessage());
            }
        });
    }

    public void getCall(String encodeJson, final ModelCallBack modelCallBack) {
        RetrofitSingleton.getInstance().getRetrofit().create(DeviceMessApi.class).getCall(MediaTypeUtil.createJsonMediaType(encodeJson)).subscribeOn(Schedulers.io()).filter(new Predicate<ResponseMy>() {
            @Override
            public boolean test(ResponseMy response) throws Exception {
                Log.i(TAG, "getCall test: " + response);
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return true;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<ResponseMy>() {
            @Override
            public void onNext(ResponseMy response) {
                super.onNext(response);
                Log.i(TAG, "getCall onNext: " + response);
                modelCallBack.callBackBean(response);
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                Log.i(TAG, "getCall onStart: ");
                addDisposable(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "getCall onError: ");
                modelCallBack.failture(1, e.getMessage());
            }
        });
    }

    public void getDeviceDetail(UserDeviceDetailVO encodeJson, final ModelCallBack modelCallBack) {
//        Log.i(TAG, "getDeviceDetail " + MediaTypeUtil.createJsonMediaType(encodeJson));
        RetrofitSingleton.getInstance()
                .getRetrofit()
                .create(DeviceMessApi.class)
                .getDeviceDetail(MediaTypeUtil.createJsonMediaType(GonsonUtil.buildGosn().toJson(encodeJson)))
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<ResponseMy>() {
                    @Override
                    public boolean test(ResponseMy response) throws Exception {
                        Log.i(TAG, "getDeviceDetail test: " + response);
                        if (response.getCode() == Constant.successCode) {
                            return true;
                        }
                        modelCallBack.failture(response.getCode(), response.getMessage());
                        return true;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<ResponseMy>() {
            @Override
            public void onNext(ResponseMy response) {
                super.onNext(response);
                Log.i(TAG, "getDeviceDetail onNext: " + response);
                modelCallBack.callBackBean(response);
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                Log.i(TAG, "getDeviceDetail onStart: ");
                addDisposable(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "getDeviceDetail onError: ");
                modelCallBack.failture(1, e.getMessage());
            }
        });
    }

    public void getNewCode(String encodeJson, final ModelCallBack modelCallBack) {
//        Log.i(TAG, "getNewCode " + MediaTypeUtil.createJsonMediaType(encodeJson));
        RetrofitSingleton.getInstance().getRetrofit().create(DeviceMessApi.class).getNewCode(MediaTypeUtil.createJsonMediaType(encodeJson)).subscribeOn(Schedulers.io()).filter(new Predicate<ResponseMy>() {
            @Override
            public boolean test(ResponseMy response) throws Exception {
                Log.i(TAG, "getNewCode test: " + response);
                if (response.getCode() == Constant.successCode) {
                    return true;
                }
                modelCallBack.failture(response.getCode(), response.getMessage());
                return true;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<ResponseMy>() {
            @Override
            public void onNext(ResponseMy response) {
                super.onNext(response);
                Log.i(TAG, "getNewCode onNext: " + response);
                modelCallBack.callBackBean(response);
            }

            @Override
            protected void onStart(Disposable s) {
                super.onStart(s);
                Log.i(TAG, "getNewCode onStart: ");
                addDisposable(s);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "getNewCode onError: ");
                modelCallBack.failture(1, e.getMessage());
            }
        });
    }


    public void getMessAndCallCount(String messageCount, String callRecordCount, final ModelCallBack modelCallBack) {
//        Log.i(TAG, "getNewCode " + MediaTypeUtil.createJsonMediaType(encodeJson));
        RetrofitSingleton
                .getInstance()
                .getRetrofit()
                .create(DeviceMessApi.class)
                .getMessAndCallCount(messageCount, callRecordCount)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<ResponseMy>() {
                    @Override
                    public boolean test(ResponseMy response) throws Exception {
                        Log.i(TAG, "getNewCode test: " + response);
                        if (response.getCode() == Constant.successCode) {
                            return true;
                        }
                        modelCallBack.failture(response.getCode(), response.getMessage());
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CusumeObserver<ResponseMy>() {
                    @Override
                    public void onNext(ResponseMy response) {
                        super.onNext(response);
                        Log.i(TAG, "getNewCode onNext: " + response);
                        modelCallBack.callBackBean(response);
                    }

                    @Override
                    protected void onStart(Disposable s) {
                        super.onStart(s);
                        Log.i(TAG, "getNewCode onStart: ");
                        addDisposable(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i(TAG, "getNewCode onError: ");
                        modelCallBack.failture(1, e.getMessage());
                    }
                });
    }
}
