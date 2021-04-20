package com.example.androidartdemo.network.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.module_base.manager.NetworkManger

class MainViewModel() : ViewModel() {

    constructor(tag: String) : this() {

    }

    private var mNetworkListener: NetworkManger? = null

    fun getNetworkListener(context: Context): NetworkManger {
        if (mNetworkListener == null) {
            mNetworkListener = NetworkManger.getInstance(context)
        }
        return mNetworkListener!!
    }


    class MainViewModelFactory(tag: String, application: Application) :
        ViewModelProvider.AndroidViewModelFactory(application){

        private val tag = tag

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(tag) as T
        }
    }

}