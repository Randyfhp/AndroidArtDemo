//
// Created by Randyfhp on 2021/3/15.
//

#ifndef ANDROIDARTDEMO_FILE_OPERATION_H
#define ANDROIDARTDEMO_FILE_OPERATION_H

#include "com_example_module_ndk_file_NativeFileOperation.h"
#include <string>
#ifdef __cplusplus
extern "C" {
#endif
/**
 * c风格字符串转java风格字符串
 * @param env
 * @param pat
 * @return
 */
jstring str2jstring(JNIEnv *,const char *);

/**
 * java风格字符串转c风格字符串
 * @param env
 * @param jstr
 * @return
 */
std::string jstring2str(JNIEnv* env, jstring jstr);
#ifdef __cplusplus
}
#endif
#endif //ANDROIDARTDEMO_FILE_OPERATION_H
