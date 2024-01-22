package com.raweng.pagemapper.pagemappersdk.type

enum class ClickEventType(private val value: String) {

    ON_CLICK("onClick"),
    ON_GAME_STATS_TRAILING_CARD_CLICKED("onGameStatsTrailingCardClicked"),
    CAROUSEL_CLICK_LISTENER("carouselClickListener");
    companion object {
        fun fromValue(value: String): ClickEventType? =
            ClickEventType.entries.find {
                it.value.equals(value, ignoreCase = true)
            }
    }

    override fun toString(): String {
        return value
    }
}