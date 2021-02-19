package com.example.androidartdemo.remote.receiver

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.example.androidartdemo.R
import com.example.androidartdemo.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RemoteAppWidgetProvider : AppWidgetProvider() {

    companion object {
        const val TAG = "RemoteAppWidgetProvider"
        const val REMOTE_CLICK_ACTION = "com.example.androidartdemo.remote.receiver.action.CLICK"
    }

    private fun rotateBitmap(context: Context?, bitmap: Bitmap?, degree: Float): Bitmap? {
        if (context == null) { return null }
        val matrix = Matrix().apply {
            reset()
            setRotate(degree)
        }
        Log.d(TAG,"1${bitmap?.width} : ${bitmap?.height}")
        return bitmap?.let {
            Log.d(TAG,"2${it.width} : ${it.height}")
            Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true).also { newBitmap ->
                Log.d(TAG,"3${newBitmap.width} : ${newBitmap.height}")
            }
        }
    }

    private fun getPendingIntent(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.apply {
               component = ComponentName(context, RemoteAppWidgetProvider::class.java)
            }
        }

        return PendingIntent.getBroadcast(context, requestCode, intent, flags)
    }

    private fun doOnRemoteClick(context: Context?) {
        if (context == null) { return }
        ToastUtil(context).s("clicked it").show()

        GlobalScope.launch(Dispatchers.Main) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val srcBitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
            for (i in 0 until 37) {
                val degree = (i * 10) % 360F
                val remoteViews = RemoteViews(context.packageName, R.layout.remote_widget_desk_layout).apply {
                    val intent = Intent().apply {
                        action = REMOTE_CLICK_ACTION
                    }

                    val pendingIntent = getPendingIntent(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                    setImageViewBitmap(R.id.remoteWidgetImageView, rotateBitmap(context, srcBitmap, degree))
                    setTextViewText(R.id.remoteWidgetTextView, "度数$i°")
                    //setTextColor(R.id.remoteWidgetTextView, Color.YELLOW)
                    setOnClickPendingIntent(R.id.remoteWidgetImageView, pendingIntent)
                }
                val componentName = ComponentName(context, RemoteAppWidgetProvider::class.java)
                appWidgetManager.updateAppWidget(componentName, remoteViews)
                delay(30)
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d(TAG, "onReceive: action = ${intent?.action}")

        // 这里判断自己的action, 内置的action已经在super里面做了
        when (intent?.action) {
            REMOTE_CLICK_ACTION -> {
                doOnRemoteClick(context)
            }
        }
    }

    private fun onWidgetUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int) {
        if (context == null || appWidgetManager == null) { return }
        val remoteViews = RemoteViews(context.packageName, R.layout.remote_widget_desk_layout).apply {
            val intent = Intent().apply {
                action = REMOTE_CLICK_ACTION
            }

            val pendingIntent = getPendingIntent(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            setOnClickPendingIntent(R.id.remoteWidgetImageView, pendingIntent)
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(TAG, "onUpdate")

        val counter = appWidgetIds?.size ?: 0
        for (i in 0 until counter) {
            appWidgetIds?.get(i)?.let {
                onWidgetUpdate(context, appWidgetManager, it)
            }
        }
    }

}