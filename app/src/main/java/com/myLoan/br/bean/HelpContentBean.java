package com.myLoan.br.bean;

import java.util.List;

/**
 * @author wzx
 * @version 3.0.0 2019/2/12 20:11
 * @update wzx 2019/2/12 20:11
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class HelpContentBean {

    /**
     * code : 0
     * success : true
     * data : [{"id":5,"title":"测试问题1","contents":["1.测试问题1回答1","2.测试问题1回答2"],"type":null,"order":1,"usefulEnum":"NOT_SELECT"},{"id":6,"title":"测试问题1","contents":["1.测试问题1回答1","2.测试问题1回答2"],"type":null,"order":2,"usefulEnum":"NOT_SELECT"},{"id":4,"title":"测试问题1","contents":["1.测试问题1回答1","2.测试问题1回答2"],"type":null,"order":3,"usefulEnum":"NOT_SELECT"}]
     * message : null
     * page : null
     */

    private int code;
    private boolean success;
    private String message;
    private Object page;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5
         * title : 测试问题1
         * contents : ["1.测试问题1回答1","2.测试问题1回答2"]
         * type : null
         * order : 1
         * usefulEnum : NOT_SELECT
         */

        private int id;
        private String title;
        private Object type;
        private int order;
        private String usefulEnum;
        private List<String> contents;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getUsefulEnum() {
            return usefulEnum;
        }

        public void setUsefulEnum(String usefulEnum) {
            this.usefulEnum = usefulEnum;
        }

        public List<String> getContents() {
            return contents;
        }

        public void setContents(List<String> contents) {
            this.contents = contents;
        }
    }
}
