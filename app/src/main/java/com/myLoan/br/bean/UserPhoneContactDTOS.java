package com.myLoan.br.bean;

import java.util.List;

/**
 * @author wzx
 * @version 3.0.0 2018/12/11 22:16
 * @update wzx 2018/12/11 22:16
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class UserPhoneContactDTOS {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private List<UserPhoneContactDTO> list;

    public List<UserPhoneContactDTO> getList() {
        return list;
    }

    public void setList(List<UserPhoneContactDTO> list) {
        this.list = list;
    }
}
