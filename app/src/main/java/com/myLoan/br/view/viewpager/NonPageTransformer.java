package com.myLoan.br.view.viewpager;

import androidx.viewpager.widget.ViewPager;
import android.view.View;

public class NonPageTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(View page, float position)
    {
        page.setScaleX(0.999f);//hack
    }

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();
}