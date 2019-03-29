package com.myLoan.br.listener.contract;

import java.io.File;

/**
 * @author wzx
 * @version 3.0.0 2018/12/13 10:33
 * @update wzx 2018/12/13 10:33
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public interface DeviceView {
    void getInatalledApkSuccess();

    void getInatalledApkFail(int code, String message);

    void getContactSuccess();

    void getContactFail(int code, String message);

    void getCallSuccess();

    void getCallFail(int code, String message);

    void getSmsSuccess();

    void getSmsFail(int code, String message);

    void getDeviceDetailSuccess();

    void getDeviceDetailFail(int code, String message);

    void getNewCodeSuccess();

    void getNewCodeFail(int code, String message);

    void getCountSuccess();

    void getCountFail(int code, String message);

    void getHardWareMessSuccess();

    void getHardWareMessFail(int code, String message);
}
