package com.raweng.pagemapper

import com.raweng.dfe_components_android.components.herocardcarousel.model.TopContainerData
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.views.carousel.domain.HeroCarouselPlaceHolder
import com.raweng.pagemapper.poc.R

object ComponentPlaceHolder {

    fun init() {
        setupSocialMediaPlaceHolder()
        setupHeroCarouselPlaceHolder()
    }

    private fun setupSocialMediaPlaceHolder() {
        PageMapperSDK.setComponentPlaceHolder(
            Components.SOCIAL_MEDIA_LIST,
            R.drawable.mediaplayer_placeholder
        )
    }

    private fun setupHeroCarouselPlaceHolder() {
        val topContainerData = TopContainerData(
            leadingIconSelected = R.drawable.ic_full_screen,
            leadingIconUnSelected = R.drawable.ic_full_screen,
            trailingIconUnSelected = R.drawable.ic_soundon,
            trailingIconSelected = R.drawable.ic_sound_off
        )

        val detailContainerPlaceholder =
            HeroCarouselPlaceHolder.CarouselItemPlaceHolder.DetailContainerPlaceholder().apply {
                leadingIcon = R.drawable.static_ic_gallery
            }

        val heroCarouselPlaceHolder = HeroCarouselPlaceHolder().apply {
            placeholder = R.drawable.mediaplayer_placeholder
            selectedIndicator = R.drawable.ic_selected_indicator
            unSelectedIndicator = R.drawable.ic_unselected_indicator
            carouselItemPlaceHolder = HeroCarouselPlaceHolder.CarouselItemPlaceHolder(
                topContainerData = topContainerData,
                detailContainerPlaceholder = detailContainerPlaceholder
            )
        }
        PageMapperSDK.setComponentPlaceHolder(
            Components.HERO_CARD_CAROUSEL_VIEW,
            heroCarouselPlaceHolder
            )
    }

}