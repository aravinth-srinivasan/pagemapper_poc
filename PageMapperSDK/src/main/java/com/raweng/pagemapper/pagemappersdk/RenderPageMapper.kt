package com.raweng.pagemapper.pagemappersdk

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.carousel.CarouselViewComponent
import com.raweng.pagemapper.pagemappersdk.views.gamestats.GameStatsCardViewComponent
import com.raweng.pagemapper.pagemappersdk.views.imageview.ImageViewComponent
import com.raweng.pagemapper.pagemappersdk.views.textview.TextViewComponent
import com.raweng.pagemapper.pagemappersdk.widgets.RenderTextWidgets

@Composable
fun RenderPageMapper(
    viewModel: PageMapperViewModel,
    liveGameViewModel: LiveGameViewModel? = null,
    liveGameId: String? = null,
    listener: ComponentClickListener? = null
) {
    val uiState = viewModel.uiStateLiveData.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.initData()
        if (liveGameViewModel != null && !liveGameId.isNullOrEmpty()) {
            liveGameViewModel.setGameId(liveGameId)
        }
    }


    uiState.value?.data?.let {
        Log.e("TAG", "RenderPageMapper:  recompose")
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            LocalRenderView(viewModel, it, liveGameViewModel, liveGameId, listener)
        }
    }

    uiState.value?.loading?.let {
        if (it) {
            RenderTextWidgets("Page Mapper Loading....")
        }
    }
}

@Composable
private fun LocalRenderView(
    viewModel: PageMapperViewModel,
    response: DynamicScreenResponse?,
    liveGameViewModel: LiveGameViewModel? = null,
    liveGameId: String? = null,
    listener: ComponentClickListener? = null
) {

    Log.e("TAG", "LocalRenderView: ")

    response?.components?.forEachIndexed { _, item ->
        when (Components.fromValue(item.value.orEmpty())) {
            Components.NEWS_FEED_VIEW -> TODO()
            Components.HORIZONTAL_LIST -> TODO()
            Components.HERO_CARD_CAROUSEL_VIEW -> {
                CarouselViewComponent(
                    pageMapperViewModel = viewModel,
                    liveGameViewModel = liveGameViewModel,
                    item = item,
                    gameId = liveGameId.orEmpty(),
                    listener = listener
                )
            }

            Components.FEATURED_FEEDS -> TODO()
            Components.IMAGE_VIEW -> {
                ImageViewComponent(
                    pageMapperViewModel = viewModel,
                    item = item,
                    listener = listener
                )
            }

            Components.USER_BANNER -> TODO()
            Components.TEXT_VIEW -> {
                TextViewComponent(
                    viewModel,
                    item
                )
            }

            Components.GAME_STATS_CARD -> {
                GameStatsCardViewComponent(
                    viewModel, item, liveGameViewModel, liveGameId, listener
                )
            }

            Components.SOCIAL_MEDIA_LIST -> TODO()
            Components.GOOGLE_ADS_VIEW -> TODO()
            Components.SCORE_CARD_CAROUSEL -> TODO()
            Components.RECENT_GAMES -> TODO()
            Components.UTILITY_MENU -> TODO()
            else -> {}
        }
    }
}