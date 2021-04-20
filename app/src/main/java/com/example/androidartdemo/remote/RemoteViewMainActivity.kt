package com.example.androidartdemo.remote

import android.content.Intent
import android.os.Bundle
import com.example.androidartdemo.databinding.ActivityRemoteViewMainBinding
import com.example.androidartdemo.remote.activity.RemoteNotificationActivity
import com.example.module_base.base.activity.BaseActivity

class RemoteViewMainActivity : BaseActivity() {

    private lateinit var mViewRootBinding: ActivityRemoteViewMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewRootBinding = ActivityRemoteViewMainBinding.inflate(layoutInflater)
        setContentView(mViewRootBinding.root)

        initView()
    }

    private fun initView() {
        mViewRootBinding.apply {
            jumpNotification.setOnClickListener {
                startActivity(Intent().apply {
                    setClass(this@RemoteViewMainActivity, RemoteNotificationActivity::class.java)
                })
            }
        }
    }
}