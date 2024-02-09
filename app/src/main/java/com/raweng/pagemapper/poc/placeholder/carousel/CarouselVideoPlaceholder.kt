package com.raweng.pagemapper.poc.placeholder.carousel

import com.raweng.dfe_components_android.components.herocardcarousel.model.TopContainerData
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.HeroCarouselPlaceHolder
import com.raweng.pagemapper.poc.R
object CarouselVideoPlaceholder {

    fun getCarouselVideoPlaceholder(): HeroCarouselPlaceHolder {
        val heroCarouselPlaceHolder = HeroCarouselPlaceHolder().apply {
            placeholder = R.drawable.mediaplayer_placeholder
            selectedIndicator = R.drawable.ic_selected_indicator
            unSelectedIndicator = R.drawable.ic_unselected_indicator
            carouselItemPlaceHolder = HeroCarouselPlaceHolder.CarouselItemPlaceHolder(
                topContainerData = getTopContainerData(),
                detailContainerPlaceholder = getDetailContainerPlaceholder()
            )
        }
        return heroCarouselPlaceHolder
    }


    private fun getTopContainerData(): TopContainerData {
        return TopContainerData(
            leadingIconSelected = R.drawable.ic_full_screen,
            leadingIconUnSelected = R.drawable.ic_full_screen,
            trailingIconUnSelected = R.drawable.ic_soundon,
            trailingIconSelected = R.drawable.ic_sound_off
        )
    }

    private fun getDetailContainerPlaceholder(): HeroCarouselPlaceHolder.CarouselItemPlaceHolder.DetailContainerPlaceholder {
        return HeroCarouselPlaceHolder.CarouselItemPlaceHolder.DetailContainerPlaceholder().apply {
            leadingIcon = R.drawable.static_ic_gallery
        }
    }
}