package com.raweng.pagemapper.pagemappersdk.views.carousel.domain

import com.raweng.dfe_components_android.components.herocardcarousel.model.TopContainerData

data class HeroCarouselPlaceHolder(
    var selectedIndicator: Int? = null,
    var unSelectedIndicator: Int? = null,
    var placeholder: Int? = null,
    var carouselItemPlaceHolder: CarouselItemPlaceHolder? = null
) {
    data class CarouselItemPlaceHolder(
        var topContainerData: TopContainerData? = null,
        var detailContainerPlaceholder: DetailContainerPlaceholder? = null,
        var trailingBottomIcon: Int? = null
    ) {
        data class DetailContainerPlaceholder(
            var leadingIcon: Int? = null
        )
    }
}