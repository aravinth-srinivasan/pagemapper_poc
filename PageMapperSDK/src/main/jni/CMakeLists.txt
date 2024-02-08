cmake_minimum_required(VERSION 3.18.1)

project("jni")

add_library(native-lib SHARED
        native-lib.cpp)

# Include libraries needed for hello-jni lib
target_link_libraries(native-lib android log)