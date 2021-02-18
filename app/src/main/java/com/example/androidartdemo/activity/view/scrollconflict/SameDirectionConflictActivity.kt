package com.example.androidartdemo.activity.view.scrollconflict

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidartdemo.databinding.ActivitySameDirectionConflictBinding

class SameDirectionConflictActivity : AppCompatActivity() {

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