package com.example.androidartdemo.window.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Button
import com.example.androidartdemo.databinding.ActivityWindowManagerBinding
import com.example.androidartdemo.utils.ToastUtil
import com.example.androidartdemo.window.view.WindowDraftView

class WindowManagerActivity : AppCompatActivity() {

    private lateinit var mViewRootBinding: ActivityWindowManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewRootBinding = ActivityWindowManagerBinding.inflate(layoutInflater)
        setContentView(mViewRootBinding.root)

        requestWindowAddViewPermission()
        initView()
    }

    private fun initView() {
        mViewRootBinding.apply {
            button1.setOnClickListener {
                ToastUtil(this@WindowManagerActivity).s("button1按下").show()
            }
            button2.setOnClickListener {
                ToastUtil(this@WindowManagerActivity).s("button2按下").show()
            }
        }

        addContentView(Button(this).apply { text = "加在activity的content" },
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))

    }

    private fun addWindow(view: View) {
        WindowDraftView(this).apply {
            text = "draftview"
            setOnClickListener { Log.d("FHP", "onClick") }
            setOnTouchListener { v, event ->
                //Log.d("FHP", "$v -> ${event.action} -> [${event.rawX}, ${event.rawY}]")
                return@setOnTouchListener false
            }
            setBackgroundColor(Color.DKGRAY)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun requestWindowAddViewPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")), 0)
            } else {
                addWindow(Button(this).apply { text = "加在window上" })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == 0) {
                if (!Settings.canDrawOverlays(this)) {
                    ToastUtil(this).s("没获得权限")
                } else {
                    addWindow(Button(this).apply { text = "加在window上" })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}