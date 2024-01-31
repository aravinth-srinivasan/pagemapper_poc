package com.raweng.pagemapper.pagemappersdk.views.components.gamestats.provider

import com.raweng.pagemapper.pagemappersdk.data.provider.base.BaseProvider
import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.CMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.gameleaders.DFEGameLeaderRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.base.CMSBaseUseCase
import com.raweng.pagemapper.pagemappersdk.data.usecase.gameleaders.DFEGameLeaderUseCase
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.domain.GameStatsResponseAndStateModel
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFERequest
import com.raweng.pagemapper.pagemappersdk.type.ContentType
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.domain.GameStatsCardResponse
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.mapper.GameStatsMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class GameStatsDataProvider(
    private val dependency: InternalComponentDependency,
    private val request: DFERequest,
) : BaseProvider(dependency) {

    private val mapper = GameStatsMapper()
    override suspend fun getData(): ResponseDataModel {
        val contentType = getContentType()
        return contentType?.let {
            when (it) {
                ContentType.DFEP -> fetchDFEEntry()
                ContentType.CONTENT_STACK -> fetchCMSEntry()
            }
        } ?: getEmptyResponseDataModel()
    }

    private suspend fun fetchCMSEntry(): ResponseDataModel {
        val job = CoroutineScope(Dispatchers.IO + Job()).async {
            val extractCMSData = async { getCMSData() }
            val fetchDFE = async { getDFEData() }
            Pair(
                extractCMSData.await(),
                fetchDFE.await()
            )
        }
        val data = job.await()
        return getResponseDataModel(data)
    }

    private fun getResponseDataModel(data: Pair<GameStatsCardResponse?, GameStatsResponseAndStateModel>): ResponseDataModel {
        val response = ResponseDataModel(item = dependency.item, apiResponse = data.second, convertedData = null)
        val dfeResponse = data.second
        val isLiveGame = ((dfeResponse.gameLeader.isNotEmpty()) && (!dependency.dependency.gameId.isNullOrEmpty()))
        mapper.prepareGameStatsCardViewDataModel()
        val mResponse = if (isLiveGame) {
            val liveDataModel = dfeResponse.copy(
                isLiveGame = isLiveGame,
                updatedGameStats = mapper.buildGameStatsCardDataModelList()
            )
            response.copy(apiResponse = liveDataModel, cmsResponse = data.first)
        } else {
            response.copy(cmsResponse = data.first)
        }
        return mResponse
    }

    private fun fetchDFEEntry(): ResponseDataModel {
        return getEmptyResponseDataModel()
    }

    private suspend fun getCMSData(): GameStatsCardResponse? {
        val cmsRepository = CMSEntryRepository<GameStatsCardResponse>()
        val cmsUseCase =
            CMSBaseUseCase(dependency.item, cmsRepository, GameStatsCardResponse::class.java)
        val cmsResponse = cmsUseCase.execute(null, isNetworkOnly = false)
        if (cmsResponse != null) {
            mapper.setGameStatsCardResponse(cmsResponse)
        }
        return cmsResponse
    }

    private suspend fun getDFEData(): GameStatsResponseAndStateModel {
        val dfeRepository = DFEGameLeaderRepository()
        val dfeUseCase = DFEGameLeaderUseCase(dfeRepository)
        val dfeResponse = dfeUseCase.execute(request, dependency.dependency.gameId.orEmpty())
        mapper.setGameLeadersAndPlayers(dfeResponse)
        return dfeResponse
    }

    fun getGameStatsMapper(): GameStatsMapper {
        return mapper
    }
}