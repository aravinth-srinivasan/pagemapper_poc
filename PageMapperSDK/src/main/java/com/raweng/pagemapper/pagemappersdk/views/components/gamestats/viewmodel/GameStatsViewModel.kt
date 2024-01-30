package com.raweng.pagemapper.pagemappersdk.views.components.gamestats.viewmodel

import androidx.lifecycle.viewModelScope
import com.raweng.nba_components_android.components.gamestatscard.model.GameStatsCardDataModel
import com.raweng.nba_components_android.components.gamestatscard.viewmodel.GameStatsCardViewModel
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.utils.BaseViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.mapper.GameStatsMapper
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.provider.GameStatsDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class GameStatsViewModel(private val provider: GameStatsDataProvider) :
    BaseViewModel(provider) {

    private lateinit var componentViewModel: GameStatsCardViewModel

    fun setComponentViewModel(componentViewModel: GameStatsCardViewModel) {
        this.componentViewModel = componentViewModel
    }

    fun getGameStatsMapper(): GameStatsMapper {
        return provider.getGameStatsMapper()
    }


    fun fetchLiveGameLeaders(viewModel: LiveGameViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            viewModel.getDFEGameLeadersLiveFlow().collect {
                if (!it.isNullOrEmpty()) {
                    val res = uiStateLiveData.value?.data
                    if (res != null) {
                        provider.getGameStatsMapper().setLiveResponse(it)
                        provider.getGameStatsMapper().prepareGameStatsCardViewDataModel()
                        if (this@GameStatsViewModel::componentViewModel.isInitialized) {
                            withContext(Dispatchers.Main) {
                                updateStats(
                                    provider.getGameStatsMapper().buildGameStatsCardDataModelList()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateStats(
        updatedList: List<GameStatsCardDataModel?>
    ) {
        if (this::componentViewModel.isInitialized) {
            componentViewModel.updateTrailingCard(true)
            componentViewModel.updateShowStats(true)
            componentViewModel.updateStats(updatedList)
        }
    }

}