package com.raweng.pagemapper.pagemappersdk.livegame

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raweng.dfe.models.gamedetail.DFEGameDetailModel
import com.raweng.dfe.models.gameleader.DFEGameLeaderModel
import com.raweng.dfe.models.gameplayerlog.DFEGamePlayerLogModel
import com.raweng.dfe.models.playbyplay.DFEPlayByPlayModel
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.livegame.sdk.LiveGameDataStream
import kotlinx.coroutines.flow.*


class LiveGameViewModel : ViewModel(),
    DefaultLifecycleObserver {

    private var gameId: String = ""
    private var gameController: String = ""

    /************************** LiveGameDataStream ******************************/

    private lateinit var liveGameDataStream: LiveGameDataStream

    /************************** Game Id Mutable And LiveData ******************************/

    private val gameIdMutable: MutableLiveData<String> = MutableLiveData("")
    private val gameIdLiveData: LiveData<String> = gameIdMutable


    /************************** fetch Principle Api ******************************/

    fun syncLiveGame() {

    }

    fun setGameId(gameId: String) {
        this.gameId = gameId
        onInitializeGame(gameId)
    }

    fun setGameController(gameController: String) {
        this.gameController = gameController
    }


    fun postGameId(gameId: String) {
        gameIdMutable.postValue(gameId)
        onInitializeGame(gameId)
    }

    fun getGameIdLiveData(): LiveData<String> {
        return gameIdLiveData
    }


    private fun onInitializeGame(gameId: String) {
        PageMapperSDK.getApplication()?.let {
            liveGameDataStream =
                LiveGameDataStream(it, PageMapperSDK.getNBAModel()?.team_id, gameController)
            liveGameDataStream.setGameId(gameId)
        }
    }


    /************************** Get LiveData ******************************/


    fun getDFEGameLeadersLiveFlow(): Flow<List<DFEGameLeaderModel>?> {
        return liveGameDataStream.getLiveGameLeaderFLow()
    }

    fun getDFEGameDetailsFlow(): Flow<List<DFEGameDetailModel>?> {
        return liveGameDataStream.getLiveGameDetailFlow()
    }

    fun getDFEPlayByPlayLiveFlow(): Flow<List<DFEPlayByPlayModel>?> {
        return liveGameDataStream.getLivePlayByPlayLiveData()
    }

    fun getDFEDFEGamePlayerLogFlow(): Flow<List<DFEGamePlayerLogModel>?> {
        return liveGameDataStream.getLiveGamePlayerLogLiveData()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (this::liveGameDataStream.isInitialized) {
            liveGameDataStream.onResubscribe()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        if (this::liveGameDataStream.isInitialized) {
            liveGameDataStream.unsubscribe()
        }
    }
}