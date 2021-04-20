package com.example.androidartdemo.activity.view

import android.os.Bundle
import com.example.androidartdemo.databinding.ActivityViewScrollerBinding
import com.example.module_base.utils.ScreenUtil
import com.example.module_base.base.activity.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewScrollerActivity : BaseActivity(), CoroutineScope by MainScope() {

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