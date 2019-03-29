package com.myLoan.br.base;

interface PresenterInterace<T> {
    //添加view
    void attachView(T view);

    //视图是否添加
    boolean isViewAttach();

    //删除
    void detachView(T view);
}
