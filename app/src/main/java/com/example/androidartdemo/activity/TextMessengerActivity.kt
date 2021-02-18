package com.example.androidartdemo.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidartdemo.MainActivity
import com.example.androidartdemo.client.MessengerClient
import com.example.androidartdemo.databinding.ActivityTextMessengerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class TextMessengerActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var mRootViewBinding: ActivityTextMessengerBinding
    private lateinit var msgManager: MessengerClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivityTextMessengerBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initData()
    }

    private fun initData() {
        msgManager = MessengerClient(this).apply {
            setServiceListener(object : MessengerClient.Companion.OnServerConnectAdapter(this@TextMessengerActivity) {

                override fun connected(context: Context?) {
                    super.connected(context)
                    (context as? MainActivity)?.apply {
                        launch {
                            msgManager.setMessenger(Bundle().apply {
                                putString("msg", "hello world!")
                            })
                        }
                    }
                }

                override fun disConnect(context: Context?) {
                    super.disConnect(context)
                    (context as? MainActivity)?.apply {

                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        msgManager.onDestroy()
    }
}