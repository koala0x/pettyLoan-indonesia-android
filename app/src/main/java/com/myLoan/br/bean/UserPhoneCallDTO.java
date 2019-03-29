package com.myLoan.br.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class UserPhoneCallDTO {


    /**
     * list : [{"callDuration":"string","callPhoneNumber":"string","callStartTime":"string","callType":"string","callerName":"string","createTime":"2018-08-20T09:03:08.662Z","deleteStatus":true,"id":0,"modifyTime":"2018-08-20T09:03:08.662Z","phonePid":"string","reject":"string","userId":0}]
     * phonePid : string
     * userId : 0
     */

    private String phonePid;
    private String userId;
    private List<UserPhoneCallBO> list;

    public String getPhonePid() {
        return phonePid;
    }

    public void setPhonePid(String phonePid) {
        this.phonePid = phonePid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserPhoneCallBO> getList() {
        return list;
    }

    public void setList(List<UserPhoneCallBO> list) {
        this.list = list;
    }

    public static class UserPhoneCallBO {
        /**
         * callDuration : string
         * callPhoneNumber : string
         * callStartTime : string
         * callType : string
         * callerName : string
         * createTime : 2018-08-20T09:03:08.662Z
         * deleteStatus : true
         * id : 0
         * modifyTime : 2018-08-20T09:03:08.662Z
         * phonePid : string
         * reject : string
         * userId : 0
         */

        private String callDuration;
        private String callPhoneNumber;
        private String callStartTime;
        private long timeKey;
        private String callType;
        private String callerName;
        private String createTime;
        private boolean deleteStatus;

        public long getTimeKey() {
            return timeKey;
        }

        public void setTimeKey(long timeKey) {
            this.timeKey = timeKey;
        }

        private int id;
        private String modifyTime;
        private String phonePid;
        private String reject;
        private String userId;


        public String getCallDuration() {
            return callDuration;
        }

        public void setCallDuration(String callDuration) {
            this.callDuration = callDuration;
        }

        public String getCallPhoneNumber() {
            return callPhoneNumber;
        }

        public void setCallPhoneNumber(String callPhoneNumber) {
            this.callPhoneNumber = callPhoneNumber;
        }

        public String getCallStartTime() {
            return callStartTime;
        }

        public void setCallStartTime(String callStartTime) {
            this.callStartTime = callStartTime;
        }

        public String getCallType() {
            return callType;
        }

        public void setCallType(String callType) {
            this.callType = callType;
        }

        public String getCallerName() {
            return callerName;
        }

        public void setCallerName(String callerName) {
            this.callerName = callerName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPhonePid() {
            return phonePid;
        }

        public void setPhonePid(String phonePid) {
            this.phonePid = phonePid;
        }

        public String getReject() {
            return reject;
        }

        public void setReject(String reject) {
            this.reject = reject;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "callDuration='" + callDuration + '\'' +
                    ", callPhoneNumber='" + callPhoneNumber + '\'' +
                    ", callStartTime='" + callStartTime + '\'' +
                    ", timeKey=" + timeKey +
                    ", callType='" + callType + '\'' +
                    ", callerName='" + callerName + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", deleteStatus=" + deleteStatus +
                    ", id=" + id +
                    ", modifyTime='" + modifyTime + '\'' +
                    ", phonePid='" + phonePid + '\'' +
                    ", reject='" + reject + '\'' +
                    ", userId=" + userId +
                    '}';
        }
    }
}
