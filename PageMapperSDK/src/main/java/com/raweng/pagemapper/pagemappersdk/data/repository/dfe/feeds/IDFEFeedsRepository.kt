package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.feeds

import com.raweng.dfe.models.feed.DFEFeedModel
import com.raweng.dfe.models.wsc.WSCFeeds

interface IDFEFeedsRepository {
    suspend fun fetchWSCVideos(
        gid: String,
        limit: Int,
    ): List<WSCFeeds>

    suspend fun fetchFeeds(limit: Int, dfepDataSource: String): List<DFEFeedModel>
}