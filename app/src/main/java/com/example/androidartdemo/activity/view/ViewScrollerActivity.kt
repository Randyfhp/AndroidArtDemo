package com.example.androidartdemo.activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidartdemo.databinding.ActivityViewScrollerBinding
import com.example.androidartdemo.utils.ScreenUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewScrollerActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var mRootViewBinding: ActivityViewScrollerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivityViewScrollerBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initView()
    }

    private fun initView() {
        mRootViewBinding.apply {
            launch {
                delay(1000)
                baseScrollLayout.smoothScrollTo(
                    ScreenUtil.dp2px(this@ViewScrollerActivity,-100f),
                    ScreenUtil.dp2px(this@ViewScrollerActivity,-100f))
                delay(1000)
                baseScrollLayout.smoothScrollTo(
                    ScreenUtil.dp2px(this@ViewScrollerActivity,-200f),
                    ScreenUtil.dp2px(this@ViewScrollerActivity,-200f))
                delay(1000)
                baseScrollLayout.smoothScrollTo(
                    ScreenUtil.dp2px(this@ViewScrollerActivity,-300f),
                    ScreenUtil.dp2px(this@ViewScrollerActivity,-300f))
            }
        }
    }
}