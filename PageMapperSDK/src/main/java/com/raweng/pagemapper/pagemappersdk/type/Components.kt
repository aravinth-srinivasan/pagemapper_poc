package com.raweng.pagemapper.pagemappersdk.type

enum class Components(private val value: String) {
    NEWS_FEED_VIEW("newsFeedView"),
    HORIZONTAL_LIST("horizontalScrollingCardListView"),
    HERO_CARD_CAROUSEL_VIEW("heroCardCarouselView"),
    FEATURED_FEEDS("featured_feeds"),
    IMAGE_VIEW("imageView"),
    USER_BANNER("userBanner"),
    TEXT_VIEW("textView"),
    GAME_STATS_CARD("gameStatsCard"),
    SOCIAL_MEDIA_LIST("socialMediaList"),
    GOOGLE_ADS_VIEW("googleAdsView"),
    SCORE_CARD_CAROUSEL("scoreCardCarousel"),
    RECENT_GAMES("recentGames"),
    UTILITY_MENU("utilityMenu");

    companion object {
        fun fromValue(value: String): Components? =
            entries.find {
                it.value.equals(value, ignoreCase = true)
            }
    }

    override fun toString(): String {
        return value
    }
}