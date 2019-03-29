package com.myLoan.br.bean;

/**
 * @author wzx
 * @version 3.0.0 2019/2/12 18:42
 * @update wzx 2019/2/12 18:42
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class HelpChoseDTO {

    /**
     * helpId : 0
     * usefulEnum : NOT_SELECT
     */

    private int helpId;
    private String usefulEnum;

    public int getHelpId() {
        return helpId;
    }

    public void setHelpId(int helpId) {
        this.helpId = helpId;
    }

    public String getUsefulEnum() {
        return usefulEnum;
    }

    public void setUsefulEnum(String usefulEnum) {
        this.usefulEnum = usefulEnum;
    }
}
