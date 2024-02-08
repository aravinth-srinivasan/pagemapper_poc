package com.raweng.pagemapper.pagemappersdk.utils

class JNIUtils {
    init {
        System.loadLibrary("native-lib")
    }

    private external fun jniCreateInstance(qualifiedClassName: String): Any?
    fun createClassInstance(qualifiedClassName: String):Any? {
        return jniCreateInstance(qualifiedClassName)
    }
}