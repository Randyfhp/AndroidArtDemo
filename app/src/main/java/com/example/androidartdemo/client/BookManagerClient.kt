package com.example.androidartdemo.client

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.androidartdemo.aidl.Book
import com.example.androidartdemo.aidl.IBookManager
import com.example.androidartdemo.aidl.IOnNewBookArrivedListener
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

class BookManagerClient constructor(context: Context?) : ServiceConnection, IBinder.DeathRecipient,
    CoroutineScope by MainScope() {

    private val mWContext = WeakReference<Context>(context)
    private var mService: IBookManager? = null;
    private var mNewBookListener: OnNewBookListener? = null

    init {
        val intent = Intent("com.example.androidartdemo.bookmanager")
        intent.setPackage("com.example.androidartdemo")
        context?.bindService(intent, this, BIND_AUTO_CREATE)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        if (service == null) { return }
        if (mService == null) {
            mService = IBookManager.Stub.asInterface(service)
            mNewBookListener = OnNewBookListener(this)
        }
        mService?.registerListener(mNewBookListener)
        Log.d("FHP", "onServiceConnected")
        service.linkToDeath(this, 0)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d("FHP", "onServiceDisconnected")
    }

    fun getBookList(): List<Book>? {
        // 阻塞
        return mService?.bookList
    }

    fun addBook(book: Book?) {
        // 阻塞
        book?.let {
            mService?.addBook(it)
        }
    }

    private fun unregisterListener() {
        if (mService != null && mService!!.asBinder()!!.isBinderAlive) {
            mService!!.unregisterListener(mNewBookListener)
        }
    }

    fun onDestroy() {
        unregisterListener()
        mWContext.get()?.let {
            Log.d("FHP", "unbindService")
            it.unbindService(this)
        }
        cancel()
    }

    override fun binderDied() {
        Log.d("FHP", "binderDied")
        mService?.asBinder()?.unlinkToDeath(this, 0)
        mService = null
    }

    companion object {
        private class OnNewBookListener(client: BookManagerClient) : IOnNewBookArrivedListener.Stub() {

            private val mWClient = WeakReference(client)

            override fun onNewBookArrivedListener(newBook: Book?) {
                mWClient.get()?.launch(Dispatchers.Main) {
                    Log.d("FHP", "on add a new book id = ${newBook?.bookId}, name = ${newBook?.bookName}")
                }
            }

        }
    }
}