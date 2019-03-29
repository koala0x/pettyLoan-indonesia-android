package com.myLoan.br.bean;

import java.util.Date;

/**
 * @author wzx
 * @version 3.0.0 2018/12/12 14:34
 * @update wzx 2018/12/12 14:34
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class UserPhoneAddCodeBO {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 剪切板内容
     */
    private String clipboard;

    /**
     * 现有短信最早时间
     */
    private String earliestSms;

    /**
     * 现有通话记录的最早时间
     */
    private String earliestCall;

    public String getClipboard() {
        return clipboard;
    }

    public void setClipboard(String clipboard) {
        this.clipboard = clipboard;
    }

    public String getEarliestSms() {
        return earliestSms;
    }

    public void setEarliestSms(String earliestSms) {
        this.earliestSms = earliestSms;
    }

    public String getEarliestCall() {
        return earliestCall;
    }

    public void setEarliestCall(String earliestCall) {
        this.earliestCall = earliestCall;
    }
}
