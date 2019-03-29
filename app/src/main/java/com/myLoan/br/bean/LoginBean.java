package com.myLoan.br.bean;

/**
 *
 * @author wzx
 * @version 3.0.0 2018/11/22 19:09
 * @update wzx 2018/11/22 19:09
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class LoginBean {

    /**
     * code : 0
     * data : {"areaCode":"string","birthday":"2018-12-06T08:54:40.945Z","birthplace":"string","block":"string","city":"string","cpf":"string","createTime":"2018-12-06T08:54:40.945Z","currentResidential":"string","deviceId":"string","educationBackground":0,"email":"string","fatherName":"string","gender":0,"headPortrait":"string","homeTelephone":"string","homeTelephoneAreaCode":"string","id":0,"idNumber":"string","loanPurpose":0,"maritalStatus":0,"modifyTime":"2018-12-06T08:54:40.945Z","motherName":"string","password":"string","phoneNumber":"string","registerTime":"2018-12-06T08:54:40.945Z","residentialDuration":0,"residentialPostcode":"string","state":"string","token":"string","username":"string","usernameShort":"string"}
     * message : string
     * page : {"currentPage":0,"dbIndex":0,"dbNumber":0,"isPaging":true,"pageSize":0,"totalNumber":0,"totalPage":0}
     * success : true
     */

    private int code;
    private DataBean data;
    private String message;
    private PageBean page;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * areaCode : string
         * birthday : 2018-12-06T08:54:40.945Z
         * birthplace : string
         * block : string
         * city : string
         * cpf : string
         * createTime : 2018-12-06T08:54:40.945Z
         * currentResidential : string
         * deviceId : string
         * educationBackground : 0
         * email : string
         * fatherName : string
         * gender : 0
         * headPortrait : string
         * homeTelephone : string
         * homeTelephoneAreaCode : string
         * id : 0
         * idNumber : string
         * loanPurpose : 0
         * maritalStatus : 0
         * modifyTime : 2018-12-06T08:54:40.945Z
         * motherName : string
         * password : string
         * phoneNumber : string
         * registerTime : 2018-12-06T08:54:40.945Z
         * residentialDuration : 0
         * residentialPostcode : string
         * state : string
         * token : string
         * username : string
         * usernameShort : string
         */

        private String areaCode;
        private String birthday;
        private String birthplace;
        private String block;
        private String city;
        private String cpf;
        private String createTime;
        private String currentResidential;
        private String deviceId;
        private int educationBackground;
        private String email;
        private String fatherName;
        private String gender;
        private String headPortrait;
        private String homeTelephone;
        private String homeTelephoneAreaCode;
        private int id;
        private String idNumber;
        private int loanPurpose;
        private int maritalStatus;
        private String modifyTime;
        private String motherName;
        private String password;
        private String phoneNumber;
        private String registerTime;
        private int residentialDuration;
        private String residentialPostcode;
        private String state;
        private String token;
        private String username;
        private String usernameShort;
        private String appsflyId;
        private String clientId;
        private String latitude;
        private String longitude;
        private String vest;
        private String systemVersion;
        private String appVersion;
        private String deviceType;

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getAppsflyId() {
            return appsflyId;
        }

        public void setAppsflyId(String appsflyId) {
            this.appsflyId = appsflyId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getVest() {
            return vest;
        }

        public void setVest(String vest) {
            this.vest = vest;
        }

        public String getSystemVersion() {
            return systemVersion;
        }

        public void setSystemVersion(String systemVersion) {
            this.systemVersion = systemVersion;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBirthplace() {
            return birthplace;
        }

        public void setBirthplace(String birthplace) {
            this.birthplace = birthplace;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCurrentResidential() {
            return currentResidential;
        }

        public void setCurrentResidential(String currentResidential) {
            this.currentResidential = currentResidential;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public int getEducationBackground() {
            return educationBackground;
        }

        public void setEducationBackground(int educationBackground) {
            this.educationBackground = educationBackground;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getHomeTelephone() {
            return homeTelephone;
        }

        public void setHomeTelephone(String homeTelephone) {
            this.homeTelephone = homeTelephone;
        }

        public String getHomeTelephoneAreaCode() {
            return homeTelephoneAreaCode;
        }

        public void setHomeTelephoneAreaCode(String homeTelephoneAreaCode) {
            this.homeTelephoneAreaCode = homeTelephoneAreaCode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public int getLoanPurpose() {
            return loanPurpose;
        }

        public void setLoanPurpose(int loanPurpose) {
            this.loanPurpose = loanPurpose;
        }

        public int getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(int maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getMotherName() {
            return motherName;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName;
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

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public int getResidentialDuration() {
            return residentialDuration;
        }

        public void setResidentialDuration(int residentialDuration) {
            this.residentialDuration = residentialDuration;
        }

        public String getResidentialPostcode() {
            return residentialPostcode;
        }

        public void setResidentialPostcode(String residentialPostcode) {
            this.residentialPostcode = residentialPostcode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsernameShort() {
            return usernameShort;
        }

        public void setUsernameShort(String usernameShort) {
            this.usernameShort = usernameShort;
        }
    }

    public static class PageBean {
        /**
         * currentPage : 0
         * dbIndex : 0
         * dbNumber : 0
         * isPaging : true
         * pageSize : 0
         * totalNumber : 0
         * totalPage : 0
         */

        private int currentPage;
        private int dbIndex;
        private int dbNumber;
        private boolean isPaging;
        private int pageSize;
        private int totalNumber;
        private int totalPage;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getDbIndex() {
            return dbIndex;
        }

        public void setDbIndex(int dbIndex) {
            this.dbIndex = dbIndex;
        }

        public int getDbNumber() {
            return dbNumber;
        }

        public void setDbNumber(int dbNumber) {
            this.dbNumber = dbNumber;
        }

        public boolean isIsPaging() {
            return isPaging;
        }

        public void setIsPaging(boolean isPaging) {
            this.isPaging = isPaging;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(int totalNumber) {
            this.totalNumber = totalNumber;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    }
}
