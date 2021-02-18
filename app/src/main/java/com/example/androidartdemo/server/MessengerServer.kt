package com.example.androidartdemo.server

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

class MessengerServer : Service() {


    private var mMessengerServer: Messenger? = null

    override fun onBind(intent: Intent?): IBinder? {
        if (mMessengerServer == null) {
            mMessengerServer = Messenger(MessengerHandler())
        }
        return mMessengerServer?.binder
    }

    companion object class MessengerHandler : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d("FHP", "msg what = ${msg.what}")
            when(msg.what) {
                MSG_FROM_CLIENT -> {
                    Log.d("FHP", "${msg.data.getString("msg")}")
                    reply(msg.replyTo, Bundle().apply {
                        putString("reply", "hello world too!")
                    })
                }
                else -> {}
            }
        }

        private fun reply(messenger: Messenger, bundle: Bundle?) {
            val replay = Message.obtain(null, MSG_FROM_SERVICE)
            bundle?.let {
                replay.data = it
            }
            try {
                messenger.send(replay)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }


        companion object {
            const val MSG_FROM_CLIENT = 0
            const val MSG_FROM_SERVICE = 1
        }
    }
}

