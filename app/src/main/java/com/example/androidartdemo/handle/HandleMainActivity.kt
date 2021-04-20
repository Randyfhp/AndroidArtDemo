package com.example.androidartdemo.handle

import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.example.androidartdemo.databinding.ActivityHandleMainBinding
import com.example.androidartdemo.handle.handlerAdapter.Handler1
import com.example.androidartdemo.utils.HandlerUtil
import com.example.module_base.base.activity.BaseActivity
import kotlinx.coroutines.*

class HandleMainActivity : BaseActivity(), CoroutineScope by MainScope() {

    private lateinit var mViewBindingRoot: ActivityHandleMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBindingRoot = ActivityHandleMainBinding.inflate(layoutInflater)
        setContentView(mViewBindingRoot.root)

        initView()
    }

    private fun initView() {
        mViewBindingRoot.delayLoadView.setOnDelayLoad(this::doOnDelayLoad)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.d("FHP", "doOnDelayLoad")
    }

    private fun doOnDelayLoad() {
        Log.d("FHP", "doOnDelayLoad")
        launch(Dispatchers.Default) {
            // 线程1
            Log.d(TAG, "==线程1 名字 = ${Thread.currentThread().name}")
            HandlerUtil.clearThreadLocalLoop()
            if (Looper.myLooper() == null) {
                Looper.prepare()
            } else {
                Log.d(TAG, "Looper 已经启动了,可能没退出成功")
            }
            Handler1().run {
                var success = post {
                    Log.d(TAG, "(1)${Thread.currentThread().name}")
                }
                Log.d(TAG, "1$success")
                success = sendMessage(obtainMessage(2, "(2)${Thread.currentThread().name}"))
                Log.d(TAG, "2$success")
/*                post {
                    Log.d(TAG, "(3)线程1 Looper quit")
                    *//*Looper.myLooper()?.quitSafely()*//*
                    Looper.myLooper()?.quit()
                }*/
                success = sendMessage(obtainMessage(3, "hello"))
                Log.d(TAG, "3$success")
            }

            Log.d(TAG, "==线程1 启动Looper")
            Looper.loop()
            Log.d(TAG, "==线程1 名字 = ${Thread.currentThread().name}退出Looper")
        }
/*        launch(Dispatchers.Default) {
            // 线程2
            Log.d(TAG, "线程2 名字 = ${Thread.currentThread().name}")
            if (Looper.myLooper() == null) {
                Looper.prepare()
            } else {
                Log.d(TAG, "Looper 已经启动了,可能没退出成功")
            }
            Handler1().run {
                post {
                    Log.d(TAG, Thread.currentThread().name)
                }
                sendMessage(obtainMessage(Thread.currentThread().id.toInt(), Thread.currentThread().name))
                Log.d(TAG, "线程2 Looper quitSafely")
                Looper.myLooper()?.quitSafely()
            }
            Log.d(TAG, "线程2 启动Looper")
            Looper.loop()
        }*/

    }


    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}