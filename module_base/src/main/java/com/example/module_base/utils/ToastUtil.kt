package com.example.module_base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.example.module_base.R

@SuppressLint("ShowToast")
class ToastUtil(context: Context?)  {

    private var mToast: Toast? = null
    private val mToastMargin =  context?.resources?.getDimensionPixelSize(R.dimen.toast_custom_margin) ?: 0

    companion object {
        fun CREATOR (context: Context?) = ToastUtil(context)
    }

    init {
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }


    private fun getString(context: Context?, @StringRes id: Int): String {
        return context?.getString(id) ?: ""
    }

    
    fun showToastWithImg(context: Context?, msg: String?, imageResource: Int): Toast? {
        context?.run {
            if (mToast == null) {
                mToast = Toast(context)
            }
            val view: View = LayoutInflater.from(this).inflate(R.layout.toast_custom_image_layout, null)
            val tv: TextView = view.findViewById(R.id.toast_custom_tv) as TextView
            tv.text = if (TextUtils.isEmpty(msg)) "" else msg
            val iv: ImageView = view.findViewById(R.id.toast_custom_iv) as ImageView
            if (imageResource > 0) {
                iv.visibility = View.VISIBLE
                iv.setImageResource(imageResource)
            } else {
                iv.visibility = View.GONE
            }
            mToast?.view = view
            mToast?.setGravity(Gravity.CENTER, 0, 0)
            mToast?.show()
        }
        return mToast
    }


    fun showToastLayout(context: Context?, @LayoutRes toastLayout: Int, gravity: Int): Toast?  {
        context?.run {
            if (mToast == null) {
                mToast = Toast(this)
            }
            val view = LayoutInflater.from(this).inflate(toastLayout, null)
            mToast?.view = view
            mToast?.setGravity(gravity, 0, 0)
            mToast?.show()
        }

        return mToast
    }

    fun show(msg: String?) {
        showToast(msg, Toast.LENGTH_SHORT, Gravity.BOTTOM)
    }

    fun showLong(msg: String?) {
        showToast(msg, Toast.LENGTH_LONG, Gravity.BOTTOM)
    }

    fun showTop(msg: String?) {
        showToast(msg, Toast.LENGTH_SHORT, Gravity.TOP)
    }

    fun showTopLong(msg: String?) {
        showToast(msg, Toast.LENGTH_LONG, Gravity.TOP)
    }

    fun showCenter(msg: String?) {
        showToast(msg, Toast.LENGTH_SHORT, Gravity.CENTER)
    }

    fun showCenterLong(msg: String?) {
        showToast(msg, Toast.LENGTH_LONG, Gravity.CENTER)
    }

    @SuppressLint("ShowToast")
    private fun showToast(str: String?, showTime: Int, gravity: Int)  {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Handler(Looper.getMainLooper()).post {
                showToastOnMain(str, showTime, gravity)
            }
        } else {
            showToastOnMain(str, showTime, gravity)
        }
    }

    private fun showToastOnMain(str: String?, showTime: Int, gravity: Int) {
        when (gravity) {
            Gravity.TOP -> {
                mToast?.setGravity(gravity, 0, mToastMargin)
            }
            Gravity.CENTER -> {
                mToast?.setGravity(gravity, 0, 0)
            }
            Gravity.BOTTOM -> {
                mToast?.setGravity(gravity, 0, mToastMargin)
            }
        }
        mToast?.setText(str)
        mToast?.show()
    }

    fun cancel() {
        mToast?.cancel()
    }


}