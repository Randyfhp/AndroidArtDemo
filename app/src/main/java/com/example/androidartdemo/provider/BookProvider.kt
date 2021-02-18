package com.example.androidartdemo.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

class BookProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Log.d("FHP", "delete current thread ${Thread.currentThread()}")
        // TODO("Implement this to handle requests to delete one or more rows")
        return 0
    }

    override fun getType(uri: Uri): String? {
        Log.d("FHP", "getType current thread ${Thread.currentThread()}")
        //TODO(
        //    "Implement this to handle requests for the MIME type of the data" +
        //            "at the given URI"
        //)
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("FHP", "insert current thread ${Thread.currentThread()}")
        // TODO("Implement this to handle requests to insert a new row.")
        return null
    }

    override fun onCreate(): Boolean {
        val packages = callingPackage
        Log.d("FHP", "onCreate callingPackage $callingPackage")
        Log.d("FHP", "onCreate thread ${Thread.currentThread()}")
        return false
        //TODO("Implement this to initialize your content provider on startup.")
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        Log.d("FHP", "query  thread ${Thread.currentThread()}")
        // TODO("Implement this to handle query requests from clients.")
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        Log.d("FHP", "update thread ${Thread.currentThread()}")
        // TODO("Implement this to handle requests to update one or more rows.")
        return 0
    }
}