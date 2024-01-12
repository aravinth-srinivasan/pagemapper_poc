package com.raweng.pagemapper.pagemappersdk.mapper.extension

fun String?.toVariant(): String? {
    if (!this.isNullOrEmpty()) {
        return if (this.lowercase().contains("default")) {
            ""
        } else {
            this
        }
    }
    return null
}