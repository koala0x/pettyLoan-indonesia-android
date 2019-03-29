package com.myLoan.br.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.myLoan.br.R
import com.myLoan.br.tools.view.ScreenTools

class ProgressBeautifulView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        View(context, attributeSet, defStyleAttr) {
    private var rectF: RectF? = null
    private var rectFInner: RectF? = null
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintInner: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintText: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var widthSize: Int = 0
    private var heightSize: Int = 0
    private var strokeWid: Float = ScreenTools.dp2px(1f).toFloat()

    private var innerLength: Float = 0f
    public var nowPercent: Float = 0.1f
    public var nowNumber: String = "10%"

    init {
        paint.strokeCap = Paint.Cap.ROUND
        paint.isDither = true
        paint.strokeWidth = strokeWid
        paint.style = Paint.Style.STROKE
        paint.color = context.resources.getColor(R.color.popup_outline)

        paintInner.strokeCap = Paint.Cap.ROUND
        paintInner.isDither = true
        paintInner.strokeWidth = strokeWid
        paintInner.style = Paint.Style.FILL
        paintInner.color = context.resources.getColor(R.color.popup_inner)

        paintText.strokeCap = Paint.Cap.ROUND
        paintText.isDither = true
        paintText.textSize = ScreenTools.sp2px(14).toFloat()
        paintText.style = Paint.Style.FILL
        paintText.textAlign = Paint.Align.CENTER
        paintText.color = context.resources.getColor(R.color.main_loan_list)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthSize = w
        heightSize = h
        rectF = RectF(0f + strokeWid / 2f, 0f + strokeWid / 2f, w.toFloat() - strokeWid / 2f, h.toFloat() - strokeWid / 2f)
        innerLength = widthSize - ScreenTools.dp2px(6f).toFloat() * 2
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rectFInner = RectF(ScreenTools.dp2px(6f).toFloat(), ScreenTools.dp2px(8f).toFloat(), ScreenTools.dp2px(6f).toFloat() + innerLength * nowPercent, heightSize - ScreenTools.dp2px(8f).toFloat())
        canvas.drawRoundRect(rectFInner, ScreenTools.dp2px(10f).toFloat(), ScreenTools.dp2px(10f).toFloat(), paintInner)
        canvas.drawRoundRect(rectF, ScreenTools.dp2px(20f).toFloat(), ScreenTools.dp2px(20f).toFloat(), paint)

        var fm: Paint.FontMetrics = paintText.getFontMetrics();
        canvas.drawText(nowNumber.toString(), widthSize / 2f, heightSize / 2f - (fm.descent - (-fm.ascent + fm.descent) / 2), paintText)

    }
}