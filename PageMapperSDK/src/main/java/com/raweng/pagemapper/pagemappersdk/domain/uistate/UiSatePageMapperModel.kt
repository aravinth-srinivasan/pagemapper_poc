package com.raweng.pagemapper.pagemappersdk.domain.uistate

import com.raweng.pagemapper.pagemappersdk.data.api.base.Error
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse

data class UiSatePageMapperModel(
    var data: DynamicScreenResponse? = null,
    var loading: Boolean = false,
    var error: Error? = null
)