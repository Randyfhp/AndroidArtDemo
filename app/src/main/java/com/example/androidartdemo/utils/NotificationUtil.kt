package com.example.androidartdemo.utils

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationUtil {

    private var notificationManager: NotificationManager? = null
    val CHANNEL_IDS = arrayOf("id1", "id2")
    var CHANNELS = mapOf(
        CHANNEL_IDS[0] to "channelName0",
        CHANNEL_IDS[1] to "channelName1"
    )


    /**
     * setAutoCancel(boolean boolean)    设置点击通知后自动清除通知
    setContent(RemoteView view)       设置自定义通知
    setContentTitle(String string)    设置通知的标题内容
    setContentText(String string)    设置通知的正文内容
    setContentIntent(PendingIntent intent)    设置点击通知后的跳转意图
    setWhen(long when)        设置通知被创建的时间
    setSmallIcon(int icon)    设置通知的小图标  注意：只能使用纯alpha图层的图片进行设置，小图标会显示在系统状态栏上

    setLargeIcon(Bitmap icon)    设置通知的大图标    下拉系统状态栏时就能看见

    setPriority(int pri)    设置通知的重要程度
    (
    //默认的重要程度，和不设置效果是一样的
    public static final int PRIORITY_DEFAULT = 0;

    //最低的重要程度，系统可能只会在特定的场合显示这条通知
    public static final int PRIORITY_MIN = -2;

    //较低的重要程度，系统可能会将这类通知缩小，或改变其显示的顺序
    public static final int PRIORITY_LOW = -1;

    //较高的重要程度，系统可能会将这类通知放大，或改变其显示的顺序
    public static final int PRIORITY_HIGH = 1;

    //最高的重要程度，表示这类通知消息必须让用户看到，甚至做出响应
    public static final int PRIORITY_MAX = 2;
    )
    setStyle(Style style)    设置通知的样式    比如设置长文字、大图片等等

    setVisibility(int defaults)    设置默认
    setLight(int argb, int  onMs, int offMs)    设置呼吸闪烁效果
    setSound(Uri sound)            设置通知音效
    setVibrate(long[] pattern)     设置震动效果，数组包含手机静止时长和震动时长
    （下标0代表手机静止时长   下标1代表手机整的时长
    下标2代表手机静止时长    下标3，4，5.......以此类推
    还需要在AndroidManifest.xml中声明权限：
    <uses-permission android:name="android.permission.VIBRATE"/>）

    setColor(int argb)                设置通知栏颜色
    setCategory(String category)    设置通知类别
    setDefaults(int defaults)
    (
    setDefaults(NotificationCompat.DEFAULT_VIBRATE) ;    // 添加默认震动提醒

    setDefaults(NotificationCompat.DEFAULT_SOUND) ;     // 添加默认声音提醒

    setDefaults(NotificationCompat.DEFAULT_LIGHTS) ;     // 添加默认闪光灯提醒

    setDefaults(NotificationCompat.DEFAULT_ALL) ;           // 添加默认以上三种提醒
    )

     **/


    fun getManager(context: Context): NotificationManager {
        synchronized(this) {
            if (notificationManager == null) {
                notificationManager = context.getSystemService(NotificationManager::class.java)
            }
            return notificationManager!!
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun addChannel(context: Context?, channelId: String, channelName: String,
                   importance: Int = NotificationManager.IMPORTANCE_DEFAULT): String {
        if (context == null) { return channelId}
        getManager(context).createNotificationChannel(
            newNotificationChannel(channelId, channelName, importance))
        return channelId
    }


    inline fun addChannel(context: Context?, channelId: String, channelName: String?,
                          config: (NotificationChannel.() -> Unit) = {}): String {
        if (context == null) { return channelId}
        //判断是否为8.0以上：Build.VERSION_CODES.O为26 才能添加通知通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager(context).createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    config()
                })
        }
        return channelId
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun newNotificationChannel(channelId: String, channelName: String,
                                  importance: Int): NotificationChannel {
        return NotificationChannel(channelId, channelName, importance)
    }

    fun getConfig(channelId: String, channel: NotificationChannel?) {
        channel?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                when (channelId) {
                    CHANNEL_IDS[0] -> {
                        // 设置描述 最长30字符
                        description = "这是通道一的通知信息"
                        // 呼吸灯
                        enableLights(true)
                        // 震动
                        enableVibration(true)
                        // 显示模式
                        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    }
                    CHANNEL_IDS[1] -> {

                    }
                }
            }
        }
    }

    fun cancelNotification(context: Context?, id: Int?) {
        if (context == null) { return }
        if (id == null) {
            getManager(context).cancelAll()
        } else {
            getManager(context).cancel(id)
        }
    }
}