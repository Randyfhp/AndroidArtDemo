package com.example.androidartdemo.remote.activity

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.androidartdemo.R
import com.example.androidartdemo.databinding.ActivityRemoteNotificationBinding
import com.example.module_base.utils.NotificationUtil
import com.example.module_base.base.activity.BaseActivity

class RemoteNotificationActivity : BaseActivity() {

    private lateinit var mViewRootBinding: ActivityRemoteNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewRootBinding = ActivityRemoteNotificationBinding.inflate(layoutInflater)
        setContentView(mViewRootBinding.root)

        initView()
    }

    private fun initView() {
        mViewRootBinding.remoteSendNotification.setOnClickListener {
            val intent = Intent(this, NotificationShowActivity::class.java)
            val pendingIntent = PendingIntent.getActivities(this, 0,
                arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT)
            val notify = NotificationCompat.Builder(this,
                NotificationUtil.addChannel(this,
                    NotificationUtil.CHANNEL_IDS[0],
                    NotificationUtil.CHANNELS[NotificationUtil.CHANNEL_IDS[0]]){
                    NotificationUtil.getConfig(NotificationUtil.CHANNEL_IDS[0], this)
                })
                .setAutoCancel(true)
                .setContentTitle("收到聊天消息")
                .setContentText("今天晚上吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setTicker("ticker文本")
                .setDefaults(Notification.DEFAULT_ALL)
                .build()
            NotificationUtil.getManager(this).notify(1, notify)
        }
    }
}