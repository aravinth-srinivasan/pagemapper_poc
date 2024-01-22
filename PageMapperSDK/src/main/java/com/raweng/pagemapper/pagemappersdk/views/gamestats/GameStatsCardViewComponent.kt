package com.raweng.pagemapper.pagemappersdk.views.gamestats

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.nba_components_android.components.gamestatscard.GameStatsCardView
import com.raweng.nba_components_android.components.gamestatscard.viewmodel.GameStatsCardViewModel
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.extension.toVariant
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.utils.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.base.CommonLaunchedEffect
import com.raweng.pagemapper.pagemappersdk.views.gamestats.domain.GameStatsResponseAndStateModel
import com.raweng.pagemapper.pagemappersdk.views.gamestats.provider.GameStatsDataProvider
import com.raweng.pagemapper.pagemappersdk.views.gamestats.viewmodel.GameStatsViewModel

@Composable
internal fun GameStatsCardViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    item: DynamicScreenResponse.Component,
    liveGameViewModel: LiveGameViewModel? = null,
    liveGameId: String? = null,
    listener: ComponentClickListener? = null
) {

    val factory = ViewModelFactory {
        GameStatsViewModel(
            GameStatsDataProvider(
                item,
                PageMapperSDK.getDFERequest(),
                liveGameId.orEmpty()
            )
        )
    }
    val viewModel: GameStatsViewModel = viewModel(factory = factory, key = item.uid.orEmpty())
    LaunchedEffect(Unit) {
        viewModel.fetchResponse()
    }

    RenderGameStatsCardView(
        pageMapperViewModel,
        viewModel,
        liveGameViewModel,
        item,
        listener
    )
}

@Composable
private fun RenderGameStatsCardView(
    pageMapperViewModel: PageMapperViewModel,
    gameStatsViewModel: GameStatsViewModel,
    liveGameViewModel: LiveGameViewModel? = null,
    item: DynamicScreenResponse.Component,
    listener: ComponentClickListener? = null
) {
    val uiState = gameStatsViewModel.uiStateLiveData.observeAsState()

    LaunchedEffect(Unit) {
        Log.e("TAG", "RenderGameStatsCardView: LaunchedEffect")
        if (liveGameViewModel != null) {
            gameStatsViewModel.fetchLiveGameLeaders(liveGameViewModel)
        }
    }

    CommonLaunchedEffect(
        pageMapperViewModel,
        item,
        uiState.value
    )

    uiState.value?.loading?.let {
        if (it) {
            Text(text = "Game Stats Loading...")
        }
    }

    uiState.value?.data?.let {
        val mapper = gameStatsViewModel.getGameStatsMapper()
        val factory = ViewModelFactory {
            GameStatsCardViewModel(
                mapper.getGameStatsCardViewDataModel()
            )
        }
        val componentViewModel: GameStatsCardViewModel =
            viewModel(factory = factory, key = item.uid)

        LaunchedEffect(Unit) {
            gameStatsViewModel.setComponentViewModel(componentViewModel)
            val response = it.apiResponse as GameStatsResponseAndStateModel
            if (response.isLiveGame) {
                gameStatsViewModel.updateStats(
                    response.updatedGameStats
                )
            }
        }

        GameStatsCardView(
            style = item.variant.toVariant(),
            viewModel = componentViewModel,
            onGameStatsTrailingCardClicked = {
                listener?.onClickedComponent(
                    data = it,
                    eventType = ClickEventType.ON_GAME_STATS_TRAILING_CARD_CLICKED
                )
            }
        )
    }
}