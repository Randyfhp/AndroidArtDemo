package com.example.androidartdemo.remote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidartdemo.databinding.ActivityRemoteViewMainBinding
import com.example.androidartdemo.remote.activity.RemoteNotificationActivity

class RemoteViewMainActivity : AppCompatActivity() {

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