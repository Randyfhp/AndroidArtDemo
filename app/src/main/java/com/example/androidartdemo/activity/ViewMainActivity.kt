package com.example.androidartdemo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidartdemo.activity.view.ScrollConflictActivity
import com.example.androidartdemo.activity.view.ViewScrollerActivity
import com.example.androidartdemo.databinding.ActivityViewMainBinding

class ViewMainActivity : AppCompatActivity() {

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