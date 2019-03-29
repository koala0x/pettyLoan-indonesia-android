package com.myLoan.br.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25 0025.
 */

public class UserPhoneContactDTO {

    /**紧急联系人
     * contactGrade : 0
     * contactName : string 姓名
     * contactPhone : string 电话
     * contactEmail : string 邮箱
     */

    private int contactGrade;
    private String contactName;
//    private String contactPhone;
//    private String contactEmail;
    private String id;
    private List<String> contactPhones;
    private List<String> contactEmails;
    private List<String> contactAdresses;

    /**
     * 该联系人电话是否被删除
     */
    private Boolean deleteStatus;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getContactGrade() {
        return contactGrade;
    }

    public void setContactGrade(int contactGrade) {
        this.contactGrade = contactGrade;
    }

    @Override
    public String toString() {
        return "UserPhoneContactDTO{" +
                "contactGrade=" + contactGrade +
                ", id='" + id + '\'' +
                ", contactPhones=" + contactPhones +
                ", contactEmails=" + contactEmails +
                ", contactAdresses=" + contactAdresses +
                '}';
    }

    public List<String> getContactPhones() {
        return contactPhones;
    }

    public void setContactPhones(List<String> contactPhones) {
        this.contactPhones = contactPhones;
    }

    public List<String> getContactEmails() {
        return contactEmails;
    }

    public void setContactEmails(List<String> contactEmails) {
        this.contactEmails = contactEmails;
    }

    public List<String> getContactAdresses() {
        return contactAdresses;
    }

    public void setContactAdresses(List<String> contactAdresses) {
        this.contactAdresses = contactAdresses;
    }
}
