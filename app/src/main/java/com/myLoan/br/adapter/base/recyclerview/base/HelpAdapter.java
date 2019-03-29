package com.myLoan.br.adapter.base.recyclerview.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.myLoan.br.R;
import com.myLoan.br.adapter.base.recyclerview.CommonAdapter;
import com.myLoan.br.bean.HelpContentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzx
 * @version 3.0.0 2019/2/13 11:12
 * @update wzx 2019/2/13 11:12
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class HelpAdapter extends CommonAdapter<HelpContentBean.DataBean> {
    private List<HelpContentBean.DataBean> mlistHelpCenter = new ArrayList<>();
    private Context mContext;
    private OnclickUse mOnclickUse;

    public HelpAdapter(Context context, int layoutId, List<HelpContentBean.DataBean> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final HelpContentBean.DataBean helpCenterBean, int position) {
        TextView questionTitleTextView = holder.getView(R.id.tv_question_title);
        final TextView helpfulnessTextView = holder.getView(R.id.tv_helpcenter_helpfulness);
        final TextView unhelpfulnessTextView = holder.getView(R.id.tv_unhelpfulness);
        TextView contentTextView = holder.getView(R.id.tv_helpcenter_content);
        List<String> contents = new ArrayList<>();
        questionTitleTextView.setText(helpCenterBean.getTitle());
        contents = helpCenterBean.getContents();
        StringBuffer stringBuffer = new StringBuffer();

        if (contents != null && contents.size() > 1) {
            for (int i = 0; i < contents.size(); i++) {
                if (i != contents.size() - 1) {
                    stringBuffer.append(contents.get(i) + "\n");
                } else {
                    stringBuffer.append(contents.get(i));
                }
            }
        }

        contentTextView.setText(stringBuffer);
        final String usefulEnum = helpCenterBean.getUsefulEnum();

        final Drawable drawable1 = mContext.getResources().getDrawable(R.mipmap.ic_helpfulness);
        final Drawable drawable2 = mContext.getResources().getDrawable(R.mipmap.ic_unhelpfulness);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());

        if (usefulEnum.equals("USEFUL")) {
            helpfulnessTextView.setCompoundDrawables(null, drawable1, null, null);
            unhelpfulnessTextView.setCompoundDrawables(null, drawable2, null, null);
        } else if (usefulEnum.equals("USELESS")) {
            helpfulnessTextView.setCompoundDrawables(null, drawable2, null, null);
            unhelpfulnessTextView.setCompoundDrawables(null, drawable1, null, null);
        } else {
            helpfulnessTextView.setCompoundDrawables(null, drawable2, null, null);
            unhelpfulnessTextView.setCompoundDrawables(null, drawable2, null, null);
        }


        helpfulnessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usefulEnum.equals("USEFUL")) {
                    helpCenterBean.setUsefulEnum("NOT_SELECT");
                    if (mOnclickUse != null)
                        mOnclickUse.onClickNotSelect(helpCenterBean.getId());
                } else {
                    helpCenterBean.setUsefulEnum("USEFUL");
                    if (mOnclickUse != null)
                        mOnclickUse.onClickUseful(helpCenterBean.getId());
                }

                notifyDataSetChanged();

            }
        });

        unhelpfulnessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usefulEnum.equals("USELESS")) {
                    helpCenterBean.setUsefulEnum("NOT_SELECT");
                    if (mOnclickUse != null)
                        mOnclickUse.onClickNotSelect(helpCenterBean.getId());
                } else {
                    helpCenterBean.setUsefulEnum("USELESS");
                    if (mOnclickUse != null)
                        mOnclickUse.onClickUseless(helpCenterBean.getId());
                }

                notifyDataSetChanged();

            }
        });
    }


    public void setOnclickUse(OnclickUse onclickUse) {
        mOnclickUse = onclickUse;
    }

    public interface OnclickUse {

        void onClickUseful(int id);

        void onClickUseless(int id);

        void onClickNotSelect(int id);
    }
}
