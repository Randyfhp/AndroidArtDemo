package com.example.module_base.base.lifecycle

import androidx.lifecycle.LifecycleEventObserver


interface ILifecycleObserver : LifecycleEventObserver {

    fun onCreate();
    fun onStart();
    fun onRestart();
    fun onResume();
    fun onPause();
    fun onStop();
    fun onDestroy();

}