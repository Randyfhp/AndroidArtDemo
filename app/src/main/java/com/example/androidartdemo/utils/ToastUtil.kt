package com.example.androidartdemo.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

class ToastUtil constructor(context: Context) {

    private val mToast: Toast = Toast(context)

    fun s(s: CharSequence?): Toast {
        return mToast.apply {
            cancel()
            setText(s)
            duration = Toast.LENGTH_SHORT
        }
    }

    fun s(@StringRes id:  Int): Toast {
        return mToast.apply {
            cancel()
            setText(id)
            duration = Toast.LENGTH_SHORT
        }
    }

    fun l(s: CharSequence?): Toast {
        return mToast.apply {
            cancel()
            setText(s)
            duration = Toast.LENGTH_LONG
        }
    }

    fun l(@StringRes id:  Int): Toast {
        return mToast.apply {
            cancel()
            setText(id)
            duration = Toast.LENGTH_LONG
        }
    }
}