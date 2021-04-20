package com.example.androidartdemo.window.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import com.example.module_base.utils.ScreenUtil

class WindowDraftView @JvmOverloads constructor(context: Context,
                                                attr: AttributeSet? = null,
                                                defStyleRes: Int = 0) :
        AppCompatButton(context, attr, defStyleRes), View.OnTouchListener, View.OnClickListener {

    private val  STATUS_BAR_HEIGHT: Int = ScreenUtil.statusBarHeight(context)
    private var mWindowManager: WindowManager? = null
    private val mWindowLayoutParams = WindowManager.LayoutParams()
    private var isExpand = false
    private var mTouchListener: OnTouchListener? = null
    private var mClickListener: OnClickListener? = null

    init {
        setOnTouchListener(null)
        setOnClickListener(null)
        mWindowLayoutParams.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.START or Gravity.TOP
            type = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
            }
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        }
        addViewToWindow()
    }

    private fun addViewToWindow() {
        if (context is Activity) {
            (context as Activity).run {
                windowManager.addView(this@WindowDraftView, mWindowLayoutParams)
                mWindowManager = windowManager
            }
        }
    }

    fun setIsExpand(expand: Boolean) {
        isExpand = expand
    }

    fun getIseExpand(): Boolean {
        return isExpand
    }



    @SuppressLint("ClickableViewAccessibility")
    override fun setOnTouchListener(l: OnTouchListener?) {
        mTouchListener = l
        super.setOnTouchListener(this)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l
        super.setOnClickListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val rawX = (event?.rawX?.toInt() ?: 0)
        val rawY = (event?.rawY?.toInt() ?: 0) - STATUS_BAR_HEIGHT
        when(event?.action) {

            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                mWindowLayoutParams.x = rawX - width / 2
                mWindowLayoutParams.y = rawY - height / 2
                mWindowManager?.updateViewLayout(this, mWindowLayoutParams)
            }
        }
        mTouchListener?.onTouch(v, event)
        return false
    }

    override fun onClick(v: View?) {
        isExpand = !isExpand
        mClickListener?.onClick(v)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mWindowManager = null
    }

    fun onDestroy() {
        mWindowManager?.removeView(this)
    }
}