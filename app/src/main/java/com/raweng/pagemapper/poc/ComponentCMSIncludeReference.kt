package com.raweng.pagemapper.poc

import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.type.Components

object ComponentCMSIncludeReference {

    private const val SECTION_HEADER_SPONSOR_REF = "section_header.sponsor"
    private const val CARD_ITEM_SPONSOR_REF = "card_items.sponsor"

    private val SECTION_HEADER_SPONSOR: Array<String> = arrayOf(SECTION_HEADER_SPONSOR_REF)
    private val CARD_HEADER_SPONSOR: Array<String> =
        arrayOf(SECTION_HEADER_SPONSOR_REF, CARD_ITEM_SPONSOR_REF)

    fun init() {
        setupHeroCarouselRef()
    }

    private fun setupHeroCarouselRef() {
        PageMapperSDK.setComponentCMSIncludeRef(
            Components.HERO_CARD_CAROUSEL_VIEW,
            SECTION_HEADER_SPONSOR
        )
    }
}