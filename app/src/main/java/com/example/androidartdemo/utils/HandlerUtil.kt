package com.example.androidartdemo.utils

import android.os.Looper
import android.util.Log

object HandlerUtil {

    fun clearThreadLocalLoop() {
        val field = Looper::class.java.getDeclaredField("sThreadLocal")
        Log.d("FHP", "${field.name}${field.type}")
        (field.get(null) as? ThreadLocal<Looper>)?.set(null)
    }
}