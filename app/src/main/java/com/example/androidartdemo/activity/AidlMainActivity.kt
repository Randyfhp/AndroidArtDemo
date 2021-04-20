package com.example.androidartdemo.activity

import android.os.Bundle
import android.util.Log
import com.example.androidartdemo.aidl.Book
import com.example.androidartdemo.client.BookManagerClient
import com.example.androidartdemo.databinding.ActivityAidlMainBinding
import com.example.module_base.base.activity.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AidlMainActivity : BaseActivity(), CoroutineScope by MainScope() {

    private lateinit var mRootViewBinding: ActivityAidlMainBinding
    private lateinit var manager: BookManagerClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivityAidlMainBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initData()
    }

    private fun initData() {
        manager = BookManagerClient(this)
        launch {
            delay(1000)
            manager.addBook(Book("1", 1))
            manager.addBook(Book("2", 2))

            delay(2000)
            Log.d("FHP", "列表 = ${manager.getBookList()?.size ?: 0}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.onDestroy()
    }
}