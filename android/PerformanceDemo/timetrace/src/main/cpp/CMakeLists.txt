cmake_minimum_required(VERSION 3.4.1)

#设置变量jnilibs，CMAKE_SOURCE_DIR是内置变量，指向cmake文件所在的目录
set(jnilibs "${CMAKE_SOURCE_DIR}/jniLibs")

#设置so库生成的目录,ANDROID_ABI指不同的cpu版本
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${jnilibs}/${ANDROID_ABI})

#生成库的名字是add，类型是SHARED，也就是.so库，STATIC是.a库，这里cpp路径可以使用相对路径
add_library(tracehook SHARED ATraceLogger.cpp)
add_library(threadhook SHARED ThreadHook.cpp)

add_subdirectory(linker)
include_directories(linker)

find_library(log-lib log)

target_link_libraries(tracehook ${log-lib})
target_link_libraries(threadhook ${log-lib})