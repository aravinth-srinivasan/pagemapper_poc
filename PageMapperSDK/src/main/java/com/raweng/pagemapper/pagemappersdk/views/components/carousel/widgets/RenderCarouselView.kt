package com.raweng.pagemapper.pagemappersdk.views.components.carousel.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.base.CommonLaunchedEffect
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.viewmodel.CarouselViewModel

@Composable
internal fun RenderCarouselView(
    pageMapperViewModel: PageMapperViewModel,
    carouselViewModel: CarouselViewModel,
    liveGameViewModel: LiveGameViewModel? = null,
    dependency: InternalComponentDependency,
    listener: ComponentClickListener? = null
) {
    val uiState = carouselViewModel.uiStateLiveData.observeAsState()

    LaunchedEffect(Unit) {
        if (liveGameViewModel != null && !dependency.dependency.gameId.isNullOrEmpty()) {
            carouselViewModel.fetchLiveGamePlayByPlay(liveGameViewModel)
        }
    }

    CommonLaunchedEffect(
        pageMapperViewModel,
        dependency,
        uiState.value
    )

    uiState.value?.loading?.let {
        if (it) {
            Text(text = "Game carousel Loading...")
        }
    }

    uiState.value?.data?.let {
        HeroCardCarouselVideo(
            responseModel = it,
            carouselViewModel = carouselViewModel,
            carouselClickListener = { uid ->
                val responseDataModel =
                    carouselViewModel.getGameStatsMapper().getResponseDataModel()
                        .copy(clickedData = uid)
                listener?.onClickedComponent(
                    responseDataModel,
                    ClickEventType.CAROUSEL_CLICK_LISTENER
                )
            },
            subHeaderClickListener = { uid ->
                val responseDataModel =
                    carouselViewModel.getGameStatsMapper().getResponseDataModel()
                        .copy(clickedData = uid)
                listener?.onClickedComponent(
                    responseDataModel,
                    ClickEventType.CAROUSEL_CLICK_LISTENER
                )
            }
        )
    }
}