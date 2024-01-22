package com.raweng.pagemapper.pagemappersdk.extension

import java.text.SimpleDateFormat
import java.util.Date

fun String.toDate(): String {
    return try {
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy")
        if (this.contains(".")) outputFormat.format(Date(this.toFloat().toLong()))
        else outputFormat.format(Date(this.toFloat().toLong()))
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        ""
    }
}