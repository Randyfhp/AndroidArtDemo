package com.example.androidartdemo.activity

import android.net.Uri
import android.os.Bundle
import com.example.androidartdemo.databinding.ActivityBookProviderBinding
import com.example.module_base.base.activity.BaseActivity

class BookProviderActivity : BaseActivity() {

    private lateinit var mRootViewBinding: ActivityBookProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivityBookProviderBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initView()
        initData()
    }

    private fun initView() {
        mRootViewBinding.providerContentText.text = "无"
    }

    private fun initData() {
        val bookContentPD = Uri.parse("content://com.example.androidproject.book.provider")
        contentResolver.query(bookContentPD, null, null, null, null)?.close()
        contentResolver.query(bookContentPD, null, null, null, null)?.close()
        contentResolver.query(bookContentPD, null, null, null, null)?.close()
    }

}