package com.raweng.pagemapper.pagemappersdk.views.components.carousel.extension

import com.raweng.dfe.models.feed.DFEFeedModel
import com.raweng.dfe.utils.FeedType
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselAnalytics
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselItemDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.DetailContainerData
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.extension.toDate
import com.raweng.pagemapper.pagemappersdk.extension.toSectionHeaderDataModelDynamic
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.CarouselViewResponse
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.placeholder.CarouselPlaceHolderManager

fun List<DFEFeedModel>.toCarousalItem(
    dependency: InternalComponentDependency,
    response: CarouselViewResponse?,
    feedType: FeedType?,
    placeHolderManager: CarouselPlaceHolderManager
): HeroCardCarouselDataModel {

    return HeroCardCarouselDataModel(
        sectionHeaderDataModel = response?.sectionHeader?.toSectionHeaderDataModelDynamic(),
        itemList = this.toCarouselItemList(feedType, dependency, placeHolderManager),
        placeholder = placeHolderManager.getPlaceholder(),
        autoScrollTimer = response?.autoScrollTimer?.toLong(),
        autoScrollEnable = response?.autoScroll ?: false,
        isIndicatorVisible = !(response?.showCarouselIndicator ?: false),
        videoRepeatModeEnable = response?.repeatVideo,
        videoRepeatCount = response?.repeatVideoCount,
        isVideoMute = ((response?.muteVideo) ?: true),
        selectedIndicator = placeHolderManager.getSelectedIndicator(),
        unSelectedIndicator = placeHolderManager.getUnSelectedIndicator()
    )
}

fun List<DFEFeedModel>.toCarouselItemList(
    feedType: FeedType?,
    dependency: InternalComponentDependency,
    placeHolderManager: CarouselPlaceHolderManager
): MutableList<CarouselItemDataModel> {
    return mapIndexed { index, dfeFeedModel ->
        dfeFeedModel.toCarouselItem(feedType, dependency, index, placeHolderManager)
    }.toMutableList()
}

fun DFEFeedModel.toCarouselItem(
    feedType: FeedType?,
    dependency: InternalComponentDependency,
    index: Int,
    placeHolderManager: CarouselPlaceHolderManager
): CarouselItemDataModel {
    val title = this.title.uppercase()
    val newsId = this.newsid
    if (feedType == FeedType.VIDEO) {
        return CarouselItemDataModel(
            topContainerData = placeHolderManager.getTopContainerData(),
            videoUrl = getVideoUrl(this, feedType),
            uid = newsId,
            detailContainerData = DetailContainerData(
                trailingText = title
            ),
            analytics = CarouselAnalytics(
                title = title,
                type = "Video",
                component = Components.HERO_CARD_CAROUSEL_VIEW.toString(),
                contentId = newsId,
                orderId = index.toString(),
                parent = dependency.dependency.parentScreenName
            )
        )
    } else if (feedType == FeedType.GALLERY) {
        val containsMedia = !this.media.isNullOrEmpty()
        return CarouselItemDataModel(
            title = title,
            cardImageUrl = if (containsMedia) this.media?.get(0)?.portrait
                ?: this.media?.get(0)?.thumbnail else "",
            detailContainerData = DetailContainerData(
                leadingIcon = placeHolderManager.getDetailContainerPlaceholder()?.leadingIcon,
                leadingText = "GALLERY", //TODO need to confirm
                trailingText = this.publishedDate.toDate().uppercase()
            ),
            uid = newsId,
            trailingBottomIcon = placeHolderManager.getTrailingBottomIcon(),
            analytics = CarouselAnalytics(
                title = title,
                type = "Gallery",
                contentId = newsId,
                orderId = index.toString(),
                parent = dependency.dependency.parentScreenName
            )
        )
    }

    return CarouselItemDataModel(
        title = "",
        cardImageUrl = "",
        videoUrl = "",
        trailingBottomIcon = 0,
        topContainerData = null,
        detailContainerData = null,
        uid = this.newsid
    )
}

private fun getVideoUrl(item: DFEFeedModel, feedType: FeedType?): String {
    if (feedType == FeedType.VIDEO) {
        val url = item.video.getOrNull(0)?.url ?: ""
        if (url.isNotEmpty()) {
            return url
        }
    }
    return ""
}