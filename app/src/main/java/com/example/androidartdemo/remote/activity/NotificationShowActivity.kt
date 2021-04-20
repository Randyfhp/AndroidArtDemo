package com.example.androidartdemo.remote.activity

import android.os.Bundle
import com.example.androidartdemo.databinding.ActivityNotificationShowBinding
import com.example.module_base.base.activity.BaseActivity

class NotificationShowActivity : BaseActivity() {

    private lateinit var mViewRootBinding: ActivityNotificationShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewRootBinding = ActivityNotificationShowBinding.inflate(layoutInflater)
        setContentView(mViewRootBinding.root)
    }
}