###############NDK开发流程###########

1.在java文件夹下的com.example.module_ndk.file创建Java-Native接口的文件

2.在上述文件夹下运行：
    javac NativeFileOperation.java
    生成class文件

3.cd 退出到java目录下：
    javah -jni com.example.module_ndk.file.NativeFileOperation
    在java目录下生成.h文件

4.创建main/jni文件夹

5.然后将生成的.h文件复制道main/jni文件夹下

6.在main/jni下编写c++代码

7.编写CMakeLists.txt

8.在工程的build.gradle下配置
    android {
        defaultConfig {
            externalNativeBuild {
                cmake {
                    cppFlags "-std=c++11"
                }
            }

            ndk {
                abiFilters "armeabi-v7a"
            }

            // 如果不使用jni这个默认名，就配置下方
            sourceSets.main {
                jniLibs.srcDirs = "src/main/jni-src"
            }
        }

        externalNativeBuild {
            cmake {
                path "src/main/jni/fileoperation/CMakeLists.txt"
            }
        }
    }

9.编译运行