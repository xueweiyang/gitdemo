cmake_minimum_required(VERSION 3.10)

add_library(native-lib SHARED src/main/cpp/native-lib.cpp)
add_library(JNI_Shm SHARED src/main/cpp/JNI_Shm.cpp)

find_library(log-lib log)

target_link_libraries(native-lib JNI_Shm ${log-lib})

