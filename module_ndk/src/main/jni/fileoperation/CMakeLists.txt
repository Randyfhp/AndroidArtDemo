cmake_minimum_required(VERSION 3.5)

# 配置库名
set(LIB_NAME fileOperation)

# 配置编译器属性
# set(CMAKE_CXX_COMPILER "g++")
# set(CMAKE_BUILD_TYPE Release)

# 搜索多线程库
find_package(Threads REQUIRED)
if(${Threads_FOUND})
message("multi Threads " ${Threads_FOUND})
endif(${Threads_FOUND})

# 搜索log库
find_library(log-lib log)

# 添加头文件依赖
INCLUDE_DIRECTORIES(${CMAKE_CURRENT_LIST_DIR})
include_directories(${Threads_INCLUDE})

# 搜索源文件
aux_source_directory(${CMAKE_CURRENT_LIST_DIR} DIR_SRC)

# 配置库属性
add_library(${LIB_NAME} SHARED ${DIR_SRC})

# 连接生成库
target_link_libraries(${LIB_NAME} ${Threads_LIBRARY} ${log-lib})