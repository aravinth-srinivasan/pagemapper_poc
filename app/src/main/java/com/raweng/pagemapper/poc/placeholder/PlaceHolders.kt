package com.raweng.pagemapper.poc.placeholder

import com.raweng.pagemapper.poc.placeholder.carousel.CarouselImagePlaceholder
import com.raweng.pagemapper.poc.placeholder.carousel.CarouselVideoPlaceholder

object PlaceHolders {

    fun getPlaceHolder(): HashMap<String, Any> {
        return hashMapOf(
            "highlightsTheme" to CarouselVideoPlaceholder.getCarouselVideoPlaceholder(),
            "galleryTheme" to CarouselImagePlaceholder.getCarouselImagePlaceholder(),
        )
    }
}