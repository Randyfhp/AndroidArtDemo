package com.example.module_base.base.app

import android.app.Application

class BaseApplication : PerformanceMonitorApplication() {

    companion object {

        lateinit var mContext: BaseApplication

        inline fun getContext() : Application {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}