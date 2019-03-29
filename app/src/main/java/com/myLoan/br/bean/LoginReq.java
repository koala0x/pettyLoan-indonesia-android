package com.myLoan.br.bean;

public class LoginReq {

    /**
     * area : string
     * areaCode : string
     * password : string
     * phoneNumber : string
     */

    private String area;
    private String areaCode;
    private String password;
    private String phoneNumber;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
