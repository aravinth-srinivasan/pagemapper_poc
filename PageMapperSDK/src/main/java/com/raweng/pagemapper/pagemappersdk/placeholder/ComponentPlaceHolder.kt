package com.raweng.pagemapper.pagemappersdk.placeholder

import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.views.carousel.domain.HeroCarouselPlaceHolder
import java.util.EnumMap

internal object ComponentPlaceHolder {

    private val componentPlaceHolder = EnumMap<Components, Int>(Components::class.java)
    private var heroCarouselPlaceHolder: HeroCarouselPlaceHolder? = null

    fun config(components: Components, placeholder: Any) {
        when (components) {
            Components.HERO_CARD_CAROUSEL_VIEW -> {
                if (placeholder is HeroCarouselPlaceHolder) {
                    heroCarouselPlaceHolder = placeholder
                }
            }

            else -> {
                if (placeholder is Int) {
                    componentPlaceHolder[components] = placeholder
                }
            }
        }
    }

    fun getCarouselPlaceholder(): HeroCarouselPlaceHolder {
        return heroCarouselPlaceHolder ?: HeroCarouselPlaceHolder()
    }

    fun getPlaceholder(components: Components): Int? {
        return componentPlaceHolder[components]
    }
}