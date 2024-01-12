package com.raweng.pagemapper.pagemappersdk.mapper.extension

import com.raweng.pagemapper.pagemappersdk.domain.components.BaseTextResponse

fun BaseTextResponse.toTextString(): String {
    return this.content ?: ""
}