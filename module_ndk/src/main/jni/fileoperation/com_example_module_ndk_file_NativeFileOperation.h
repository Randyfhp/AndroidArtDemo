/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_example_module_ndk_file_NativeFileOperation */

#ifndef _Included_com_example_module_ndk_file_NativeFileOperation
#define _Included_com_example_module_ndk_file_NativeFileOperation
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_example_module_ndk_file_NativeFileOperation
 * Method:    write
 * Signature: (Ljava/lang/String;I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_example_module_1ndk_file_NativeFileOperation_write
  (JNIEnv *, jobject, jstring, jstring, jint);

/*
 * Class:     com_example_module_ndk_file_NativeFileOperation
 * Method:    read
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_example_module_1ndk_file_NativeFileOperation_read
  (JNIEnv *, jobject, jstring, jint);

#ifdef __cplusplus
}
#endif
#endif
