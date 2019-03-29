package com.myLoan.br.adapter.base.recyclerview;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/6/30 0030.
 */

public class AddItemDecoration extends RecyclerView.ItemDecoration {
    private int top;
    private int left;
    private int buttom;
    private int right;

    public AddItemDecoration(int top, int left, int buttom, int right) {
        this.top = top;
        this.right = right;
        this.buttom = buttom;
        this.left = left;
    }

    public AddItemDecoration(int top, int left) {
        this.top = top;
        this.right = 0;
        this.buttom = 0;
        this.left = left;
    }

    public AddItemDecoration(int buttom) {
        this.top = 0;
        this.right = 0;
        this.buttom = buttom;
        this.left = 0;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(left, top, right, buttom);
    }
}
