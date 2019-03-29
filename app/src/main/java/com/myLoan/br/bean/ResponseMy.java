package com.myLoan.br.bean;

/**
 * Created by Administrator on 2018/7/24 0024.
 */

public class ResponseMy {

    /**
     * code : 0
     * message : string
     * page : {"currentPage":0,"dbIndex":0,"dbNumber":0,"pageSize":0,"totalNumber":0,"totalPage":0}
     * success : true
     */

    private int code;
    private String message;
    private PageBean page;
    private boolean success;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public static class PageBean {
        /**
         * currentPage : 0
         * dbIndex : 0
         * dbNumber : 0
         * pageSize : 0
         * totalNumber : 0
         * totalPage : 0
         */

        private int currentPage;
        private int dbIndex;
        private int dbNumber;
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

    @Override
    public String toString() {
        return "ResponseMy{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", page=" + page +
                ", success=" + success +
                ", data='" + data + '\'' +
                '}';
    }
}
