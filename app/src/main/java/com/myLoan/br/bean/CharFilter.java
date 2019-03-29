package com.myLoan.br.bean;

/**
 * Created by Administrator on 2018/8/21 0021.
 */

public class CharFilter {
    /**
     * 根据
     * 1. "汉字区间"
     * 2. "数字区间"
     * 3. "小写字母区间"
     * 4. "大写字母区间"
     * 过滤掉非法字符的方法
     * @param oldString
     * @return
     */
    public static String filterCharToNormal(String oldString) {
        StringBuilder stringBuilder = new StringBuilder();
         int length = oldString.length();
        for (int i = 0; i < length; i++) {//遍历传入的String的所有字符
            char codePoint = oldString.charAt(i);
            if (//如果当前字符为常规字符,则将该字符拼入StringBuilder
                    ((codePoint >= 0x4e00) && (codePoint <= 0x9fa5)) ||//表示汉字区间
                            ((codePoint >= 0x30) && (codePoint <= 0x39)) ||//表示数字区间
                            ((codePoint >= 0x41) && (codePoint <= 0x5a)) ||//表示大写字母区间
                            ((codePoint >= 0x61) && (codePoint <= 0x7a))) {//小写字母区间
                stringBuilder.append(codePoint);
            } else {//如果当前字符为非常规字符,则忽略掉该字符
            }
        }
        return stringBuilder.toString();
    }
}
