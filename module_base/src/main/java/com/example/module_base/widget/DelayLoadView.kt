package com.example.module_base.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class DelayLoadView @JvmOverloads constructor(context: Context?,
                                              attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mAction: Runnable? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mAction?.run {
            post(this)
        }
    }


    public fun setOnDelayLoad(action: Runnable) {
        mAction = action
    }
}