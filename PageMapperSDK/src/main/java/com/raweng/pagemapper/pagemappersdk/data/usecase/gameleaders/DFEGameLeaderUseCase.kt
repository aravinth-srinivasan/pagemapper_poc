package com.raweng.pagemapper.pagemappersdk.data.usecase.gameleaders

import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.gameleaders.DFEGameLeaderRepository
import com.raweng.pagemapper.pagemappersdk.views.gamestats.domain.GameStatsResponseAndStateModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFERequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class DFEGameLeaderUseCase(
    private val repository: DFEGameLeaderRepository,
) {

    suspend fun execute(request: DFERequest, gameId: String): GameStatsResponseAndStateModel {
        val job = CoroutineScope(Dispatchers.IO + Job()).async {
            val players = async { repository.fetchPlayerList(request) }
            val gameLeaders = async {
                repository.fetchGameLeaders(request.teamId, gameId)
                    .filter { it.teamid == request.teamId }
            }
            GameStatsResponseAndStateModel(gameLeaders.await(), players.await())
        }
        return job.await()
    }
}