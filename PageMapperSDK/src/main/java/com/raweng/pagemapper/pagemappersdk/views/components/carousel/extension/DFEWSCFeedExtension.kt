package com.raweng.pagemapper.pagemappersdk.views.components.carousel.extension

import com.raweng.dfe.models.wsc.WSCFeeds
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselItemDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.DetailContainerData
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.extension.toSectionHeaderDataModelDynamic
import com.raweng.pagemapper.pagemappersdk.dependency.placeholder.ComponentPlaceHolderDependency
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.CarouselViewResponse

fun List<WSCFeeds>.toCarousalItem(
    response: CarouselViewResponse?
): HeroCardCarouselDataModel {
    return HeroCardCarouselDataModel(
        sectionHeaderDataModel = response?.sectionHeader?.toSectionHeaderDataModelDynamic(),
        itemList = this.toCarouselItemList(),
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

fun List<WSCFeeds>.toCarouselItemList(): MutableList<CarouselItemDataModel> {
    return map {
        it.toCarouselItem()
    }.toMutableList()
}

fun WSCFeeds.toCarouselItem(): CarouselItemDataModel {
    val uid = this.uid.toString()
    return CarouselItemDataModel(
        topContainerData = ComponentPlaceHolderDependency.getCarouselPlaceholder().carouselItemPlaceHolder?.topContainerData,
        videoUrl = getVideoUrl(this),
        uid = uid,
        detailContainerData = DetailContainerData(
            trailingText = this.title.uppercase()
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