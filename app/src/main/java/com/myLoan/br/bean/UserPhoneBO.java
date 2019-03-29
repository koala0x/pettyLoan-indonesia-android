package com.myLoan.br.bean;

/**
 * @author wzx
 * @version 3.0.0 2018/12/11 11:19
 * @update wzx 2018/12/11 11:19
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class UserPhoneBO {
    /**
     * 用户手机gps信息
     */
    private UserPhoneAddressBO userPhoneAddressBO;

    /**
     * 用户手机BSSID信息
     */
    private UserPhoneBssidBO userPhoneBssidBO;

    /**
     * 用户手机硬件信息
     */
    private UserPhoneHardwareBO userPhoneHardwareBO;

    /**
     * 用户手机ip信息
     */
    private UserPhoneIpBO userPhoneIpBO;

    /**
     * 用户手机版本信息
     */
    private UserPhoneVersionBO userPhoneVersionBO;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserPhoneAddressBO getUserPhoneAddressBO() {
        return userPhoneAddressBO;
    }

    public void setUserPhoneAddressBO(UserPhoneAddressBO userPhoneAddressBO) {
        this.userPhoneAddressBO = userPhoneAddressBO;
    }

    public UserPhoneBssidBO getUserPhoneBssidBO() {
        return userPhoneBssidBO;
    }

    public void setUserPhoneBssidBO(UserPhoneBssidBO userPhoneBssidBO) {
        this.userPhoneBssidBO = userPhoneBssidBO;
    }

    public UserPhoneHardwareBO getUserPhoneHardwareBO() {
        return userPhoneHardwareBO;
    }

    public void setUserPhoneHardwareBO(UserPhoneHardwareBO userPhoneHardwareBO) {
        this.userPhoneHardwareBO = userPhoneHardwareBO;
    }

    public UserPhoneIpBO getUserPhoneIpBO() {
        return userPhoneIpBO;
    }

    public void setUserPhoneIpBO(UserPhoneIpBO userPhoneIpBO) {
        this.userPhoneIpBO = userPhoneIpBO;
    }

    public UserPhoneVersionBO getUserPhoneVersionBO() {
        return userPhoneVersionBO;
    }

    public void setUserPhoneVersionBO(UserPhoneVersionBO userPhoneVersionBO) {
        this.userPhoneVersionBO = userPhoneVersionBO;
    }

    /**
     * 用户手机gps信息
     *
     * @author Mr.ZhenYJ
     * @date 2018/8/17 21:02
     **/
    public static class UserPhoneAddressBO {

        /**
         * 经度
         */
        private String longitude;

        /**
         * 纬度
         */
        private String latitude;


        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }

    /**
     * 用户手机BSSID信息
     *
     * @author Mr.ZhenYJ
     * @date 2018/8/17 21:02
     **/
    public static class UserPhoneBssidBO {

        /**
         * BSSID
         */
        private String bssid;

        public String getBssid() {
            return bssid;
        }

        public void setBssid(String bssid) {
            this.bssid = bssid;
        }
    }


    /**
     * 用户手机信息
     *
     * @author Mr.ZhenYJ
     * @date 2018/8/17 21:02
     **/
    public static class UserPhoneHardwareBO {

        /**
         * 手机IMEI
         */
        private String phoneImei;//

        /**
         * 手机MEID
         */
        private String phoneMeid;//

        /**
         * 手机IMSI
         */
        private String phoneImsi;//

        /**
         * 手机ICCD
         */
        private String phoneIccd;//

        /**
         * 手机uuid
         */
        private String phoneUuid;//

        /**
         * 设备型号
         */
        private String equipmentModel;//

        /**
         * 所属终端
         */
        private String affiliatedTerminal;//

        /**
         * 系统版本
         */
        private String systemVersion;//

        /**
         * 电池电量
         */
        private String batteryPower;//

        /**
         * 手机前摄像头像素
         */
        private String cameraFrontCameraPixel;

        /**
         * 手机后摄像头像素
         */
        private String cameraRearCameraPixel;

        /**
         * 手机前摄像头数量
         */
        private String cameraFrontNumber;

        /**
         * 手机后摄像头数量
         */
        private String cameraRearNumber;

        /**
         * 手机屏幕分辨率
         */
        private String screenResolution;//

        /**
         * 手机分辨路密度DPI
         */
        private String screenResolutionDpi;//

        /**
         * 手机屏幕尺寸
         */
        private String screenSize;//

        /**
         * 手机CPU型号
         */
        private String cpu;//

        /**
         * 手机主板信息
         */
        private String motherboard;//

        /**
         * 手机已经开机时间
         */
        private String bootTime;//

        /**
         * 回传客户手机拍摄照片（未经过压缩）的分辨率
         */
        private String photoResolution;

        /**
         * 客户手机是否root
         */
        private Boolean root;//

        /**
         * 客户本机手机号
         */
        private String phoneNumber;

        /**
         * 回传客户手机拍摄照片（未经过压缩）的照片分辨率的长宽比值
         */
        private String photoResolutionRatio;

        /**
         * 用户代理
         */
        private String userAgent;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPhotoResolutionRatio() {
            return photoResolutionRatio;
        }

        public void setPhotoResolutionRatio(String photoResolutionRatio) {
            this.photoResolutionRatio = photoResolutionRatio;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public String getPhoneImei() {
            return phoneImei;
        }

        public void setPhoneImei(String phoneImei) {
            this.phoneImei = phoneImei;
        }

        public String getPhoneMeid() {
            return phoneMeid;
        }

        public void setPhoneMeid(String phoneMeid) {
            this.phoneMeid = phoneMeid;
        }

        public String getPhoneImsi() {
            return phoneImsi;
        }

        public void setPhoneImsi(String phoneImsi) {
            this.phoneImsi = phoneImsi;
        }

        public String getPhoneIccd() {
            return phoneIccd;
        }

        public void setPhoneIccd(String phoneIccd) {
            this.phoneIccd = phoneIccd;
        }

        public String getPhoneUuid() {
            return phoneUuid;
        }

        public void setPhoneUuid(String phoneUuid) {
            this.phoneUuid = phoneUuid;
        }

        public String getEquipmentModel() {
            return equipmentModel;
        }

        public void setEquipmentModel(String equipmentModel) {
            this.equipmentModel = equipmentModel;
        }

        public String getAffiliatedTerminal() {
            return affiliatedTerminal;
        }

        public void setAffiliatedTerminal(String affiliatedTerminal) {
            this.affiliatedTerminal = affiliatedTerminal;
        }

        public String getSystemVersion() {
            return systemVersion;
        }

        public void setSystemVersion(String systemVersion) {
            this.systemVersion = systemVersion;
        }

        public String getBatteryPower() {
            return batteryPower;
        }

        public void setBatteryPower(String batteryPower) {
            this.batteryPower = batteryPower;
        }

        public String getCameraFrontCameraPixel() {
            return cameraFrontCameraPixel;
        }

        public void setCameraFrontCameraPixel(String cameraFrontCameraPixel) {
            this.cameraFrontCameraPixel = cameraFrontCameraPixel;
        }

        public String getCameraRearCameraPixel() {
            return cameraRearCameraPixel;
        }

        public void setCameraRearCameraPixel(String cameraRearCameraPixel) {
            this.cameraRearCameraPixel = cameraRearCameraPixel;
        }

        public String getCameraFrontNumber() {
            return cameraFrontNumber;
        }

        public void setCameraFrontNumber(String cameraFrontNumber) {
            this.cameraFrontNumber = cameraFrontNumber;
        }

        public String getCameraRearNumber() {
            return cameraRearNumber;
        }

        public void setCameraRearNumber(String cameraRearNumber) {
            this.cameraRearNumber = cameraRearNumber;
        }

        public String getScreenResolution() {
            return screenResolution;
        }

        public void setScreenResolution(String screenResolution) {
            this.screenResolution = screenResolution;
        }

        public String getScreenResolutionDpi() {
            return screenResolutionDpi;
        }

        public void setScreenResolutionDpi(String screenResolutionDpi) {
            this.screenResolutionDpi = screenResolutionDpi;
        }

        public String getScreenSize() {
            return screenSize;
        }

        public void setScreenSize(String screenSize) {
            this.screenSize = screenSize;
        }

        public String getCpu() {
            return cpu;
        }

        public void setCpu(String cpu) {
            this.cpu = cpu;
        }

        public String getMotherboard() {
            return motherboard;
        }

        public void setMotherboard(String motherboard) {
            this.motherboard = motherboard;
        }

        public String getBootTime() {
            return bootTime;
        }

        public void setBootTime(String bootTime) {
            this.bootTime = bootTime;
        }

        public String getPhotoResolution() {
            return photoResolution;
        }

        public void setPhotoResolution(String photoResolution) {
            this.photoResolution = photoResolution;
        }

        public Boolean getRoot() {
            return root;
        }

        public void setRoot(Boolean root) {
            this.root = root;
        }
    }

    /**
     * 用户手机ip信息
     *
     * @author Mr.ZhenYJ
     * @date 2018/8/17 21:02
     **/
    public static class UserPhoneIpBO {

        /**
         * MAC地址
         */
        private String macAddress;//

        /**
         * IP地址的属性
         */
        private String ipAddressAttribute;//

        /**
         * IP地址
         */
        private String ipAddress;//

        /**
         * ip获取途径
         * 1、APP注册流程中，在设置密码页面，点击设置完成按钮，上传表；
         * 2、APP登录流程中，在登录页面，点击登录按钮，上传表；
         * 3、APP借款流程中，在提现详情页面，点击提现按钮，上传表；
         * 4、在APP更改支付密码、忘记支付密码页面，点击设置完成按钮，上传表；
         * 5、在APP更改登录密码、忘记登录密码页面，点击设置完成按钮，上传表；
         * 6、客户在上传照片时的IP地址
         */
        private Integer ipType;

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        public String getIpAddressAttribute() {
            return ipAddressAttribute;
        }

        public void setIpAddressAttribute(String ipAddressAttribute) {
            this.ipAddressAttribute = ipAddressAttribute;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public Integer getIpType() {
            return ipType;
        }

        public void setIpType(Integer ipType) {
            this.ipType = ipType;
        }
    }

    /**
     * 用户手机版本信息
     *
     * @author Mr.ZhenYJ
     * @date 2018/8/17 21:02
     **/
    public static class UserPhoneVersionBO {

        /**
         * 现金贷下载渠道
         */
        private String downloadChannel;

        /**
         * 现金贷APP版本
         */
        private String appErsion;

        /**
         * 时区
         */
        private String timeZone;


        public String getDownloadChannel() {
            return downloadChannel;
        }

        public void setDownloadChannel(String downloadChannel) {
            this.downloadChannel = downloadChannel;
        }

        public String getAppErsion() {
            return appErsion;
        }

        public void setAppErsion(String appErsion) {
            this.appErsion = appErsion;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }
    }

}
