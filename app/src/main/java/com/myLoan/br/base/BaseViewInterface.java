package com.myLoan.br.base;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public interface BaseViewInterface{
    //展示对话框
    void showProgessDialog();

    //隐藏对话框
    void hiddenProgessDialog();

    //失败
    void showFailture(int code, String message);

}
