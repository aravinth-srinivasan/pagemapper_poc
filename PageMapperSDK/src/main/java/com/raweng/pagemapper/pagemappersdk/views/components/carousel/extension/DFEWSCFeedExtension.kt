package com.raweng.pagemapper.pagemappersdk.views.components.carousel.extension

import com.raweng.dfe.models.wsc.WSCFeeds
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselAnalytics
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselItemDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.DetailContainerData
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.extension.toSectionHeaderDataModelDynamic
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.CarouselViewResponse
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.placeholder.CarouselPlaceHolderManager

fun List<WSCFeeds>.toCarousalItem(
    dependency: InternalComponentDependency,
    response: CarouselViewResponse?,
    placeHolderManager: CarouselPlaceHolderManager
): HeroCardCarouselDataModel {
    return HeroCardCarouselDataModel(
        sectionHeaderDataModel = response?.sectionHeader?.toSectionHeaderDataModelDynamic(),
        itemList = this.toCarouselItemList(dependency, placeHolderManager),
        placeholder = placeHolderManager.getPlaceholder(),
        autoScrollTimer = response?.autoScrollTimer?.toLong(),
        autoScrollEnable = response?.autoScroll ?: false,
        isIndicatorVisible = !(response?.showCarouselIndicator ?: false),
        videoRepeatModeEnable = response?.repeatVideo,
        videoRepeatCount = response?.repeatVideoCount,
        isVideoMute = ((response?.muteVideo) ?: true),
        selectedIndicator = placeHolderManager.getSelectedIndicator(),
        unSelectedIndicator = placeHolderManager.getUnSelectedIndicator(),
    )
}

fun List<WSCFeeds>.toCarouselItemList(
    dependency: InternalComponentDependency,
    placeHolderManager: CarouselPlaceHolderManager
): MutableList<CarouselItemDataModel> {
    return map {
        it.toCarouselItem(dependency, placeHolderManager)
    }.toMutableList()
}

fun WSCFeeds.toCarouselItem(
    dependency: InternalComponentDependency,
    placeHolderManager: CarouselPlaceHolderManager
): CarouselItemDataModel {
    val uid = this.uid.toString()
    val title = this.title.uppercase()
    return CarouselItemDataModel(
        topContainerData = placeHolderManager.getTopContainerData(),
        videoUrl = getVideoUrl(this),
        uid = uid,
        detailContainerData = DetailContainerData(
            trailingText = title
        ),
        analytics = CarouselAnalytics(
            title = title,
            contentId = uid,
            type = "Video",
            parent = dependency.dependency.parentScreenName
        )
    )
}


private fun getVideoUrl(item: WSCFeeds): String {
    val url = item.event_clips.getOrNull(0)?.video_url ?: ""
    if (url.isNotEmpty()) {
        return url
    }
    return ""
}