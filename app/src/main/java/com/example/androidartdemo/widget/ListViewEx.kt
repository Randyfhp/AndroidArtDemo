package com.example.androidartdemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ListView
import kotlin.math.abs

class ListViewEx @JvmOverloads constructor(context: Context,
                                           attributeSet: AttributeSet? = null,
                                           defStyleAttr: Int = 0) : ListView(context, attributeSet, defStyleAttr) {

    companion object {
        private const val TAG = "ListViewEx"
    }

    private var mLastX = 0
    private var mLastY = 0


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                //Log.d(TAG, "${parent::class.java.canonicalName}")
                parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = ev.x - mLastX
                val deltaY = ev.y - mLastY
                if (abs(deltaX) > abs(deltaY)) {
                    parent?.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        mLastX = ev?.x?.toInt() ?: 0
        mLastY = ev?.y?.toInt() ?: 0
        return super.dispatchTouchEvent(ev)
    }
}