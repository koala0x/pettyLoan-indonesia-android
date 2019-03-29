package com.myLoan.br.tools.math;

import android.content.Context;

import java.text.NumberFormat;

/**
 * Created by Administrator on 2018/7/14 0014.
 */

public class NumberUtil {
    public static String forMat(Context context, double amount) {
        // 把string类型的货币转换为double类型。
        // 想要转换成指定国家的货币格式
    /*    DecimalFormat df = new DecimalFormat("0.00");

        String result = df.format(amount);*/
       /* NumberFormat format = NumberFormat.getCurrencyInstance(context.getResources().getConfiguration().locale);
        // 把转换后的货币String类型返回
        String numString = format.format(amount);*/
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return format.format(amount);
    }

}
