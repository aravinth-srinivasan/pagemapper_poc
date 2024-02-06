package com.raweng.pagemapper.pagemappersdk.listener

import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType

interface ComponentEventListener {
    fun onClickedComponent(
        data: ResponseDataModel,
        eventType: ClickEventType
    )
}