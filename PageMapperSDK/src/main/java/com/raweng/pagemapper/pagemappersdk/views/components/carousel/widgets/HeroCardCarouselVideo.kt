package com.raweng.pagemapper.pagemappersdk.views.components.carousel.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asFlow
import androidx.media3.exoplayer.ExoPlayer
import com.raweng.dfe_components_android.commoncomponents.imageview.ImageViewNative
import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.HeroCardCarouselView
import com.raweng.dfe_components_android.components.herocardcarousel.interfaces.AnalyticsInterface
import com.raweng.dfe_components_android.components.herocardcarousel.model.CarouselItemDataModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.extension.toVariant
import com.raweng.pagemapper.pagemappersdk.utils.ComposableLifecycle
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.viewmodel.CarouselViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HeroCardCarouselVideo(
    modifier: Modifier = Modifier,
    responseModel: ResponseDataModel,
    carouselViewModel: CarouselViewModel,
    carouselClickListener: ((String?) -> Unit)? = null,
    subHeaderClickListener: ((String?) -> Unit)? = null,
    analyticsInterface: AnalyticsInterface? = null
) {
    val convertedData = (responseModel.convertedData as HeroCardCarouselDataModel)
    var onPausedCalled = false
    var heroCardCarouselView by remember { mutableStateOf<HeroCardCarouselView?>(null) }

    LaunchedEffect(Unit) {
        carouselViewModel.uiUpdatedStateLiveData.asFlow().collectLatest {
            try {
                if (it.data != null && it.data?.convertedData != null) {
                    val updatedConvertedData = (it.data?.convertedData as HeroCardCarouselDataModel)
                    if (!updatedConvertedData.itemList.isNullOrEmpty()) {
                        heroCardCarouselView?.loadMorePagerItems(
                            position = 0,
                            removeDataCount = carouselViewModel.getGameStatsMapper()
                                .removeItemsSize(),
                            updatedConvertedData.itemList
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .layoutId("heroCardCarouselPhoto")
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            factory = {
                HeroCardCarouselView(
                    it
                ).apply {
                    if (!convertedData.itemList.isNullOrEmpty()) {
                        configureView(
                            dataModel = convertedData,
                            style = responseModel.item?.variant.toVariant()
                        )
                    }
                    trailingIconClickListener =
                        { carouselDataModel, player, imageView, carouselItem ->
                            muteUnMutePlayer(carouselDataModel, player, imageView, carouselItem)
                        }
                    leadingIconClickListener = { datModel, player, carouselItem ->

                    }
                    this.subHeaderClickListener = subHeaderClickListener
                    this.carouselClickListener = carouselClickListener
                    this.analyticsInterface = analyticsInterface
                    heroCardCarouselView = this // Store the reference
                }

            },
            update = {

            }
        )
    }

    ComposableLifecycle(lifeCycleOwner = LocalLifecycleOwner.current, onEvent = { source, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            try {
                if (onPausedCalled) {
                    heroCardCarouselView?.resumeCurrentlyPlayingVideo()
                    heroCardCarouselView?.registerViewPagerPageChangeListener()
                    heroCardCarouselView?.startAutoScroll()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (event == Lifecycle.Event.ON_PAUSE) {
            try {
                onPausedCalled = true
                heroCardCarouselView?.pauseCurrentlyPlayingVideo()
                heroCardCarouselView?.stopAutoScrollTimer()
                heroCardCarouselView?.unregisterViewPagerPageChangeListener()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (event == Lifecycle.Event.ON_DESTROY) {
            try {
                heroCardCarouselView?.releaseAllPlayers()
                heroCardCarouselView?.stopAutoScrollTimer()
                heroCardCarouselView?.unregisterViewPagerPageChangeListener()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }, onDisposeCalled = {

        try {
            heroCardCarouselView?.releaseAllPlayers()
            heroCardCarouselView?.stopAutoScrollTimer()
            heroCardCarouselView?.unregisterViewPagerPageChangeListener()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    })

}

fun muteUnMutePlayer(
    carouselDataModel: HeroCardCarouselDataModel?,
    player: ExoPlayer,
    trailingIcon: ImageViewNative,
    item: CarouselItemDataModel?
) {
    val isMuted = player.volume == 0f
    if (isMuted) {
        // Un-mute the player
        carouselDataModel?.isVideoMute = false
        player.volume = 1f
        trailingIcon.configureView(
            dataModel = ImageViewDataModel(
                imageRes = item?.topContainerData?.trailingIconUnSelected
            )
        )
    } else {
        // Mute the player
        carouselDataModel?.isVideoMute = true
        player.volume = 0f
        trailingIcon.configureView(
            dataModel = ImageViewDataModel(
                imageRes = item?.topContainerData?.trailingIconSelected
            )
        )
    }
}
