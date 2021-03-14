package com.example.module_ndk.file

class NativeFileOperationKt {

    companion object {

        private const val LIB_NAME = "fileOperation"

        init {
            System.loadLibrary(LIB_NAME)
        }
    }

    external fun write(text: String, size: Int): Boolean

    external fun read(size: Int): String
}