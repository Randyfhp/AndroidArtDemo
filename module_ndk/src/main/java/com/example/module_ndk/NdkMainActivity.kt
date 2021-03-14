package com.example.module_ndk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.module_ndk.databinding.ActivityNdkMainBinding
import com.example.module_ndk.file.NativeFileOperation

class NdkMainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityNdkMainBinding
    private lateinit var mFileOp: NativeFileOperation

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        mViewBinding = ActivityNdkMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
        mFileOp = NativeFileOperation(this)

        initView()
    }

    private fun initView() {
        mViewBinding.ndkButton.setOnClickListener {
            mViewBinding.ndkEditText.text.let {
                mFileOp.write(it.toString(), it.toString().length)
            }

            mViewBinding.ndkContent.text = mFileOp.read(100)
        }

        mViewBinding.ndkContent.text = mFileOp.read(100)
    }
}