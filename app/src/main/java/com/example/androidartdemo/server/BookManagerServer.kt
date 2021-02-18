package com.example.androidartdemo.server

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import android.os.RemoteCallbackList
import android.util.Log
import com.example.androidartdemo.aidl.Book
import com.example.androidartdemo.aidl.IBookManager
import com.example.androidartdemo.aidl.IOnNewBookArrivedListener
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

class BookManagerServer : Service(), CoroutineScope by MainScope() {

    private var mStub: IBinder? = null
    private val mBooks: CopyOnWriteArrayList<Book> = CopyOnWriteArrayList()
    private var mIsServiceDestroy = AtomicBoolean(false)
    private val mListeners: RemoteCallbackList<IOnNewBookArrivedListener> = RemoteCallbackList()

    override fun onCreate() {
        super.onCreate()
        startAddBook()
    }



    override fun onBind(intent: Intent?): IBinder? {
        if (mStub == null) {
            mStub = BookManagerBinder(this)
        }
        Log.d("FHP", "BookManagerServer onBind")
        val check = checkCallingOrSelfPermission(
            "com.example.androidartdemo.permission.ACCESS_BOOK_SERVICE")
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.d("FHP", "BookManagerServer PERMISSION_DENIED")
            return null
        }
        return mStub;
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsServiceDestroy.set(true)
        cancel()
        Log.d("FHP", "BookManagerServer onDestroy")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("FHP", "BookManagerServer onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d("FHP", "BookManagerServer onRebind")
        super.onRebind(intent)
    }

    private fun startAddBook() {
        launch(Dispatchers.Unconfined) {
            while (!mIsServiceDestroy.get()) {
                delay(5000)
                (mStub as BookManagerBinder?)?.addBook(Book().apply {
                    bookId = mBooks.size + 1
                    bookName = "new book#${mBooks.size + 1}"
                })
            }
        }
    }

    private class BookManagerBinder(server: BookManagerServer) : IBookManager.Stub() {

        private val mServer = server

        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

        private fun onNewBookArrived(book: Book?) {
            book?.let {
                mServer.mBooks.add(it)
                for (i in  0 until  mServer.mListeners.beginBroadcast()) {
                    mServer.mListeners.getBroadcastItem(i).onNewBookArrivedListener(it)
                }
                mServer.mListeners.finishBroadcast()
            }
        }

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            // 验证权限

            // 验证包名
            val packages = mServer.packageManager.getPackagesForUid(getCallingUid())
            Log.d("FHP", Arrays.toString(packages))
            return super.onTransact(code, data, reply, flags)
        }

        override fun getBookList(): MutableList<Book> {
            return mServer.mBooks
        }

        override fun addBook(book: Book?) {
            book?.let {
                onNewBookArrived(it)
            }
        }

        private fun<T : IInterface?>  getListenerCnt(list: RemoteCallbackList<T>?): Int {
            return list?.run {
                val n = list.beginBroadcast()
                finishBroadcast()
                n
            } ?: 0
        }

        override fun registerListener(listener: IOnNewBookArrivedListener?) {
            if (!mServer.mListeners.register(listener)) {
                Log.d("FHP", "listener already exist")
                return
            }
            Log.d("FHP", "listener add success size = ${getListenerCnt(mServer.mListeners)}")
        }

        override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
            if (!mServer.mListeners.unregister(listener)) {
                Log.d("FHP", "listener not exist")
                return
            }
            Log.d("FHP", "listener remove success size = ${getListenerCnt(mServer.mListeners)}")
        }

    }
}