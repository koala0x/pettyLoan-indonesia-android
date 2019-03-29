package com.myLoan.br.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.myLoan.br.MyApplication;
import com.myLoan.br.R;

/**
 * @author wzx
 * @version 3.0.0 2018/11/23 9:26
 * @update wzx 2018/11/23 9:26
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class ToastView {
    public static void showShort(String msg, int color) {
        show(msg, Toast.LENGTH_SHORT, color);
    }

    public static void showShort(String msg) {
        show(msg, Toast.LENGTH_SHORT, R.color.FF585858);
    }

    public static void showLong(String msg, int color) {
        show(msg, Toast.LENGTH_LONG, color);
    }

    private static void show(String massage, int show_length, int color) {
        Context context = MyApplication.mContext;
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        //获取TextView
        TextView title = (TextView) view.findViewById(R.id.tv_toast);
        title.setTextColor(color);
        //设置显示的内容
        title.setText(massage);
        Toast toast = new Toast(context);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        //设置显示时间
        toast.setDuration(show_length);

        toast.setView(view);
        if (!TextUtils.isEmpty(massage)) {
            toast.show();
        }

    }
}
