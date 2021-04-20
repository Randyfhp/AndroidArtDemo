package com.example.androidartdemo.activity

import android.content.Intent
import android.os.Bundle
import com.example.androidartdemo.activity.view.ScrollConflictActivity
import com.example.androidartdemo.activity.view.ViewScrollerActivity
import com.example.androidartdemo.databinding.ActivityViewMainBinding
import com.example.module_base.base.activity.BaseActivity

class ViewMainActivity : BaseActivity() {

    private lateinit var mRootViewBinding: ActivityViewMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivityViewMainBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initView()
    }

    private fun initView() {
        mRootViewBinding.apply {
            scrollerView.setOnClickListener {
                startActivity(Intent().apply {
                    setClass(this@ViewMainActivity, ViewScrollerActivity::class.java)
                })
            }
            scrollConflictView.setOnClickListener {
                startActivity(Intent().apply {
                    setClass(this@ViewMainActivity, ScrollConflictActivity::class.java)
                })
            }
        }
    }
}