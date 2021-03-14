package com.example.module_base.base

import android.content.Context

class BaseApplication : PerformanceMonitorApplication() {

    companion object {

        lateinit var mContext: BaseApplication

        inline fun getContext() : Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}