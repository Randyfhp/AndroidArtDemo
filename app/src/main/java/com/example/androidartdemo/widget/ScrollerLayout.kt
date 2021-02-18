package com.example.androidartdemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.widget.LinearLayout
import android.widget.Scroller

open class ScrollerLayout @JvmOverloads constructor(context: Context,
                                                    attributeSet: AttributeSet? = null,
                                                    defStyleAttr: Int = 0) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val mScroller = Scroller(context)
    private val mVelocityTracker = VelocityTracker.obtain()

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidate()
        }
    }

    open fun smoothScrollTo(finalX: Int, finalY: Int) {
        mScroller.startScroll(scrollX, scrollY, finalX - scrollX, finalY - scrollY)
        invalidate()
    }

    open fun smoothScrollBy(deltaX: Int, deltaY: Int) {
        smoothScrollTo(scrollX + deltaX, scrollY + deltaY)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mVelocityTracker.addMovement(event)
        Log.d("FHP", "action = ${event?.action}")
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                smoothScrollTo(-event.x.toInt(), -event.y.toInt())
            }
            MotionEvent.ACTION_MOVE -> {
                scrollTo(-event.x.toInt(), -event.y.toInt())
            }
            MotionEvent.ACTION_UP -> {
                mVelocityTracker.computeCurrentVelocity(1000)
                Log.d("FHP", "Vx = ${mVelocityTracker.xVelocity}, Vy = ${mVelocityTracker.yVelocity}")
            }
            else -> {}
        }

        return true

    }
}