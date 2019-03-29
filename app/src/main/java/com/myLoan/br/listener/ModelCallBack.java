package com.myLoan.br.listener;

import java.util.List;

public interface ModelCallBack<T> extends FailtureInterface, LoadDialoginterface {
    void callBackBean();
    void callBackBean(T t);

    void callBackList(List<T> list);
}
