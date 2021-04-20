package com.example.module_base.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.example.module_base.databinding.DialogBaseCommonLayoutBinding

object DialogUtil {
    fun showRechargeAlertDialog(context: Context,
                                title: String?,
                                type: Int,
                                config: DialogConfig?): AlertDialog {
        val binding = DialogBaseCommonLayoutBinding.inflate(LayoutInflater.from(context));
        val alertDialog = createAlertDialog(context, binding.root, config);
        return alertDialog
    }

    //创建共用的dialog的属性
    fun createAlertDialog(context: Context, view: View, config: DialogConfig?): AlertDialog {
        val builder = AlertDialog.Builder(context);
        builder.setView(view);
        config?.onConfig(builder)
        val catDialog = builder.show();
        catDialog.window?.setBackgroundDrawable(null);
        catDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        val lp = catDialog.window?.attributes;
        ScreenUtil.screenSize(context).let {
            lp?.width = it.width() //设置宽度
            lp?.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        catDialog.window?.attributes = lp;
        return catDialog;
    }

    interface DialogConfig {
        fun onConfig(builder: AlertDialog.Builder)
    }
}