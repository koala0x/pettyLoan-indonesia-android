package com.myLoan.br.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25 0025.
 */

public class UserPhoneContactDTOH5 {
    private String contactName;
    private List<String> contactPhones;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public List<String> getContactPhones() {
        return contactPhones;
    }

    public void setContactPhones(List<String> contactPhones) {
        this.contactPhones = contactPhones;
    }
}
