package com.myLoan.br.bean;

import java.util.List;

/**
 * @author wzx
 * @version 3.0.0 2018/12/13 9:40
 * @update wzx 2018/12/13 9:40
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class UserPhoneContactMessH5 {
    private List<UserPhoneContactDTOH5> userPhoneContactDTOH5s;
    private int code;

    public List<UserPhoneContactDTOH5> getUserPhoneContactDTOH5s() {
        return userPhoneContactDTOH5s;
    }

    public void setUserPhoneContactDTOH5s(List<UserPhoneContactDTOH5> userPhoneContactDTOH5s) {
        this.userPhoneContactDTOH5s = userPhoneContactDTOH5s;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
