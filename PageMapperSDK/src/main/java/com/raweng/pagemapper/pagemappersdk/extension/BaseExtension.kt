package com.raweng.pagemapper.pagemappersdk.extension

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