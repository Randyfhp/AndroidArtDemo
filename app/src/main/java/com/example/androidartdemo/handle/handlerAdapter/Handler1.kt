package com.example.androidartdemo.handle.handlerAdapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log

class Handler1(looper: Looper = Looper.myLooper()!!, private val action: ((Message) -> Unit)? = null) : Handler(looper) {

    override fun dispatchMessage(msg: Message) {
        super.dispatchMessage(msg)

        Log.d("Handler1", msg.toString())

        action?.invoke(msg)
        if (TextUtils.equals(msg.obj as String? , "quite")) {
            Log.d("Handler1", "Looper.quite")
            Looper.myLooper()?.quit()
            removeCallbacksAndMessages(null)
        }
    }
}