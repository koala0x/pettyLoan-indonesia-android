package com.myLoan.br.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CusCoordinatorLayout extends CoordinatorLayout {
    private boolean isDispatch = true;

    public CusCoordinatorLayout(@NonNull Context context) {
        super(context);
    }

    public CusCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setIsDispatch(boolean isDispatch) {
        this.isDispatch = isDispatch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isDispatch) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
