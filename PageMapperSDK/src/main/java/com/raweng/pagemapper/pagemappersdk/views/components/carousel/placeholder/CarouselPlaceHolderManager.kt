package com.raweng.pagemapper.pagemappersdk.views.components.carousel.placeholder

import com.raweng.dfe_components_android.components.herocardcarousel.model.TopContainerData
import com.raweng.pagemapper.pagemappersdk.dependency.placeholder.ComponentPlaceHolderDependency
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.HeroCarouselPlaceHolder

class CarouselPlaceHolderManager(variant: String?) {

    private var heroCarouselPlaceHolder: HeroCarouselPlaceHolder? = null

    init {
        heroCarouselPlaceHolder = ComponentPlaceHolderDependency.getPlaceholder()
            ?.get(variant) as HeroCarouselPlaceHolder?
    }

    fun getPlaceholder(): Int? {
        return heroCarouselPlaceHolder?.placeholder
    }

    fun getSelectedIndicator(): Int? {
        return heroCarouselPlaceHolder?.selectedIndicator
    }

    fun getUnSelectedIndicator(): Int? {
        return heroCarouselPlaceHolder?.unSelectedIndicator
    }

    fun getTopContainerData(): TopContainerData? {
        return heroCarouselPlaceHolder?.carouselItemPlaceHolder?.topContainerData
    }

    fun getDetailContainerPlaceholder(): HeroCarouselPlaceHolder.CarouselItemPlaceHolder.DetailContainerPlaceholder? {
        return heroCarouselPlaceHolder?.carouselItemPlaceHolder?.detailContainerPlaceholder
    }

    fun getTrailingBottomIcon(): Int? {
        return heroCarouselPlaceHolder?.carouselItemPlaceHolder?.trailingBottomIcon
    }
}