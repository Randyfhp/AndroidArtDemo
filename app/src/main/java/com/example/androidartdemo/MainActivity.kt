package com.example.androidartdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidartdemo.activity.AidlMainActivity
import com.example.androidartdemo.activity.BookProviderActivity
import com.example.androidartdemo.activity.TextMessengerActivity
import com.example.androidartdemo.activity.ViewMainActivity
import com.example.androidartdemo.databinding.ActivityMainBinding
import com.example.androidartdemo.handle.HandleMainActivity
import com.example.androidartdemo.remote.RemoteViewMainActivity
import com.example.androidartdemo.window.activity.WindowManagerActivity
import com.example.module_ndk.NdkMainActivity
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var mRootViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initView()
    }

    private fun goto(clz: Class<*>) {
        startActivity(Intent().apply {
            setClass(this@MainActivity, clz)
        })
    }

    private fun initView() {
        mRootViewBinding.jumpBookAidl.setOnClickListener {
            goto(AidlMainActivity::class.java)
        }
        mRootViewBinding.jumpTextMessenger.setOnClickListener {
            goto(TextMessengerActivity::class.java)
        }
        mRootViewBinding.jumpBookProvider.setOnClickListener {
            goto(BookProviderActivity::class.java)
        }
        mRootViewBinding.jumpViewTest.setOnClickListener {
            goto(ViewMainActivity::class.java)
        }
        mRootViewBinding.jumpRemoteView.setOnClickListener {
            goto(RemoteViewMainActivity::class.java)
        }
        mRootViewBinding.jumpWindowManager.setOnClickListener {
            goto(WindowManagerActivity::class.java)
        }
        mRootViewBinding.jumpHandleTest.setOnClickListener {
            goto(HandleMainActivity::class.java)
        }
        mRootViewBinding.jumpNDKTest.setOnClickListener {
            goto(NdkMainActivity::class.java)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}