package com.example.androidartdemo.activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidartdemo.activity.view.scrollconflict.DifferentDirectConflictActivity
import com.example.androidartdemo.databinding.ActivityScrollConflictBinding

class ScrollConflictActivity : AppCompatActivity() {

    private lateinit var mRootViewBinding: ActivityScrollConflictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivityScrollConflictBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initView()
        iniData()
    }

    private fun initView() {
        mRootViewBinding.let {
            it.differentDirectionConflict.setOnClickListener {
                startActivity(Intent().apply {
                    setClass(this@ScrollConflictActivity, DifferentDirectConflictActivity::class.java)
                })
            }
            it.sameDirectionConflict.setOnClickListener {
                startActivity(Intent().apply {
                    setClass(this@ScrollConflictActivity, DifferentDirectConflictActivity::class.java)
                })
            }
        }
    }

    private fun iniData() {

    }

}