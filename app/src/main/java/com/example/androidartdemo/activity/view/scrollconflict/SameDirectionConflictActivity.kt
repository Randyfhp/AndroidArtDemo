package com.example.androidartdemo.activity.view.scrollconflict

import android.os.Bundle
import com.example.androidartdemo.databinding.ActivitySameDirectionConflictBinding
import com.example.module_base.base.activity.BaseActivity

class SameDirectionConflictActivity : BaseActivity() {

    private lateinit var mRootViewBinding: ActivitySameDirectionConflictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivitySameDirectionConflictBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initView()
        initData()
    }

    private fun initView() {}

    private fun initData() {}
}