package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.gameleaders

import com.raweng.dfe.models.gameleader.DFEGameLeaderModel
import com.raweng.dfe.models.player.DFEPlayerModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFERequest

interface IDFEGameLeaderRepository {
    suspend fun fetchPlayerList(request: DFERequest):List<DFEPlayerModel>
    suspend fun fetchGameLeaders(teamId: String?, gameId: String?):List<DFEGameLeaderModel>

}