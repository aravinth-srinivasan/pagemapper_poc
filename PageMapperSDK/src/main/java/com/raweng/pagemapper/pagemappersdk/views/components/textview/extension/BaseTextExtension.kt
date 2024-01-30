package com.raweng.pagemapper.pagemappersdk.views.components.textview.extension

import com.raweng.pagemapper.pagemappersdk.views.components.textview.domain.BaseTextResponse

fun BaseTextResponse.toTextString(): String {
    return this.content ?: ""
}