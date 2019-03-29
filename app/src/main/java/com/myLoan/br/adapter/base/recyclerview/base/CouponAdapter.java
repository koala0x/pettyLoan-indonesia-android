package com.myLoan.br.adapter.base.recyclerview.base;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.myLoan.br.R;
import com.myLoan.br.adapter.base.recyclerview.CommonAdapter;
import com.myLoan.br.bean.HelpContentBean;
import com.myLoan.br.bean.HelpContentBean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ${className}.java是极搜小说的$DES$类。
 *
 * @author wzx
 * @version 3.0.0 2019/3/1 11:16
 * @update wzx 2019/3/1 11:16
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class CouponAdapter extends CommonAdapter<String> {

    private List<String> mlist = new ArrayList<>();
    private Context mContext;

    public CouponAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        mlist = datas;
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, String dataBean, int position) {
        ImageView imageView = holder.getView(R.id.im_coupon_bg);
        TextView textViewGo = holder.getView(R.id.tv_coupon_canuse);
        TextView textViewName = holder.getView(R.id.tv_new_customer_coupon);
        TextView textViewIntereset = holder.getView(R.id.tv_coupon_interest);
        TextView textViewMaxLoan = holder.getView(R.id.tv_max_loan);
        TextView textViewDeadLine = holder.getView(R.id.tv_dead_line);
        TextView textViewExpired = holder.getView(R.id.ig_coupon_expired);
        textViewGo.setText(dataBean);

    }
}
