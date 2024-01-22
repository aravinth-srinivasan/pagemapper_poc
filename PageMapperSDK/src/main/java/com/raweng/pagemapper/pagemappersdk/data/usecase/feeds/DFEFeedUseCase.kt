package com.raweng.pagemapper.pagemappersdk.data.usecase.feeds

import com.raweng.dfe.models.feed.DFEFeedModel
import com.raweng.dfe.models.wsc.WSCFeeds
import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.feeds.DFEFeedsRepository

class DFEFeedUseCase(
    private val repository: DFEFeedsRepository,
) {
    suspend fun execute(gid: String, limit: Int): List<WSCFeeds> {
        return repository.fetchWSCVideos(gid, limit)
    }

    suspend fun execute(limit: Int, dfepDataSource: String): List<DFEFeedModel> {
        return repository.fetchFeeds(limit, dfepDataSource)
    }
}