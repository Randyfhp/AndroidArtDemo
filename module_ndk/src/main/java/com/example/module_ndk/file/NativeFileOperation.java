package com.example.module_ndk.file;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class NativeFileOperation {

    private static final String TAG = "NativeFileOperation";
    private static final String LIB_NAME = "fileOperation";
    private INativeFileOperation mListener;

    static {
        System.loadLibrary(LIB_NAME);
    }

    private File mFilePath = null;

    public String getFileRootPath() {
        return mFilePath.toString();
    }

    public String getFilePath() {
        return getFileRootPath() + File.separator + "file.txt";
    }

    public NativeFileOperation(Context context) {
        mFilePath = context.getFilesDir();
    }

    public boolean write(String text, int size) {
        return write(getFilePath(), text, size);
    }

    public String read(int size) {
        return read(getFilePath(), size);
    }

    public void setOnFileOperation(INativeFileOperation l) {
        mListener = l;
    }

    /**
     * so 回调（静态方法）
     */
    public static void callbackStatic() {
        Log.d(TAG, "callbackStatic");
    }

    /**
     * so 回调 （普通方法）
     * @param line
     */
    public void callback(String line) {
        if (mListener != null) {
            mListener.onGetLine(line);
        }
        Log.d(TAG, "callback->" + line);
    }

    public native boolean write(String path, String text, int size);


    public native String read(String path,  int size);
}
