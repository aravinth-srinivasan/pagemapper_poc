package com.raweng.pagemapper.pagemappersdk.utils

import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType

interface ComponentClickListener {
    fun onClickedComponent(
        data: ResponseDataModel,
        eventType: ClickEventType
    )
}