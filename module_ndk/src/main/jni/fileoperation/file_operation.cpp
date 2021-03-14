//
// Created by Randyfhp on 2021/3/13.
//
#include "file_operation.h"
#include <iostream>
#include <fstream>

extern "C" JNIEXPORT jboolean JNICALL Java_com_example_module_1ndk_file_NativeFileOperation_write
        (JNIEnv *env, jobject object, jstring src, jstring str, jint size) {
    std::ofstream file;
    std::string path(jstring2str(env, src));
    try {
        if (path.empty()) {
            path = ("/storage/emulated/0/file.txt");
        }
        std::cout << path << std::endl;
        file.open(path.c_str(), std::ios_base::app);
        if (file.good()) {
            file << jstring2str(env, str) << std::endl;
        }

    } catch (std::exception &ex) {
        std::cout << ex.what() << std::endl;
    }
    if (file.is_open()) {
        file.close();
    }

    return 1;
}

void callJavaStaticMethod(JNIEnv *env) {
    jclass clazz = env->FindClass("com/example/module_ndk/file/NativeFileOperation");
    if (clazz == nullptr) {
        std::cerr << "find class NativeFileOperation error!" << std::endl;
        return;
    }
    jmethodID id = env->GetStaticMethodID(clazz, "callbackStatic", "()V");
    if (id == nullptr) {
        std::cerr << "find method callbackStatic error!" << std::endl;
        return;
    }
    env->CallStaticVoidMethod(clazz, id);
}

void callJavaMethod(JNIEnv *env, jobject thiz, std::string &line) {
    jclass clazz = env->FindClass("com/example/module_ndk/file/NativeFileOperation");
    if (clazz == nullptr) {
        std::cerr << "find class NativeFileOperation error!" << std::endl;
        return;
    }
    jmethodID id = env->GetMethodID(clazz, "callback", "(Ljava/lang/String;)V");
    if (id == nullptr) {
        std::cerr << "find method callback error!" << std::endl;
        return;
    }
    jstring jstr = env->NewStringUTF(line.c_str());
    env->CallVoidMethod(thiz, id, jstr);
}

extern "C" JNIEXPORT jstring JNICALL Java_com_example_module_1ndk_file_NativeFileOperation_read
        (JNIEnv *env, jobject thiz, jstring src, jint size) {
    std::ifstream file;
    std::string all;

    std::string path(jstring2str(env, src));
    if (path.empty()) {
        path = ("/storage/emulated/0/file.txt");
    }
    try {
        if (file.good()) {
            file.open(path.c_str());
        }

        std::string line;
        while(getline(file, line)) {
            all += line;
            all += "\n";
            callJavaStaticMethod(env);
            callJavaMethod(env, thiz, line);
        }
    } catch (std::exception &ex) {
        std::cout << ex.what() << std::endl;
    }
    if (file.is_open()) {
        file.close();
    }

    std::string sub = all.substr(0, size);
    return env->NewStringUTF(sub.c_str());
}


jstring str2jstring(JNIEnv *env,const char *pat) {
    if(pat == nullptr) { env->NewStringUTF(""); }
    //定义java String类 strClass
    jclass strClass = (env)->FindClass("java/lang/String");
    //获取String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    //建立byte数组
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));
    //将char* 转换为byte数组
    (env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
    // 设置String, 保存语言类型,用于byte数组转换至String时的参数
    jstring encoding = (env)->NewStringUTF("utf-8s");
    //将byte数组转换为java String,并输出
    return (jstring)(env)->NewObject(strClass, ctorID, bytes, encoding);
}

std::string jstring2str(JNIEnv* env, jstring jstr) {
    if (jstr == nullptr) { return ""; }
    char* rtn = nullptr;
    jclass clsString = env->FindClass("java/lang/String");
    jstring strEncode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(clsString, "getBytes", "(Ljava/lang/String;)[B");
    auto barr = (jbyteArray)(env->CallObjectMethod(jstr, mid, strEncode));
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr,JNI_FALSE);
    if(alen > 0) {
        rtn = new char[alen + 1];
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    std::string result(rtn);
    delete[] rtn;
    return result;
}

