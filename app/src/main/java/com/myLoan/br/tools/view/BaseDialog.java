package com.myLoan.br.tools.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myLoan.br.R;
import com.myLoan.br.tools.DebugLog;


/**
 * 基础类 （只写了基础，后期需要扩展，自己去完善）
 * Created by TGHZ on 2017/8/30.
 */

public class BaseDialog extends Dialog {
    private Context context;

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(@NonNull View view) {

        super.setContentView(view);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    /**
     * (后期有需要完善)
     */
    public static class Build {
        private Context context;
        private View infateView;
        private Button btnSure, btnCancel;
        private RelativeLayout mrlBtnSure, mrlBtnCancel;
        private int layoutResID;
        private boolean flag;
        private boolean cancel;
        private int with = 0, height = 0;
        private RelativeLayout mwindowView;
        //mesage 左距右距
        private int leftMargin, topMargin;

        public Build(Context context) {
            this.context = context;
            infateView = LayoutInflater.from(context).inflate(R.layout.dialog_base, null);
            btnSure = (Button) infateView.findViewById(R.id.btn_sure);
            btnCancel = (Button) infateView.findViewById(R.id.btn_cancel);
            mwindowView = (RelativeLayout) infateView.findViewById(R.id.rl_window);
            mrlBtnSure = (RelativeLayout) infateView.findViewById(R.id.rl_sure);
            mrlBtnCancel = (RelativeLayout) infateView.findViewById(R.id.rl_cancel);
            with = DensityUtil.dip2px(context, 285);
            height = DensityUtil.dip2px(context, 225);
            leftMargin = DensityUtil.dip2px(context, 20);
            topMargin = DensityUtil.dip2px(context, 28);
        }

        public Build setSzie(int with, int height) {
            this.with = with;
            this.height = height;
            return this;
        }

        public Build setContentView(int layoutResID) {
            this.layoutResID = layoutResID;
            return this;
        }

        public Build setTitle(String title) {
            return setTitle(title, "", 0);
        }

        public Build setTitle(String title, int size) {
            return setTitle(title, "", size);
        }

        public Build setTitle(String title, String color) {
            return setTitle(title, color, 0);
        }

        /**
         * @param title 标题
         * @param color 字体颜色（""显示默认颜色）
         * @param size  字体大小（0显示默认大小）
         * @return
         */

        public Build setTitle(String title, String color, int size) {
            TextView mtvTitle = (TextView) infateView.findViewById(R.id.tv_title);
            mtvTitle.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(color)) {
                mtvTitle.setTextColor(Color.parseColor(color));
            }
            if (size != 0) {
                mtvTitle.setTextSize(size);
            }
            mtvTitle.setText(title);
            return this;
        }

        /**
         * 设置显示内容 左距，顶距
         *
         * @param left 左距
         * @param top  顶距
         * @return
         */
        public Build setMarginLeftAndMarginTop(int left, int top) {
            leftMargin = left;
            topMargin = top;
            return this;
        }

        /**
         * 不设置，使用默认颜色
         *
         * @param message
         * @return
         */
        public Build setMessage(String message) {

            return setMessage(message, "", 0, -1);
        }

        public Build setMessage(String message, String color, int gravty) {
            return setMessage(message, color, 0, gravty);
        }

        public Build setMessage(String message, String color) {
            return setMessage(message, color, 0, -1);
        }

        public Build setMessage(String message, int textSize, int gravity) {

            return setMessage(message, "", textSize, gravity);
        }

        public Build setMessage(String message, int textSize) {

            return setMessage(message, "", textSize, -1);
        }

        /**
         * 设置颜色 大小
         *
         * @param message
         * @param color
         * @param textSize 0显示默认大小
         * @param gravity  方向， -1 默认居中
         * @return
         */
        public Build setMessage(String message, String color, int textSize, int gravity) {
            TextView tv = new TextView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            if (gravity == -1) {
                lp.leftMargin = leftMargin;
                lp.topMargin = topMargin;
            } else {
                tv.setGravity(Gravity.CENTER);
            }
            //  tv.setBackgroundColor(Color.parseColor("#ffcc00"));
            //  tv.setGravity(Gravity.TOP);
            tv.setLayoutParams(lp);
            if (!TextUtils.isEmpty(color)) {
                tv.setTextColor(Color.parseColor(color));
            } else {
                tv.setTextColor(Color.parseColor("#000000"));
            }
            if (textSize != 0) {
                tv.setTextSize(textSize);
            }
            tv.setText(message);
            return setContentView(tv);
        }

        public Build setContentView(View view) {
            LinearLayout llContent = (LinearLayout) infateView.findViewById(R.id.ll_content);
            llContent.addView(view);
            return this;
        }

        public Build setContentView(View view, int color) {
            LinearLayout llContent = (LinearLayout) infateView.findViewById(R.id.ll_content);
            llContent.setBackgroundColor(color);
            mwindowView.setBackgroundColor(color);
            llContent.addView(view);
            return this;
        }

        public Build setCancelable(boolean flag) {
            this.flag = flag;
            return this;
        }

        public Build setCanceledOnTouchOutside(boolean cancel) {
            this.cancel = cancel;
            return this;
        }

        public Build setPositiveButton(final OnClickListener click) {
            return setPositiveButton(context.getString(R.string.btn_sure), 0, click);
        }

        public Build setPositiveButton(String text, final OnClickListener click) {
            return setPositiveButton(text, 0, click);
        }

        public Build setPositiveButton(int leftMargin, final OnClickListener click) {
            return setPositiveButton(context.getString(R.string.btn_sure), leftMargin, click);
        }

        /**
         * @param text       确定按钮提示
         * @param leftMargin 左距
         * @param click      点击
         * @return
         */
        public Build setPositiveButton(String text, int leftMargin, final OnClickListener click) {
            if (!TextUtils.isEmpty(text)) {
                btnSure.setText(text);
            }
            mrlBtnSure.setVisibility(View.VISIBLE);
            btnSure.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) btnSure.getLayoutParams();
            if (lp == null) {
                lp = new RelativeLayout.LayoutParams(DensityUtil.dip2px(context, 100), DensityUtil.dip2px(context, 40));
            }
            if (leftMargin == 0) {
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            } else {
                lp.leftMargin = DensityUtil.dip2px(context, leftMargin);
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            }
            btnSure.setLayoutParams(lp);
            btnSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onClick((DialogInterface) v.getTag(), v.getId());
                }
            });
            return this;
        }

        public Build setNegativeButton(OnClickListener click) {

            return setNegativeButton(context.getString(R.string.btn_cancel), 0, click);
        }

        public Build setNegativeButton(int marginRight, OnClickListener click) {

            return setNegativeButton(context.getString(R.string.btn_cancel), marginRight, click);
        }

        public Build setNegativeButton(String text, OnClickListener click) {

            return setNegativeButton(text, 0, click);
        }

        /**
         * @param text        取消按钮提示
         * @param rightMargin 右距
         * @param click       点击
         * @return
         */
        public Build setNegativeButton(String text, int rightMargin, final OnClickListener click) {
            if (!TextUtils.isEmpty(text)) {
                btnCancel.setText(text);
            }
            mrlBtnCancel.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) btnCancel.getLayoutParams();
            if (lp == null) {
                lp = new RelativeLayout.LayoutParams(DensityUtil.dip2px(context, 100), DensityUtil.dip2px(context, 40));
            }
            if (rightMargin == 0) {
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            } else {
                lp.rightMargin = DensityUtil.dip2px(context, rightMargin);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            btnCancel.setLayoutParams(lp);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onClick((DialogInterface) v.getTag(), v.getId());
                }
            });
            return this;
        }

        /**
         * 创建dialog
         *
         * @return
         */
        public Dialog createDialog() {
            Dialog dialog = new BaseDialog(context, R.style.Dialog_Tip);
            btnSure.setTag(dialog);
            btnCancel.setTag(dialog);
            dialog.setCancelable(flag);
            dialog.setCanceledOnTouchOutside(cancel);
            dialog.setContentView(infateView);
            DebugLog.e("wang", "with" + with + "::" + "height" + height);
            if (with != 0 || height != 0) {
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.width = with;
                lp.height = height;
                dialog.getWindow().setAttributes(lp);
            }

            return dialog;
        }

        /**
         * 展示dialog
         *
         * @return
         */
        public Dialog show() {
            Dialog dialog = createDialog();
            dialog.show();
            return dialog;
        }

    }

}
