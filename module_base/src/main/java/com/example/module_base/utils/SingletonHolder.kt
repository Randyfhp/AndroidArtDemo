package com.example.module_base.utils

open class SingletonHolder<in T, out R>(private var creator: ((T) -> R)?) {

    @Volatile
    private var instance: R? = null

    fun getInstance(arg: T): R  = instance ?: synchronized(this) {
        instance ?: creator?.invoke(arg)?.apply {
            instance = this
            creator = null
        }!!
    }
}