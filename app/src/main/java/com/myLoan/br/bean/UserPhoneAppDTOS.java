package com.myLoan.br.bean;

import java.util.List;

/**
 * @author wzx
 * @version 3.0.0 2018/12/11 10:38
 * @update wzx 2018/12/11 10:38
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class UserPhoneAppDTOS {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private List<PackInfoBean> list;

    public List<PackInfoBean> getList() {
        return list;
    }

    public void setList(List<PackInfoBean> list) {
        this.list = list;
    }
}
