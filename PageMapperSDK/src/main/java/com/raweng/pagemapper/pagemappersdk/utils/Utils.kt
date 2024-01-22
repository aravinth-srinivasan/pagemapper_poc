package com.raweng.pagemapper.pagemappersdk.utils

object Utils {

    fun checkNullSafe(data: String?): String {
        return data?.takeUnless { it.equals("null", true) } ?: ""
    }
}