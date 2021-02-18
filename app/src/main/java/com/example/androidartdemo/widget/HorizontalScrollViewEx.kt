package com.example.androidartdemo.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class HorizontalScrollViewEx @JvmOverloads constructor(context: Context,
                                                       attributeSet: AttributeSet? = null,
                                                       styleAttr: Int = 0) : ViewGroup(context, attributeSet, styleAttr) {

    companion object {
        private const val TAG = "HorizontalScrollViewEx"
    }

    private val mScroller = Scroller(context)
    private val mVelocityTracker = VelocityTracker.obtain()

    var alwaysEnableIntercept: Boolean = true

    private var mChildWidth = 0
    private var mChildIndex = 0

    // 分别记录上次滑动的坐标
    private var mLastX = 0
    private var mLastY = 0

    // 分别记录上次拦截的坐标
    private var mLastXIntercept = 0
    private var mLastYIntercept = 0

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var intercept = false
        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                intercept = false
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                    intercept = true
                }
                return intercept
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = ev.x - mLastXIntercept
                val deltaY = ev.y - mLastYIntercept
                intercept = (abs(deltaX) > abs(deltaY))
            }
            MotionEvent.ACTION_UP -> {
                intercept = false
            }
        }
        Log.d(TAG, "action = ${ev?.action} intercept = $intercept")
        mLastX = ev?.x?.toInt() ?: 0
        mLastY = ev?.y?.toInt() ?: 0
        mLastXIntercept = mLastX
        mLastYIntercept = mLastY

        return if (alwaysEnableIntercept) true else intercept
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent = ${event?.action}")
        mVelocityTracker.addMovement(event)
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                    Log.d(TAG, "onTouchEvent abortAnimation")
                }
            }
            MotionEvent.ACTION_MOVE -> {
                scrollBy(-(event.x - mLastX).toInt(), 0)
            }
            MotionEvent.ACTION_UP -> {
                val scrollToChildIndex = scrollX / mChildWidth
                mVelocityTracker.computeCurrentVelocity(1000)
                val xVel = mVelocityTracker.xVelocity

                //Log.d("FHP", "xVel = $xVel")
                mChildIndex = if (abs(xVel) >= 50) {
                    if (xVel > 0) mChildIndex - 1 else mChildIndex + 1
                } else {
                    (scrollX + mChildWidth / 2) / mChildWidth
                }
                mChildIndex = max(0, min(mChildIndex, childCount - 1))
                Log.d("FHP", "mChildIndex = $mChildIndex")
                val dx = mChildIndex * mChildWidth - scrollX
                //Log.d("FHP", "scrollX = $scrollX")
                smoothScrollXBy(dx)
                mVelocityTracker.clear()
            }
        }

        mLastX = event?.x?.toInt() ?: 0
        mLastY = event?.y?.toInt() ?: 0

        return true
    }

    private fun smoothScrollXBy(dx: Int) {
        mScroller.startScroll(scrollX, 0, dx, 0, 500)
        invalidate()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidate()
        }
    }

    override fun onDetachedFromWindow() {
        mVelocityTracker.recycle()
        super.onDetachedFromWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        var measureWidth = 0
        var measureHeight = 0

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        if (childCount == 0) {
            setMeasuredDimension(measureWidth, measureHeight)
        } else {
            val childView = getChildAt(0)
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                measureWidth = childView.measuredWidth * childCount
                measureHeight = childView.measuredHeight
                setMeasuredDimension(measureWidth, measureHeight)
            } else if (widthSpecMode == MeasureSpec.AT_MOST) {
                measureWidth = childView.measuredWidth * childCount
                setMeasuredDimension(measureWidth, heightSpecSize)
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                measureHeight = childView.measuredHeight
                setMeasuredDimension(widthSpecSize, measureHeight)
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var childLeft = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i) ?: continue
            mChildWidth = child.measuredWidth
            if (child.visibility != View.GONE) {
                child.layout(childLeft, t, childLeft + mChildWidth, t + child.measuredHeight)
                childLeft += mChildWidth
            }
        }
    }
}