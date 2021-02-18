package com.example.androidartdemo.aidl

import android.os.Parcel
import android.os.Parcelable

data class Book(var bookName: String? = null, var bookId: Int = 0) : Parcelable {

    private constructor(parcel: Parcel) : this() {
        bookName = parcel.readString()
        bookId = parcel.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(bookName)
        dest?.writeInt(bookId)
    }

    companion object CREATOR : Parcelable.Creator<Book> {

        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}