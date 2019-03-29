package com.myLoan.br.tools.view;

import android.content.Context;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * @author wzx
 * @version 3.0.0 2018/11/15 10:32
 * @update wzx 2018/11/15 10:32
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class SpannableStringUtil {
    /**
     * 用于给相同的内容着色
     *
     * @param colorString 需要着色的内容用户输入的内容
     * @param allString   完整的String
     * @return 返回着色后的字符串
     */

    public static SpannableStringBuilder getColorString(Context context, String colorString, String allString, @ColorRes int id) {
        //用于部分着色的文字对象
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        if (allString.contains(colorString)) {
            String[] strings = allString.split(colorString);
            SpannableString textColored = new SpannableString(colorString);
            textColored.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, id)), 0, colorString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (strings.length == 0) {
                //当前全部的文字都是着色的,检查重复次数
                int i = allString.length() / colorString.length();
                for (int j = 0; j < i; j++) {
                    ssb.append(textColored);
                }
            } else if (strings.length == 1) {
                //头尾出现一次的情况
                if (allString.startsWith(colorString)) {
                    //头上出现的情况
                    ssb.append(textColored).append(allString.substring(colorString.length()));
                } else {
                    //末尾出现
                    ssb.append(allString.split(colorString)[0]).append(colorString);
                }
            } else {
                //多次出现
                //中间出现一次
                ssb.append(strings[0]).append(textColored).append(strings[1]);
            }
        } else {
            ssb.append(allString);
        }
        return ssb;
    }
}
