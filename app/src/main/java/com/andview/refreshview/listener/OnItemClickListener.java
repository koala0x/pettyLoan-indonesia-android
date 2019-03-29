package com.andview.refreshview.listener;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public interface OnItemClickListener {
    void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

    boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
}
