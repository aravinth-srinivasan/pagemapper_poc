package com.raweng.pagemapper.pagemappersdk.domain

import com.raweng.pagemapper.pagemappersdk.data.manager.api.base.Error

data class UiSatePageMapperModel(
    var data: DynamicScreenResponse? = null,
    var loading: Boolean = false,
    var error: Error? = null
)