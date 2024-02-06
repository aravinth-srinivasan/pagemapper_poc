package com.raweng.pagemapper.pagemappersdk.views.components.carousel.extension

import com.raweng.dfe.models.wsc.WSCFeeds
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselAnalytics
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselItemDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.DetailContainerData
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.extension.toSectionHeaderDataModelDynamic
import com.raweng.pagemapper.pagemappersdk.dependency.placeholder.ComponentPlaceHolderDependency
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.CarouselViewResponse

fun List<WSCFeeds>.toCarousalItem(
    dependency: InternalComponentDependency,
    response: CarouselViewResponse?
): HeroCardCarouselDataModel {
    return HeroCardCarouselDataModel(
        sectionHeaderDataModel = response?.sectionHeader?.toSectionHeaderDataModelDynamic(),
        itemList = this.toCarouselItemList(dependency),
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

fun List<WSCFeeds>.toCarouselItemList(dependency: InternalComponentDependency): MutableList<CarouselItemDataModel> {
    return map {
        it.toCarouselItem(dependency)
    }.toMutableList()
}

fun WSCFeeds.toCarouselItem(dependency: InternalComponentDependency): CarouselItemDataModel {
    val uid = this.uid.toString()
    val title = this.title.uppercase()
    return CarouselItemDataModel(
        topContainerData = ComponentPlaceHolderDependency.getCarouselPlaceholder().carouselItemPlaceHolder?.topContainerData,
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