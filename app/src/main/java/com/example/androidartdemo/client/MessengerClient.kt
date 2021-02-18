package com.example.androidartdemo.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.example.androidartdemo.server.MessengerServer
import com.example.androidartdemo.server.MessengerServer.MessengerHandler.Companion.MSG_FROM_SERVICE
import java.lang.ref.WeakReference

class MessengerClient(context: Context?) : ServiceConnection {

    private val mWContext = WeakReference<Context>(context)
    private var mService: Messenger? = null
    private var mListener: OnServerConnectListener? = null
    private val mClientMessenger: Messenger = Messenger(MessengerHandler())

    init {
        val intent = Intent(context, MessengerServer::class.java)
        context?.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        if (mService == null) {
            mService = Messenger(service)
        }
        mListener?.connected()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        mListener?.disConnect()
    }

    fun setMessenger(bundle: Bundle?) {
        val msg = Message.obtain(null, MessengerServer.MessengerHandler.MSG_FROM_CLIENT)
        bundle?.let {
            msg.data = it
        }
        msg.replyTo = mClientMessenger
        try {
            mService?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    fun onDestroy() {
        mWContext.get()?.let {
            it.unbindService(this)
        }
        mListener = null
    }

    fun setServiceListener(l: OnServerConnectListener?) {
        mListener = l
    }

    companion object {
        open class OnServerConnectAdapter(context: Context?) : OnServerConnectListener {

            private val mContext = WeakReference<Context>(context)

            override fun connected() {
                connected(mContext.get())
            }

            override fun disConnect() {
                disConnect(mContext.get())
            }

            open fun connected(context: Context?) {

            }

            open fun disConnect(context: Context?) {

            }
        }

        private class MessengerHandler : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when(msg.what) {
                    MSG_FROM_SERVICE -> {
                        Log.d("FHP", "${msg.data.getString("reply")}")
                    }
                    else -> {}
                }
            }
        }
    }

    interface OnServerConnectListener {

        fun connected()

        fun disConnect()
    }
}