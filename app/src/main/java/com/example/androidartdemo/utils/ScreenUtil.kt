package com.example.androidartdemo.utils

import android.content.Context
import android.graphics.Rect

object ScreenUtil {

    fun dp2px(context: Context?, dp: Float): Int {
        if (context == null) { return 0 }
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun px2dp(context: Context?, px: Float): Int {
        if (context == null) { return 0 }
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun screenSize(context: Context?): Rect {
        val result = Rect()
        context?.apply {
            result.right = resources.displayMetrics.widthPixels
            result.bottom = resources.displayMetrics.heightPixels
        }
        return result
    }
}