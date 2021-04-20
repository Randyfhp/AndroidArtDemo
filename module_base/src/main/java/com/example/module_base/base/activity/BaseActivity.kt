package com.example.module_base.base.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

open class BaseActivity : FragmentActivity(), LifecycleEventObserver {

    companion object {
        @JvmStatic
        public var TAG = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = javaClass::class.java.simpleName
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        val d = MediatorLiveData<Boolean>()
    }
}