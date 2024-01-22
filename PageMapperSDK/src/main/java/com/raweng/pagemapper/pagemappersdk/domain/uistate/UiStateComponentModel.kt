package com.raweng.pagemapper.pagemappersdk.domain.uistate

import com.raweng.pagemapper.pagemappersdk.data.api.base.Error
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel

data class UiStateComponentModel(
    var data: ResponseDataModel? = null,
    var loading: Boolean = false,
    var error: Error? = null,
    var uidLive:String = ""
)