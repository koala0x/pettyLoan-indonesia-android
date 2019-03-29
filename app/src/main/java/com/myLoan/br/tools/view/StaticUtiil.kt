package com.myLoan.br.tools.view

import android.content.res.Resources
import androidx.core.graphics.drawable.DrawableCompat
import android.widget.ImageView

class StaticUtiil {
    companion object {
        fun changeImageViewColor(imageView: ImageView, color: Int, resources: Resources) {
            DrawableCompat.setTint(imageView.drawable, resources.getColor(color))
            imageView.setImageDrawable(imageView.drawable)
        }
    }
}