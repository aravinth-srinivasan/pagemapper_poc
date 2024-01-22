package com.raweng.pagemapper.pagemappersdk.views.carousel

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.utils.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.base.CommonLaunchedEffect
import com.raweng.pagemapper.pagemappersdk.views.carousel.provider.CarouselViewDataProvider
import com.raweng.pagemapper.pagemappersdk.views.carousel.viewmodel.CarouselViewModel
import com.raweng.pagemapper.pagemappersdk.views.carousel.widgets.HeroCardCarouselVideo

@Composable
internal fun CarouselViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    liveGameViewModel: LiveGameViewModel? = null,
    item: DynamicScreenResponse.Component,
    gameId: String,
    listener: ComponentClickListener? = null
) {
    val factory = ViewModelFactory {
        CarouselViewModel(CarouselViewDataProvider(item, gameId))
    }
    val viewModel: CarouselViewModel = viewModel(factory = factory, key = item.uid.orEmpty())
    LaunchedEffect(Unit) {
        viewModel.fetchResponse()
    }

    RenderCarouselView(
        pageMapperViewModel,
        viewModel,
        liveGameViewModel,
        gameId,
        item,
        listener
    )

}

@Composable
private fun RenderCarouselView(
    pageMapperViewModel: PageMapperViewModel,
    carouselViewModel: CarouselViewModel,
    liveGameViewModel: LiveGameViewModel? = null,
    liveGameId: String = "",
    item: DynamicScreenResponse.Component,
    listener: ComponentClickListener? = null
) {
    val uiState = carouselViewModel.uiStateLiveData.observeAsState()

    LaunchedEffect(Unit) {
        Log.e("TAG", "RenderCarouselView: LaunchedEffect")
        if (liveGameViewModel != null && liveGameId.isNotEmpty()) {
            carouselViewModel.fetchLiveGamePlayByPlay(liveGameViewModel)
        }
    }

    CommonLaunchedEffect(
        pageMapperViewModel,
        item,
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
                Log.e("TAG", "RenderCarouselView: "+uid, )
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