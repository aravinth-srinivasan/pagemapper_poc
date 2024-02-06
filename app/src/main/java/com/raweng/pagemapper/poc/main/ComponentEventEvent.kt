package com.raweng.pagemapper.poc.main

import android.util.Log
import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.domain.GameStatsCardResponse
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.domain.GameStatsResponseAndStateModel
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.domain.BaseImageResponse

class ComponentEventEvent : ComponentEventListener {
    override fun onClickedComponent(data: ResponseDataModel, eventType: ClickEventType) {
        val item = data.item
        when (Components.fromValue(item?.value.orEmpty())) {
            Components.HERO_CARD_CAROUSEL_VIEW -> {
                if (eventType == ClickEventType.CAROUSEL_CLICK_LISTENER) {
                    val componentData = (data.convertedData as HeroCardCarouselDataModel?)
                    val uid = data.clickedData.toString()
                    val clickedItem = componentData?.itemList?.firstOrNull { it.uid == uid }
                    Log.e("TAG", "onClickedComponent: " + clickedItem?.detailContainerData?.trailingText)
                }
            }
            Components.IMAGE_VIEW -> {
                val componentData = (data.convertedData as ImageViewDataModel?)
                val responseData = (data.apiResponse as BaseImageResponse?)
                Log.e("TAG", "onClickedComponent: " + responseData?.ctaLink)
            }
            Components.GAME_STATS_CARD -> {
                val responseData = (data.apiResponse as GameStatsResponseAndStateModel?)
                val cmsData = (data.cmsResponse as GameStatsCardResponse?)
                Log.e("TAG", "onClickedComponent:Size " + responseData?.gameLeader?.size)
                Log.e(
                    "TAG",
                    "onClickedComponent:CMS " + cmsData?.title + " " + cmsData?.ctaButton?.clickthroughLink
                )
                Log.e("TAG", "onClickedComponent:Event " + eventType.toString())
            }
            else -> {}
        }
    }
}