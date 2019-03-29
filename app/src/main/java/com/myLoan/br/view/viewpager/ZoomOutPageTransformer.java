package com.myLoan.br.view.viewpager;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float SCALE_MAX = 0.85f;

    @Override
    public void transformPage(View page, float position) {
        float scale = (position < 0)
                ? ((1 - SCALE_MAX) * position + 1)
                : ((SCALE_MAX - 1) * position + 1);
        //为了滑动过程中，page间距不变，这里做了处理
        if (position < 0) {
            ViewCompat.setPivotX(page, page.getWidth());
            ViewCompat.setPivotY(page, page.getHeight() / 2);
        } else {
            ViewCompat.setPivotX(page, 0);
            ViewCompat.setPivotY(page, page.getHeight() / 2);
        }
        ViewCompat.setScaleX(page, scale);
        ViewCompat.setScaleY(page, scale);
    }
}