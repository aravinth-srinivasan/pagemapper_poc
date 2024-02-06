package com.raweng.pagemapper.pagemappersdk.views.components.carousel.extension

import com.raweng.dfe.models.feed.DFEFeedModel
import com.raweng.dfe.utils.FeedType
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselAnalytics
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselItemDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.DetailContainerData
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.extension.toDate
import com.raweng.pagemapper.pagemappersdk.extension.toSectionHeaderDataModelDynamic
import com.raweng.pagemapper.pagemappersdk.dependency.placeholder.ComponentPlaceHolderDependency
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.CarouselViewResponse

fun List<DFEFeedModel>.toCarousalItem(
    dependency: InternalComponentDependency,
    response: CarouselViewResponse?,
    feedType: FeedType?
): HeroCardCarouselDataModel {
    return HeroCardCarouselDataModel(
        sectionHeaderDataModel = response?.sectionHeader?.toSectionHeaderDataModelDynamic(),
        itemList = this.toCarouselItemList(feedType, dependency),
        placeholder = ComponentPlaceHolderDependency.getCarouselPlaceholder().placeholder,
        autoScrollTimer = response?.autoScrollTimer?.toLong(),
        autoScrollEnable = response?.autoScroll ?: false,
        isIndicatorVisible = !(response?.showCarouselIndicator ?: false),
        videoRepeatModeEnable = response?.repeatVideo,
        videoRepeatCount = response?.repeatVideoCount,
        isVideoMute = ((response?.muteVideo) ?: true),
        selectedIndicator = ComponentPlaceHolderDependency.getCarouselPlaceholder().selectedIndicator,
        unSelectedIndicator = ComponentPlaceHolderDependency.getCarouselPlaceholder().unSelectedIndicator,
    )
}

fun List<DFEFeedModel>.toCarouselItemList(
    feedType: FeedType?,
    dependency: InternalComponentDependency
): MutableList<CarouselItemDataModel> {
    return mapIndexed { index, dfeFeedModel ->
        dfeFeedModel.toCarouselItem(feedType, dependency, index)
    }.toMutableList()
}

fun DFEFeedModel.toCarouselItem(
    feedType: FeedType?,
    dependency: InternalComponentDependency,
    index: Int
): CarouselItemDataModel {
    val title = this.title.uppercase()
    val newsId = this.newsid
    if (feedType == FeedType.VIDEO) {
        return CarouselItemDataModel(
            topContainerData = ComponentPlaceHolderDependency.getCarouselPlaceholder().carouselItemPlaceHolder?.topContainerData,
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
                leadingIcon = ComponentPlaceHolderDependency.getCarouselPlaceholder().carouselItemPlaceHolder?.detailContainerPlaceholder?.leadingIcon,
                leadingText = "GALLERY", //TODO need to confirm
                trailingText = this.publishedDate.toDate().uppercase()
            ),
            uid = newsId,
            trailingBottomIcon = ComponentPlaceHolderDependency.getCarouselPlaceholder().carouselItemPlaceHolder?.trailingBottomIcon,
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