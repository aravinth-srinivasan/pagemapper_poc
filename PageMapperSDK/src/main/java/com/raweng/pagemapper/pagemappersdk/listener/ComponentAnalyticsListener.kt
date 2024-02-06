package com.raweng.pagemapper.pagemappersdk.listener

import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel

interface ComponentAnalyticsListener {
    fun onAnalyticsEvent(data: ResponseDataModel, analyticData: String?)
}