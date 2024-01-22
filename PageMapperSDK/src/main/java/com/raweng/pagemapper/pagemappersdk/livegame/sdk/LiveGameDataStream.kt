package com.raweng.pagemapper.pagemappersdk.livegame.sdk

import android.app.Application
import com.raweng.dfe.models.gamedetail.DFEGameDetailModel
import com.raweng.dfe.models.gameleader.DFEGameLeaderModel
import com.raweng.dfe.models.gameplayerlog.DFEGamePlayerLogModel
import com.raweng.dfe.models.playbyplay.DFEPlayByPlayModel
import com.raweng.dfe.modules.realtime.IRealtimeLiveCallback
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private const val DEBOUNCE_TIME_INTERVAL = 1500L

class LiveGameDataStream(context: Application, appTeamId: String?, controller: String) :
    IRealtimeLiveCallback {
    private var liveGameSdk: LiveGameSdk = LiveGameSdk(context, appTeamId)
    private var gameId: String? = null

    init {
        liveGameSdk.subscribeLiveGame(controller, callback = this) {
            asyncLiveGameObserver()
        }
    }

    private var liveGameLeaderStateFlow = MutableStateFlow<List<DFEGameLeaderModel>?>(emptyList())
    private var liveGameDetailStateFlow = MutableStateFlow<List<DFEGameDetailModel>?>(emptyList())
    private var livePlayByPlayStateFlow = MutableStateFlow<List<DFEPlayByPlayModel>?>(emptyList())
    private var liveGamPlayerLogStateFlow =
        MutableStateFlow<List<DFEGamePlayerLogModel>?>(emptyList())

    private var liveGameLeaderFlow: Flow<List<DFEGameLeaderModel>?> =
        liveGameLeaderStateFlow.asStateFlow()
    private var liveGameDetailFlow: Flow<List<DFEGameDetailModel>?> =
        liveGameDetailStateFlow.asStateFlow()
    private var livePlayByPlayFlow: Flow<List<DFEPlayByPlayModel>?> =
        livePlayByPlayStateFlow.asStateFlow()
    private var liveGamPlayerLogFlow: Flow<List<DFEGamePlayerLogModel>?> =
        liveGamPlayerLogStateFlow.asStateFlow()

    private fun asyncLiveGameObserver() {
        observeGameLeaders()
        observeGameDetails()
        observeGamePlayByPlay()
        observeGamePlayerLog()
    }

    @OptIn(FlowPreview::class)
    private fun observeGameDetails() {
        liveGameDetailFlow
            .debounce(DEBOUNCE_TIME_INTERVAL)
            .filter {
                !it.isNullOrEmpty()
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    @OptIn(FlowPreview::class)
    private fun observeGamePlayByPlay() {
        livePlayByPlayFlow
            .debounce(DEBOUNCE_TIME_INTERVAL)
            .filter {
                !it.isNullOrEmpty()
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    @OptIn(FlowPreview::class)
    private fun observeGameLeaders() {
        liveGameLeaderFlow
            .debounce(DEBOUNCE_TIME_INTERVAL)
            .filter {
                !it.isNullOrEmpty()
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    @OptIn(FlowPreview::class)
    private fun observeGamePlayerLog() {
        liveGamPlayerLogFlow
            .debounce(DEBOUNCE_TIME_INTERVAL)
            .filter {
                !it.isNullOrEmpty()
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    fun getLiveGameDetailFlow(): Flow<List<DFEGameDetailModel>?> {
        return liveGameDetailFlow
    }

    fun getLivePlayByPlayLiveData(): Flow<List<DFEPlayByPlayModel>?> {
        return livePlayByPlayFlow
    }

    fun getLiveGameLeaderFLow(): Flow<List<DFEGameLeaderModel>?> {
        return liveGameLeaderFlow
    }

    fun getLiveGamePlayerLogLiveData(): Flow<List<DFEGamePlayerLogModel>?> {
        return liveGamPlayerLogFlow
    }

    fun setGameId(gameId: String) {
        this.gameId = gameId
    }

    fun onResubscribe() {
        liveGameSdk.onResubscribeLiveGame()
    }

    fun unsubscribe() {
        liveGameSdk.unSubscribeLiveGame()
    }

    override fun onReceiveGameDetails(
        p0: String?,
        p1: String?,
        list: MutableList<DFEGameDetailModel>?
    ) {
        if (!list.isNullOrEmpty()) {
            val firstItem = list.firstOrNull()
            if (firstItem != null && firstItem.gameid == gameId) {
                liveGameDetailStateFlow.tryEmit(list)
            }
        }
    }

    override fun onReceivePlayByPlay(
        p0: String?,
        p1: String?,
        list: MutableList<DFEPlayByPlayModel>?
    ) {
        if (!list.isNullOrEmpty()) {
            val mFilter = list.filter { it.gameid == gameId }
            if (mFilter.isNotEmpty()) {
                livePlayByPlayStateFlow.tryEmit(mFilter)
            }
        }
    }

    override fun onReceiveGameLeaders(
        p0: String?,
        p1: String?,
        list: MutableList<DFEGameLeaderModel>?
    ) {
        if (!list.isNullOrEmpty()) {
            val mFilter = list.filter { it.gameid == gameId }
            if (mFilter.isNotEmpty()) {
                liveGameLeaderStateFlow.tryEmit(mFilter)
            }
        }
    }

    override fun onReceiveGamePlayerLog(
        p0: String?,
        p1: String?,
        list: MutableList<DFEGamePlayerLogModel>?
    ) {
        if (!list.isNullOrEmpty()) {
            val mFilter = list.filter { it.gameid == gameId }
            if (mFilter.isNotEmpty()) {
                liveGamPlayerLogStateFlow.tryEmit(mFilter)
            }
        }
    }

}