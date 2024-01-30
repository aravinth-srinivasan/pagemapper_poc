package com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain

import com.raweng.dfe.models.feed.DFEFeedModel
import com.raweng.dfe.models.wsc.WSCFeeds

data class DFEFeedDataModel(
    var wscFeeds: List<WSCFeeds> = listOf(),
    var feeds: List<DFEFeedModel> = listOf(),
    var isWSCFeed: Boolean = false
)