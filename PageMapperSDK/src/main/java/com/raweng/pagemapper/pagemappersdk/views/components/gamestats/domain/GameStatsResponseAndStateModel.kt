package com.raweng.pagemapper.pagemappersdk.views.components.gamestats.domain

import com.raweng.dfe.models.gameleader.DFEGameLeaderModel
import com.raweng.dfe.models.player.DFEPlayerModel
import com.raweng.nba_components_android.components.gamestatscard.model.GameStatsCardDataModel

data class GameStatsResponseAndStateModel(
    var gameLeader: List<DFEGameLeaderModel>,
    var players: List<DFEPlayerModel>,
    var isLiveGame: Boolean = false,
    var updatedGameStats: List<GameStatsCardDataModel?> = mutableListOf(),
)