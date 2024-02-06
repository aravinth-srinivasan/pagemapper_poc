package com.raweng.pagemapper.pagemappersdk.views.components.gamestats.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.nba_components_android.components.gamestatscard.GameStatsCardView
import com.raweng.nba_components_android.components.gamestatscard.viewmodel.GameStatsCardViewModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.extension.toVariant
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.base.CommonLaunchedEffect
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.domain.GameStatsResponseAndStateModel
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.viewmodel.GameStatsViewModel

@Composable
internal fun RenderGameStatsCardView(
    pageMapperViewModel: PageMapperViewModel,
    gameStatsViewModel: GameStatsViewModel,
    liveGameViewModel: LiveGameViewModel? = null,
    dependency: InternalComponentDependency,
    componentEventListener: ComponentEventListener? = null,
) {
    val uiState = gameStatsViewModel.uiStateLiveData.observeAsState()

    LaunchedEffect(Unit) {
        if (liveGameViewModel != null) {
            gameStatsViewModel.fetchLiveGameLeaders(liveGameViewModel)
        }
    }

    CommonLaunchedEffect(
        pageMapperViewModel,
        dependency,
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
            viewModel(factory = factory, key = dependency.item.uid)

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
            style = dependency.item.variant.toVariant(),
            viewModel = componentViewModel,
            onGameStatsTrailingCardClicked = {
                componentEventListener?.onClickedComponent(
                    data = it,
                    eventType = ClickEventType.ON_GAME_STATS_TRAILING_CARD_CLICKED
                )
            }
        )
    }
}