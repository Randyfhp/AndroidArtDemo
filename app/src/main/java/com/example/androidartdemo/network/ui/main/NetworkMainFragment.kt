package com.example.androidartdemo.network.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.androidartdemo.databinding.MainFragmentBinding
import com.example.module_base.base.app.BaseApplication
import com.example.module_base.base.fragment.BaseFragment

class NetworkMainFragment : BaseFragment() {

    companion object {
        fun newInstance() = NetworkMainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return MainFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, MainViewModel.MainViewModelFactory(TAG,
        BaseApplication.getContext())).get(MainViewModel::class.java)
        
    }

}