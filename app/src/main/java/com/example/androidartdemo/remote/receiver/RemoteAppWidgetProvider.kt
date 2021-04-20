package com.example.androidartdemo.remote.receiver

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import com.example.androidartdemo.R
import com.example.module_base.utils.ToastUtil
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean


class RemoteAppWidgetProvider : AppWidgetProvider() {

    companion object {
        const val TAG = "RemoteAppWidgetProvider"
        const val REMOTE_CLICK_ACTION = "com.example.androidartdemo.remote.receiver.action.CLICK"
    }

    private val isAnimationFinished = AtomicBoolean(true)

    private fun rotateBitmap(context: Context?, bitmap: Bitmap?, degree: Float): Bitmap? {
        if (context == null) { return null }
        val matrix = Matrix().apply {
            reset()
            setRotate(degree)
        }
        return bitmap?.let {
            Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true)

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


    private fun getBitmap(context: Context?, vectorDrawableId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        if (context == null) { return null }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable = context.getDrawable(vectorDrawableId)
            bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth,
                    vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
            vectorDrawable.draw(canvas)
        } else {
            bitmap = BitmapFactory.decodeResource(context.resources, vectorDrawableId)
        }
        return bitmap
    }


    private fun doOnRemoteClick(context: Context?) {
        if (context == null) { return }
        ToastUtil(context).show("clicked it")
        if (!isAnimationFinished.get()) { return }
        isAnimationFinished.set(false)
        GlobalScope.launch(Dispatchers.Default) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val srcBitmap = getBitmap(context,  R.mipmap.ic_launcher)
            for (i in 0..360) {
                val degree = (i * 1) % 360F
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
                delay(10)
            }
            isAnimationFinished.set(true)
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