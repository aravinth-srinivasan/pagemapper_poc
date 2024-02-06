package com.raweng.pagemapper.pagemappersdk.views.components.carousel.extension

import com.raweng.dfe.models.playbyplay.DFEPlayByPlayModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselAnalytics
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselItemDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.DetailContainerData
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.extension.toSectionHeaderDataModelDynamic
import com.raweng.pagemapper.pagemappersdk.dependency.placeholder.ComponentPlaceHolderDependency
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.CarouselViewResponse

fun List<DFEPlayByPlayModel>.toCarousalItem(
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

fun List<DFEPlayByPlayModel>.toCarouselItemList(dependency: InternalComponentDependency): MutableList<CarouselItemDataModel> {
    return mapIndexed { index, dfePlayByPlayModel ->
        dfePlayByPlayModel.toCarouselItem(dependency, index)
    }.toMutableList()
}

fun DFEPlayByPlayModel.toCarouselItem(
    dependency: InternalComponentDependency,
    index: Int
): CarouselItemDataModel {
    val uid = this.uid
    val title = this.description.uppercase()
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
            orderId = index.toString(),
            parent = dependency.dependency.parentScreenName
        )
    )
}

private fun getVideoUrl(item: DFEPlayByPlayModel): String {
    val url = item.event_clips.getOrNull(0)?.video_url ?: ""
    if (url.isNotEmpty()) {
        return url
    }
    return ""
}