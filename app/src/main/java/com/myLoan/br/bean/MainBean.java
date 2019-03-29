package com.myLoan.br.bean;

import java.util.List;

public class MainBean {

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
        private Double auditMoney;
        private String createTime;
        private Integer expireRejectDays;
        private Boolean hasDue;
        private Integer loanDays;
        private Integer loanEndDays;
        private String modifyTime;
        private Integer orderType;
        private Integer overDueDays;
        private Double repayAmount;
        private Long repayDate;
        private Integer status;
        private Integer auditOrderId;
        private Integer orderId;
        private int currentPeriod;
        private double dueMonthDelayRate;
        private String orderNo;
        private List<productDTOS> productDTOS;
        private int creditStatus;//状态;1-待审核；2-人工审核通过；3-人工审核拒绝；4-机审拒绝；5-失效

        public int getCreditStatus() {
            return creditStatus;
        }

        public void setCreditStatus(int creditStatus) {
            this.creditStatus = creditStatus;
        }

        public List<MainBean.productDTOS> getProductDTOS() {
            return productDTOS;
        }

        public void setProductDTOS(List<MainBean.productDTOS> productDTOS) {
            this.productDTOS = productDTOS;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public int getCurrentPeriod() {
            return currentPeriod;
        }

        public void setCurrentPeriod(int currentPeriod) {
            this.currentPeriod = currentPeriod;
        }

        public double getDueMonthDelayRate() {
            return dueMonthDelayRate;
        }

        public void setDueMonthDelayRate(double dueMonthDelayRate) {
            this.dueMonthDelayRate = dueMonthDelayRate;
        }

        public Integer getAuditOrderId() {
            return auditOrderId;
        }

        public void setAuditOrderId(Integer auditOrderId) {
            this.auditOrderId = auditOrderId;
        }

        public Double getAuditMoney() {
            return auditMoney;
        }

        public void setAuditMoney(Double auditMoney) {
            this.auditMoney = auditMoney;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Integer getExpireRejectDays() {
            return expireRejectDays;
        }

        public void setExpireRejectDays(Integer expireRejectDays) {
            this.expireRejectDays = expireRejectDays;
        }

        public Boolean getHasDue() {
            return hasDue;
        }

        public void setHasDue(Boolean hasDue) {
            this.hasDue = hasDue;
        }

        public Integer getLoanDays() {
            return loanDays;
        }

        public void setLoanDays(Integer loanDays) {
            this.loanDays = loanDays;
        }

        public Integer getLoanEndDays() {
            return loanEndDays;
        }

        public void setLoanEndDays(Integer loanEndDays) {
            this.loanEndDays = loanEndDays;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public Integer getOverDueDays() {
            return overDueDays;
        }

        public void setOverDueDays(Integer overDueDays) {
            this.overDueDays = overDueDays;
        }

        public Double getRepayAmount() {
            return repayAmount;
        }

        public void setRepayAmount(Double repayAmount) {
            this.repayAmount = repayAmount;
        }

        public Long getRepayDate() {
            return repayDate;
        }

        public void setRepayDate(Long repayDate) {
            this.repayDate = repayDate;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

    public static class productDTOS {
        private Integer approveAmountMax;
        private Integer approveAmountMin;
        private Integer productId;
        private Integer productType;
        private Integer loanTermMix;
        private Integer loanTermMax;

        public Integer getLoanTermMix() {
            return loanTermMix;
        }

        public void setLoanTermMix(Integer loanTermMix) {
            this.loanTermMix = loanTermMix;
        }

        public Integer getLoanTermMax() {
            return loanTermMax;
        }

        public void setLoanTermMax(Integer loanTermMax) {
            this.loanTermMax = loanTermMax;
        }

        public Integer getApproveAmountMax() {
            return approveAmountMax;
        }

        public void setApproveAmountMax(Integer approveAmountMax) {
            this.approveAmountMax = approveAmountMax;
        }

        public Integer getApproveAmountMin() {
            return approveAmountMin;
        }

        public void setApproveAmountMin(Integer approveAmountMin) {
            this.approveAmountMin = approveAmountMin;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getProductType() {
            return productType;
        }

        public void setProductType(Integer productType) {
            this.productType = productType;
        }
    }

    public static class PageBean {
        private int currentPage;
        private int dbIndex;
        private int dbNumber;
        private boolean isPaging;
        private int pageSize;
        private int totalNumber;
        private int totalPage;


        public boolean isPaging() {
            return isPaging;
        }

        public void setPaging(boolean paging) {
            isPaging = paging;
        }

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

        @Override
        public String toString() {
            return "PageBean{" +
                    "currentPage=" + currentPage +
                    ", dbIndex=" + dbIndex +
                    ", dbNumber=" + dbNumber +
                    ", isPaging=" + isPaging +
                    ", pageSize=" + pageSize +
                    ", totalNumber=" + totalNumber +
                    ", totalPage=" + totalPage +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", page=" + page +
                ", success=" + success +
                '}';
    }
}
