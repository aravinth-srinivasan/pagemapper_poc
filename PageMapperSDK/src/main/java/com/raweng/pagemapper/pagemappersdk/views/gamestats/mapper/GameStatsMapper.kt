package com.raweng.pagemapper.pagemappersdk.views.gamestats.mapper

import com.raweng.dfe.models.gameleader.DFEGameLeaderModel
import com.raweng.dfe.models.player.DFEPlayerModel
import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewDataModel
import com.raweng.nba_components_android.components.gamestatscard.model.GameStatsCardDataModel
import com.raweng.nba_components_android.components.gamestatscard.model.GameStatsCardViewDataModel
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.views.gamestats.domain.GameStatsResponseAndStateModel
import com.raweng.pagemapper.pagemappersdk.views.gamestats.domain.GameStatsCardResponse

private const val POINT_LEADER = "POINTS LEADER"
private const val REBOUNDS_LEADER = "REBOUNDS LEADER"
private const val ASSISTS_LEADER = "ASSISTS LEADER"
private const val BLOCKS_LEADER = "BLOCKS LEADER"
private const val STEALS_LEADER = "STEALS LEADER"
class GameStatsMapper {

    private lateinit var mGameStatsCardViewDataModel: GameStatsCardViewDataModel
    private lateinit var response: GameStatsCardResponse
    private lateinit var gameLeaderAndPlayersModel: GameStatsResponseAndStateModel

    private var players: List<DFEPlayerModel>? = null
    private var gameLeaders: List<DFEGameLeaderModel>? = null
    private var storedPlayers: HashMap<String, DFEPlayerModel> = hashMapOf()
    private var appTeamId: String? = null


    fun setLiveResponse(
        gameLeaders: List<DFEGameLeaderModel>?
    ) {
        this.appTeamId = PageMapperSDK.getNBAModel()?.team_id
        this.players = gameLeaderAndPlayersModel.players
        this.gameLeaders = gameLeaders
        players?.forEach {
            storedPlayers[it.playerid] = it
        }
    }

    fun setGameStatsCardResponse(response: GameStatsCardResponse) {
        this.response = response
    }

    fun setGameLeadersAndPlayers(gameLeaderAndPlayersModel: GameStatsResponseAndStateModel) {
        this.gameLeaderAndPlayersModel = gameLeaderAndPlayersModel
        if (gameLeaderAndPlayersModel.gameLeader.isNotEmpty())  {
            setLiveResponse(gameLeaderAndPlayersModel.gameLeader)
        }
    }


    fun prepareGameStatsCardViewDataModel() {
        val trailingIcon = response.ctaButton?.icon?.url
        mGameStatsCardViewDataModel = GameStatsCardViewDataModel(
            gameStatsCardList = buildGameStatsCardDataModelList(),
            showStatsDetails = false,
            inActiveText = response.noStatsMessage,
            trailingCardIcon = ImageViewDataModel(imageUrl = trailingIcon),
            trailingCardText = response.ctaButton?.title,
            isTrailingCardVisible = false,
        )
    }


    fun getGameStatsCardViewDataModel(): GameStatsCardViewDataModel {
        return mGameStatsCardViewDataModel
    }

    fun buildGameStatsCardDataModelList(): List<GameStatsCardDataModel?> {
        return if (gameLeaders.isNullOrEmpty()) {
            getDefaultGameStatsCardDataModelList()
        } else {
            getGameStatsCardDataModelList()
        }

    }

    private fun getDefaultGameStatsCardDataModelList(): List<GameStatsCardDataModel> {
        return listOf(
            GameStatsCardDataModel(id = "1", POINT_LEADER),
            GameStatsCardDataModel(id = "2", REBOUNDS_LEADER),
            GameStatsCardDataModel(id = "3", ASSISTS_LEADER),
            GameStatsCardDataModel(id = "4", BLOCKS_LEADER),
            GameStatsCardDataModel(id = "5", STEALS_LEADER),
        )
    }

    private fun getGameStatsCardDataModelList(): List<GameStatsCardDataModel> {
        val appTeamLeaders = gameLeaders?.find { it.teamid == appTeamId }
        return listOf(
            buildGameStatsCardDataModel(item = appTeamLeaders, POINT_LEADER),
            buildGameStatsCardDataModel(item = appTeamLeaders, REBOUNDS_LEADER),
            buildGameStatsCardDataModel(item = appTeamLeaders, ASSISTS_LEADER),
            buildGameStatsCardDataModel(item = appTeamLeaders, BLOCKS_LEADER),
            buildGameStatsCardDataModel(item = appTeamLeaders, STEALS_LEADER),
        )
    }

    private fun buildGameStatsCardDataModel(
        item: DFEGameLeaderModel?,
        type: String
    ): GameStatsCardDataModel {
        return GameStatsCardDataModel(
            id = item?.uid,
            type = type,
            points = getPoints(type, item),
            playerName = getPlayersName(type, item).uppercase(),
            playerImage = ImageViewDataModel(imageUrl = getPlayersImage(type, item))
        )
    }

    private fun getPoints(type: String, item: DFEGameLeaderModel?): String {
        return when (type) {
            POINT_LEADER -> "${item?.points?.firstOrNull()?.value ?: 0} PTS"
            REBOUNDS_LEADER -> "${item?.rebounds?.firstOrNull()?.value ?: 0}"
            ASSISTS_LEADER -> "${item?.assists?.firstOrNull()?.value ?: 0}"
            BLOCKS_LEADER -> "${item?.blocks?.firstOrNull()?.value ?: 0}"
            STEALS_LEADER -> "${item?.steals?.firstOrNull()?.value ?: 0}"
            else -> {
                ""
            }
        }
    }

    private fun getPlayersName(type: String, item: DFEGameLeaderModel?): String {
        return when (type) {
            POINT_LEADER -> getPlayerNameFromHashMap(item?.points?.firstOrNull()?.personId.orEmpty())
            REBOUNDS_LEADER -> getPlayerNameFromHashMap(item?.rebounds?.firstOrNull()?.personId.orEmpty())
            ASSISTS_LEADER -> getPlayerNameFromHashMap(item?.assists?.firstOrNull()?.personId.orEmpty())
            BLOCKS_LEADER -> getPlayerNameFromHashMap(item?.blocks?.firstOrNull()?.personId.orEmpty())
            STEALS_LEADER -> getPlayerNameFromHashMap(item?.steals?.firstOrNull()?.personId.orEmpty())
            else -> {
                ""
            }
        }
    }

    private fun getPlayersImage(type: String, item: DFEGameLeaderModel?): String {
        return when (type) {
            POINT_LEADER -> getPlayerImageFromHashMap(item?.points?.firstOrNull()?.personId.orEmpty())
            REBOUNDS_LEADER -> getPlayerImageFromHashMap(item?.rebounds?.firstOrNull()?.personId.orEmpty())
            ASSISTS_LEADER -> getPlayerImageFromHashMap(item?.assists?.firstOrNull()?.personId.orEmpty())
            BLOCKS_LEADER -> getPlayerImageFromHashMap(item?.blocks?.firstOrNull()?.personId.orEmpty())
            STEALS_LEADER -> getPlayerImageFromHashMap(item?.steals?.firstOrNull()?.personId.orEmpty())
            else -> {
                ""
            }
        }
    }

    private fun getPlayerNameFromHashMap(playerId: String): String {
        return if (storedPlayers.containsKey(playerId)) {
            return storedPlayers[playerId]?.lastName.orEmpty()
        } else {
            ""
        }
    }

    private fun getPlayerImageFromHashMap(playerId: String): String {
        return if (storedPlayers.containsKey(playerId)) {
            return storedPlayers[playerId]?.headshotImageUrl.orEmpty()
        } else {
            ""
        }
    }
}