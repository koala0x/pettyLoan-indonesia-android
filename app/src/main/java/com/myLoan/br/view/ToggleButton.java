package com.myLoan.br.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.myLoan.br.R;

/**
 * Created by wuzhixiao on 2018/11/20.
 */
public class ToggleButton extends View {
    private Bitmap backgroundBitmap;
    private Bitmap slideButtonBitmap;
    private boolean currentState;
    private int currentX;
    private boolean isTouching;
    private OnMyToggleButtonStateChangedListener mListener;

    public ToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttrs(attrs);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
//		super(context, attrs);
        this(context, attrs, 0);
    }

    public ToggleButton(Context context) {
//		super(context);
        this(context, null);
    }

    private void setAttrs(AttributeSet attrs) {
        String namespace = "http://schemas.android.com/apk/res/com.itcast.togglebutton";
        int backgroundResValue = attrs.getAttributeResourceValue(namespace, "backgroundRes", 0);
        setBackgroundRes(backgroundResValue);

        int slideButtonResValue = attrs.getAttributeResourceValue(namespace, "slideButtonRes", 0);
        setSlideButtonRes(slideButtonResValue);

        currentState = attrs.getAttributeBooleanValue(namespace, "state", false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        // 当前是否是滑动状态
        if (isTouching) {// 是滑动状态，根据手指触摸的位置绘制滑动块
            int left = currentX - slideButtonBitmap.getWidth() / 2;
            // 给控件设置边界
            if (left < 0) {// 如果超出左边界，直接画到最左边
                left = 0;
            } else if (left > backgroundBitmap.getWidth() - slideButtonBitmap.getWidth()) {// 如果超出最右边，直接画到最右边
                left = backgroundBitmap.getWidth() - slideButtonBitmap.getWidth();
            }
            canvas.drawBitmap(slideButtonBitmap, left, 0, null);
        } else {
            // 绘制滑动块图片
            if (currentState) {// 根据当前开关的状态绘制滑动块的位置
                // 展示开的状态
                setBackgroundRes(R.mipmap.ic_switch_background_open);
                canvas.drawBitmap(slideButtonBitmap, backgroundBitmap.getWidth() - slideButtonBitmap.getWidth(), 0, null);
            } else {// 展示关的状态
                setBackgroundRes(R.mipmap.ic_switch_background_close);
                canvas.drawBitmap(slideButtonBitmap, 0, 0, null);
            }
        }
    }

    public void setBackgroundRes(int backgroundRes) {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), backgroundRes);
    }

    public void setSlideButtonRes(int slideButtonRes) {
        slideButtonBitmap = BitmapFactory.decodeResource(getResources(), slideButtonRes);
    }

    // 提供方法设置当前开关状态
    public void setCurrentState(boolean state) {
        currentState = state;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouching = true;// 当前处于滑动状态
                currentX = (int) event.getX();// 获取相对于自己的x坐标
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouching = false;// 当前不处于滑动状态
                currentX = (int) event.getX();

                // 手指抬起时，根据当前手指的位置与背景图片宽度的一半进行比较，
                // 如果手指的位置大于背景图片宽度的一半，应该让滑动块画到最右边，展示开的状态
                // 如果手指的位置小于背景图片宽度的一半，应该让滑动块画到最左边，展示关的状态
                boolean tempState = currentX > backgroundBitmap.getWidth() / 2;
                // 当状态发生变化时，调用外界监听器的onStateChanged
                if (tempState != currentState) {
                    currentState = tempState;
                    if (mListener != null) {
                        mListener.onStateChanged(currentState);
                    }
                }
                break;

            default:
                break;
        }
        invalidate();
//		postInvalidate();// 在子线程中使用
        return true;
    }

    public interface OnMyToggleButtonStateChangedListener {
        void onStateChanged(boolean state);
    }

    public void setOnMyToggleButtonStateChangedListener(OnMyToggleButtonStateChangedListener listener) {
        this.mListener = listener;
    }
}
