package com.example.androidartdemo.remote.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidartdemo.databinding.ActivityNotificationShowBinding

class NotificationShowActivity : AppCompatActivity() {

    private lateinit var mViewRootBinding: ActivityNotificationShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewRootBinding = ActivityNotificationShowBinding.inflate(layoutInflater)
        setContentView(mViewRootBinding.root)
    }
}