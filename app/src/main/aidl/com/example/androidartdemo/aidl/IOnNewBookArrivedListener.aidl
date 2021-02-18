// IOnNewBookArrivedListener.aidl
package com.example.androidartdemo.aidl;

// Declare any non-default types here with import statements
import com.example.androidartdemo.aidl.Book;

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrivedListener(in Book newBook);
}