package com.example.androidartdemo.activity.view.scrollconflict

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.androidartdemo.R
import com.example.androidartdemo.databinding.ActivityDifferentDirectConflictBinding

class DifferentDirectConflictActivity : AppCompatActivity() {

    private lateinit var mRootViewBinding: ActivityDifferentDirectConflictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootViewBinding = ActivityDifferentDirectConflictBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding.root)

        initView()
        iniData()
    }

    private fun initView() {
        //mRootViewBinding.container1.alwaysEnableIntercept = true
    }

    private fun iniData() {
        initListData(mRootViewBinding.list1)
        initListData(mRootViewBinding.list2)
        initListData(mRootViewBinding.list3)
    }

    private fun initListData(view: ListView?) {
        if (view == null) { return }
        val data = mutableListOf<String>()
        for (i in 0 until 50) {
            data.add("name: $i")
        }
        view.adapter = ArrayAdapter(this, R.layout.scroll_conflict_content_list_item, R.id.itemName, data)
    }

}