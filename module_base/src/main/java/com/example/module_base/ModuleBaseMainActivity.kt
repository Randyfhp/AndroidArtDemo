package com.example.module_base

import androidx.appcompat.app.AppCompatActivity

open class ModuleBaseMainActivity : AppCompatActivity() {

    companion object {
        public val TAG = "FHP_${ModuleBaseMainActivity::class.java.simpleName}"
    }

}