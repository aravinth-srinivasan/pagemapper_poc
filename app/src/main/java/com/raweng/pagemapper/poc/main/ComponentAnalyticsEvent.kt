package com.raweng.pagemapper.poc.main

import android.util.Log
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.type.Components
class ComponentAnalyticsEvent : ComponentAnalyticsListener {
    override fun onAnalyticsEvent(data: ResponseDataModel, analyticData: String?) {
        val item = data.item
        when (Components.fromValue(item?.value.orEmpty())) {
            Components.HERO_CARD_CAROUSEL_VIEW -> {
                Log.e("TAG", "onAnalyticsEvent:${Components.HERO_CARD_CAROUSEL_VIEW}  $analyticData")
            }
            Components.IMAGE_VIEW -> {
                Log.e("TAG", "onAnalyticsEvent:${Components.IMAGE_VIEW} $analyticData")
            }
            Components.GAME_STATS_CARD -> {
                Log.e("TAG", "onAnalyticsEvent:${Components.GAME_STATS_CARD} $analyticData")
            }

            else -> {}
        }
    }
}