package com.example.module_base.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.example.module_base.utils.SingletonHolder


class NetworkManger private constructor(context: Context?) : LiveData<Int>() {

    companion object : SingletonHolder<Context, NetworkManger>(::NetworkManger)
    private val TAG = "NetworkManger"
    private val mContext: Context? = context?.applicationContext

    private var callback: NetworkCallback? = null
    private var request: NetworkRequest? = null

    private var mNetworkReceiver: NetworkReceiver? = null
    private var mIntentFilter: IntentFilter? = null

    interface State {
        companion object {
            const val NETWORK_NO_CONNECT = -1
            const val NETWORK_INVALID = -1
            const val NETWORK_MOBILE_VALID = 1
            const val NETWORK_WIFI_VALID = 2
            const val NETWORK_OTHER_VALID = 3

        }
    }

    init {
        registerNetworkCallback()
    }

    override fun onActive() {
        super.onActive()
        if (mContext == null) { return }
        val manager = ContextCompat.getSystemService(mContext, ConnectivityManager::class.java)
        request?.let {
            manager?.registerNetworkCallback(it, callback!!)
        }
        mIntentFilter?.let {
            mContext.registerReceiver(mNetworkReceiver, it)
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (mContext == null) { return }
        val manager = ContextCompat.getSystemService(mContext, ConnectivityManager::class.java)
        request?.let {
            manager?.unregisterNetworkCallback(callback!!)
        }
        mIntentFilter?.let {
            mContext.unregisterReceiver(mNetworkReceiver)
        }
    }

    private fun registerNetworkCallback() {
        //7.0及以后 使用这个新的api（7.0以前还是用静态注册广播）
        mContext?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val manager = ContextCompat.getSystemService(this, ConnectivityManager::class.java)
                // 请注意这里会有一个版本适配bug，所以请在这里添加非空判断
                manager?.let {
                    request = NetworkRequest.Builder().build()
                    callback = NetworkCallback()
                }
            } else {
                mNetworkReceiver = NetworkReceiver()
                mIntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private inner class NetworkCallback : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d(TAG, "onAvailable")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d(TAG, "onLost")
            if (mContext == null) {
                return
            }
            getInstance(mContext).postValue(State.NETWORK_NO_CONNECT)

        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (mContext == null) {
                return
            }
            networkCapabilities.let {
                Log.d(TAG, "")
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        getInstance(mContext).postValue(State.NETWORK_WIFI_VALID)
                    } else {
                        getInstance(mContext).postValue(State.NETWORK_MOBILE_VALID)
                    }
                } else {
                    getInstance(mContext).postValue(State.NETWORK_INVALID)
                }
            }
        }

    }


    private inner class NetworkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (mContext == null) {
                return
            }
            val manager = ContextCompat.getSystemService(mContext, ConnectivityManager::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return
            }
            val networkInfo = manager?.activeNetworkInfo;
            networkInfo?.run {
                when (type) {
                    ConnectivityManager.TYPE_WIFI -> {
                        getInstance(mContext).postValue(State.NETWORK_WIFI_VALID)
                    }
                    ConnectivityManager.TYPE_MOBILE -> {
                        getInstance(mContext).postValue(State.NETWORK_MOBILE_VALID)
                    }
                    else -> {
                        getInstance(mContext).postValue(State.NETWORK_OTHER_VALID)
                    }
                }
            }
            getInstance(mContext).postValue(State.NETWORK_INVALID)

        }
    }
}