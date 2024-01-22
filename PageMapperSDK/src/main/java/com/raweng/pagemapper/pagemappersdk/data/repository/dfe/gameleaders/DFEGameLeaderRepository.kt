package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.gameleaders

import com.raweng.dfe.models.gameleader.DFEGameLeaderModel
import com.raweng.dfe.models.player.DFEPlayerModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.pagemapper.pagemappersdk.data.api.DFEApiManager
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFERequest

class DFEGameLeaderRepository : IDFEGameLeaderRepository {
    override suspend fun fetchPlayerList(request: DFERequest): List<DFEPlayerModel> {
        return try {
            DFEApiManager.fetchPlayerList(request, RequestType.NetworkElseDatabase)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun fetchGameLeaders(
        teamId: String?,
        gameId: String?
    ): List<DFEGameLeaderModel> {
        return try {
            return DFEApiManager.fetchGameLeaders(
                teamId = teamId,
                gameId = gameId,
                requestType = RequestType.NetworkElseDatabase
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}