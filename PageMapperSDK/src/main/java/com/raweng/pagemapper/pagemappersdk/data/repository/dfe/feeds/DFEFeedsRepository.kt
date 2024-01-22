package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.feeds

import com.raweng.dfe.models.feed.DFEFeedModel
import com.raweng.dfe.models.wsc.WSCFeeds
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.dfe.utils.FeedType
import com.raweng.pagemapper.pagemappersdk.data.api.DFEApiManager

class DFEFeedsRepository : IDFEFeedsRepository {

    override suspend fun fetchWSCVideos(
        gid: String,
        limit: Int
    ): List<WSCFeeds> {
        return try {
            DFEApiManager.fetchWSCVideos(RequestType.Network, gid, limit)
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }

    override suspend fun fetchFeeds(limit: Int, dfepDataSource: String): List<DFEFeedModel> {
        return try {
            val feedType = getFeedType(dfepDataSource)
            val params = createParams(limit, feedType)
            DFEApiManager.fetchFeedsList(RequestType.Network, params)
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }


    private fun createParams(
        limit: Int,
        feedType: FeedType? = null,
    ): HashMap<String, Any> {
        return if (feedType != null) {
            hashMapOf(
                DFEApiManager.KEY_LIMIT to limit,
                DFEApiManager.KEY_FEED_TYPE to feedType,
                DFEApiManager.KEY_SORT_ORDER to SORT_ORDER,
                DFEApiManager.KEY_SORT_BY to SORT_BY
            )
        } else {
            return hashMapOf(
                DFEApiManager.KEY_LIMIT to limit,
                DFEApiManager.KEY_SORT_ORDER to SORT_ORDER,
                DFEApiManager.KEY_SORT_BY to SORT_BY
            )
        }
    }

    companion object {

        private const val NBA_GALLERY = "nba_gallery"
        private const val NBA_VIDEO = "nba_video"
        private const val NBA_ARTICLE = "nba_article"
        private const val NBA_ALL_FEEDS = "nba_all_feeds"
        const val WSC_FEEDS = "wsc_feeds"
        private const val SORT_ORDER = "DESC"
        private const val SORT_BY = "sortBy"
        fun getFeedType(dfepDataSource: String?): FeedType? {
            return if (dfepDataSource.equals(NBA_GALLERY, true)) {
                FeedType.GALLERY
            } else if (dfepDataSource.equals(NBA_VIDEO, true)) {
                FeedType.VIDEO
            } else if (dfepDataSource.equals(NBA_ARTICLE, true)) {
                FeedType.ARTICLE
            } else if (dfepDataSource.equals(NBA_ALL_FEEDS, true)) {
                null
            } else if (dfepDataSource.equals(WSC_FEEDS, true)) {
                null
            } else {
                null
            }
        }
    }
}