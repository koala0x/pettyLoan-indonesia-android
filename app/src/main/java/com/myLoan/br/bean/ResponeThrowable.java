package com.myLoan.br.bean;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public class ResponeThrowable extends Exception {
    private Throwable throwable;
    public int code;
    public String message;
    public boolean isShow=true;

    public ResponeThrowable(Throwable e, int code) {
        super(e);
        this.code = code;
    }

}
