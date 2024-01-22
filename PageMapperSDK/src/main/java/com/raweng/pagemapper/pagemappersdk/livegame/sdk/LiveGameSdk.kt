package com.raweng.pagemapper.pagemappersdk.livegame.sdk

import android.app.Application
import android.text.TextUtils
import com.raweng.dfe.DFEManager
import com.raweng.dfe.models.schedule.DFECurrentTeamScheduleCallback
import com.raweng.dfe.models.schedule.DFEScheduleModel
import com.raweng.dfe.models.topics.DFETopic
import com.raweng.dfe.modules.callbacks.DFEResultCallback
import com.raweng.dfe.modules.policy.ErrorModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.dfe.modules.realtime.DFERealtimeManager
import com.raweng.dfe.modules.realtime.IRealtimeLiveCallback
import com.raweng.dfe.modules.realtime.ResponseType
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.type.GameStatus
import com.raweng.pagemapper.pagemappersdk.utils.GameUtils
import java.util.Locale

typealias OnSubscribedLiveGameCallback = ((() -> Unit))

internal class LiveGameSdk(
    private val context: Application,
    private val appTeamId: String?,
) {

    private var topics: List<DFETopic?>? = listOf()
    private var onSubscribedLiveGame: OnSubscribedLiveGameCallback? = null
    private var callback: IRealtimeLiveCallback? = null
    private var controller: String = "GameMainFragment"
    private var channels: String? = null
    private var isSubscribed = false

    init {
        checkForLiveGameSubscription()
    }

    private fun checkForLiveGameSubscription() {
        DFEManager.getInst().queryManager.getScorecard(
            appTeamId,
            object : DFECurrentTeamScheduleCallback() {
                override fun onCompletion(
                    arrayList: ArrayList<DFEScheduleModel>?,
                    i: Int,
                    error: Error?
                ) {
                    if (arrayList != null) {
                        try {
                            val schedule = arrayList[i]
                            val gameStatus = GameUtils.getGameStatus(schedule)
                            if (gameStatus == GameStatus.LIVE_GAME || gameStatus == GameStatus.FUTURE_GAME) {
                                subscribePubnubForGame()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        )
    }

    fun subscribePubnubForGame() {
        DFEManager.getInst().queryManager.getTopics(
            null,
            null,
            -1,
            -1,
            RequestType.Network,
            object : DFEResultCallback() {
                override fun onComplete(list: List<*>?, errorModel: ErrorModel?) {
                    if (list != null) {
                        initializePubnub(list as List<DFETopic?>?)
                    }
                }
            }
        )
    }

    private fun initializePubnub(topics: List<DFETopic?>?) {
        this.topics = topics
        DFEManager.getInst().realtimeManager.initialize(context)
        findChannels()
        val key = PageMapperSDK.getPubNubModel()?.subscribe_key.orEmpty()
        DFEManager.getInst().realtimeManager
            .setKeyAndChannels(key, topics)
        DFEManager.getInst().queryManager.realtimeManager
            .subscribeLiveGame(ResponseType.LIVE_GAME, controller, callback)
        isSubscribed = true
        onSubscribedLiveGame?.invoke()
    }

    private fun findChannels() {
        if (topics != null && (topics?.size ?: 0) > 0) {
            topics?.forEachIndexed { i, dfeTopic ->
                if (dfeTopic != null && !TextUtils.isEmpty(dfeTopic.service) && dfeTopic.service.lowercase(
                        Locale.getDefault()
                    ) == "realtime"
                ) {
                    channels =
                        if (TextUtils.isEmpty(channels)) (if (!TextUtils.isEmpty(dfeTopic.key)) dfeTopic.key else channels) else if (!TextUtils.isEmpty(
                                dfeTopic.key
                            )
                        ) channels + "," + dfeTopic.key else channels
                }
            }
        }
    }

    fun subscribeLiveGame(
        controller: String,
        callback: IRealtimeLiveCallback,
        onSubscribedLiveGameCallback: OnSubscribedLiveGameCallback
    ) {
        this.controller = controller
        this.callback = callback
        this.onSubscribedLiveGame = onSubscribedLiveGameCallback
    }

    fun onResubscribeLiveGame() {
        if (!isSubscribed && !channels.isNullOrEmpty()) {
            DFEManager.getInst().realtimeManager.realtimeListner.subscribeChannels(
                PageMapperSDK.PUBNUB_SUB_KEY,
                channels
            )
            isSubscribed = true
        }
    }

    fun unSubscribeLiveGame() {
        /*if (isSubscribed && !channels.isNullOrEmpty()) {
            DFEManager.getInst().realtimeManager.realtimeListner.unsubscribeChannels(
                PageMapperSDK.PUBNUB_SUB_KEY,
                channels?.split(",")?.toTypedArray() ?: arrayOf()
            )
            isSubscribed = false
        }*/
    }
}