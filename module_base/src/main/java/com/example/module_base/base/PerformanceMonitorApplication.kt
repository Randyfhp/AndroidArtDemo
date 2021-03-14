package com.example.module_base.base

import android.app.Application
import com.example.module_base.base.performance.AppBlockCanaryContext
import com.github.moduth.blockcanary.BlockCanary

open class PerformanceMonitorApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        BlockCanary.install(this, AppBlockCanaryContext()).start()
    }

}