package com.raweng.pagemapper.poc

import android.util.Log
import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.views.gamestats.domain.GameStatsCardResponse
import com.raweng.pagemapper.pagemappersdk.views.gamestats.domain.GameStatsResponseAndStateModel
import com.raweng.pagemapper.pagemappersdk.views.imageview.domain.BaseImageResponse

class ComponentClickEvent : ComponentClickListener {
    override fun onClickedComponent(data: ResponseDataModel, eventType: ClickEventType) {
        val item = data.item
        when (Components.fromValue(item?.value.orEmpty())) {
            Components.NEWS_FEED_VIEW -> TODO()
            Components.HORIZONTAL_LIST -> TODO()
            Components.HERO_CARD_CAROUSEL_VIEW -> {
                if (eventType == ClickEventType.CAROUSEL_CLICK_LISTENER) {
                    val componentData = (data.convertedData as HeroCardCarouselDataModel?)
                    val uid = data.clickedData.toString()
                    Log.e("TAG", "onClickedComponent:Clicked " + uid)
                    val clickedItem = componentData?.itemList?.firstOrNull { it.uid == uid }
                    Log.e("TAG", "onClickedComponent: " + clickedItem?.detailContainerData?.trailingText)
                }
            }

            Components.FEATURED_FEEDS -> TODO()
            Components.IMAGE_VIEW -> {
                val componentData = (data.convertedData as ImageViewDataModel?)
                val responseData = (data.apiResponse as BaseImageResponse?)
                Log.e("TAG", "onClickedComponent: " + responseData?.ctaLink)
            }

            Components.USER_BANNER -> TODO()
            Components.TEXT_VIEW -> TODO()
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

            Components.SOCIAL_MEDIA_LIST -> TODO()
            Components.GOOGLE_ADS_VIEW -> TODO()
            Components.SCORE_CARD_CAROUSEL -> TODO()
            Components.RECENT_GAMES -> TODO()
            Components.UTILITY_MENU -> TODO()
            null -> {}
        }
    }
}