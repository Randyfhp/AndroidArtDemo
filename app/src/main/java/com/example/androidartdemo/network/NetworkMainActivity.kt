package com.example.androidartdemo.network

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.androidartdemo.databinding.IncludeBottomViewBinding
import com.example.androidartdemo.databinding.NetworkMainActivityBinding
import com.example.androidartdemo.network.ui.main.MainViewModel
import com.example.androidartdemo.network.ui.main.NetworkMainFragment
import com.example.module_base.utils.ToastUtil
import com.example.module_base.base.activity.BaseActivity
import com.example.module_base.base.app.BaseApplication

class NetworkMainActivity : BaseActivity() {

    lateinit var mViewBinding: NetworkMainActivityBinding
    lateinit var mBottomViewBinding: IncludeBottomViewBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.MainViewModelFactory(TAG,
            BaseApplication.getContext())).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = NetworkMainActivityBinding.inflate(layoutInflater)
        mBottomViewBinding = IncludeBottomViewBinding.bind(mViewBinding.root)
        setContentView(mViewBinding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(mViewBinding.container.id, NetworkMainFragment.newInstance())
                    .commitNow()
        }
        initListener()
        initObserver()
    }

    private fun initListener() {
        mBottomViewBinding.run {
            bottomBtn.setOnClickListener {
                ToastUtil.CREATOR(this@NetworkMainActivity).show("on click!")
            }
        }
    }

    private fun initObserver() {
        viewModel.getNetworkListener(this).observe(this, {
            ToastUtil(this).show("网络状态切换$it")
        })
    }
}