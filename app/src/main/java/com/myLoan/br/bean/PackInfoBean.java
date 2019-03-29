package com.myLoan.br.bean;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class PackInfoBean {
    /**
     * 包名
     */
    private String packName;//包名
    private String isRunning;//是否正在运行 1正在运行 0不是正在运行
    private String appName;//应用名称
    /**
     *  已安装APP的安装时间
     */
    private String appInstallTime;

    /**
     *  已安装App的安装时间中最早的时间
     */
    private String appInstallFirstTime;

    public String getAppInstallTime() {
        return appInstallTime;
    }

    public void setAppInstallTime(String appInstallTime) {
        this.appInstallTime = appInstallTime;
    }

    public String getAppInstallFirstTime() {
        return appInstallFirstTime;
    }

    public void setAppInstallFirstTime(String appInstallFirstTime) {
        this.appInstallFirstTime = appInstallFirstTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(String isRunning) {
        this.isRunning = isRunning;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    @Override
    public String toString() {
        return "PackInfo{" +
                "packName='" + packName + '\'' +
                ", isRunning='" + isRunning + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }
}
