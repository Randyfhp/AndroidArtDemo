package com.example.module_base.base.activity

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry




class BaseCommonActivity : Activity(), LifecycleOwner {

    private var mLifecycleRegistry: LifecycleRegistry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry?.currentState = Lifecycle.State.CREATED
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry!!
    }

}