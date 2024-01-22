package com.raweng.pagemapper.pagemappersdk.views.textview.extension

import com.raweng.pagemapper.pagemappersdk.views.textview.domain.BaseTextResponse

fun BaseTextResponse.toTextString(): String {
    return this.content ?: ""
}