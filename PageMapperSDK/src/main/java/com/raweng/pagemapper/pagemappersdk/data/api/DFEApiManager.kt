package com.raweng.pagemapper.pagemappersdk.data.api

import com.google.gson.JsonObject
import com.raweng.dfe.DFEManager
import com.raweng.dfe.WSCType
import com.raweng.dfe.callback.DFECustomSchemaCallback
import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.dfe.models.feed.DFEFeedModel
import com.raweng.dfe.models.gameleader.DFEGameLeaderModel
import com.raweng.dfe.models.player.DFEPlayerModel
import com.raweng.dfe.models.schedule.DFEScheduleModel
import com.raweng.dfe.models.wsc.WSCFeeds
import com.raweng.dfe.modules.policy.ErrorModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.dfe.utils.FeedType
import com.raweng.pagemapper.pagemappersdk.data.api.base.DFEResponseCallback
import com.raweng.pagemapper.pagemappersdk.data.api.base.Error
import com.raweng.pagemapper.pagemappersdk.data.api.base.ErrorType
import com.raweng.pagemapper.pagemappersdk.data.api.base.IAPIResponseListener
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFERequest
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object DFEApiManager {

    const val KEY_FIELDS = "fields"
    const val KEY_FREE_FROM = "free_from"
    const val KEY_FEED_ID = "feed_id"
    const val KEY_WALLPAPER_ID = "wallpaper_id"
    const val KEY_WALLPAPER_DATA = "wallpaper_data"
    const val KEY_FEED_TYPE = "feed_type"
    const val KEY_CATEGORY = "category"
    const val KEY_GAME_ID = "gameID"
    const val KEY_PID = "pid"
    const val KEY_TID = "tid"
    const val KEY_CID = "cid"
    const val KEY_FROM_DATE = "from_date"
    const val KEY_TO_DATE = "to_date"
    const val KEY_SORT_ORDER = "sort_order"
    const val KEY_SORT_BY = "sort_by"
    const val KEY_SKIP = "skip"
    const val KEY_LIMIT = "limit"

    suspend fun fetchConfig(fields: String? = null, requestType: RequestType): DFEConfigModel =
        suspendCancellableCoroutine {
            val responseListener = object : IAPIResponseListener<DFEConfigModel> {
                override fun onSuccess(data: List<DFEConfigModel>?) {
                    data?.let { mData ->
                        it.resume(mData[0])
                    } ?: it.resumeWithException(Error(ErrorType.NO_DATA))
                }

                override fun onFailure(error: Error) {
                    it.resumeWithException(error)
                }
            }
            DFEManager.getInst().queryManager.getConfig(
                fields, requestType, DFEResponseCallback(responseListener)
            )
        }


    suspend fun fetchCustomObject(
        classname: String?,
    ): JSONObject? = suspendCancellableCoroutine {
        DFEManager.getInst().queryManager.getCustomObject(
            classname,
            0,
            0,
            "",
            "",
            object : DFECustomSchemaCallback() {
                override fun onComplete(data: JSONObject?, error: ErrorModel?) {
                    data?.let { mData ->
                        it.resume(mData)
                    }

                    error?.let { mError ->
                        it.resumeWithException(Exception(mError.errorMessage))
                    }
                }
            })
    }


    suspend fun fetchScheduleList(
        request: DFERequest,
        fromDate: String? = "",
        toDate: String? = "",
        requestType: RequestType
    ): List<DFEScheduleModel> = suspendCancellableCoroutine {
        val responseListener = object : IAPIResponseListener<DFEScheduleModel> {
            override fun onSuccess(data: List<DFEScheduleModel>?) {
                it.resume(data ?: arrayListOf())
            }

            override fun onFailure(error: Error) {
                it.resumeWithException(error)
            }
        }
        DFEManager.getInst().queryManager.getGameSchedules(
            request.fields,
            request.teamId,
            fromDate,
            toDate,
            request.skip,
            request.limit,
            request.year,
            request.leagueId,
            request.seasonId,
            request.sortBy,
            request.sortOrder,
            requestType,
            DFEResponseCallback(responseListener)
        )
    }


    suspend fun fetchPlayerList(
        request: DFERequest, requestType: RequestType = RequestType.NetworkElseDatabase
    ): List<DFEPlayerModel> = suspendCancellableCoroutine {
        val responseListener = object : IAPIResponseListener<DFEPlayerModel> {
            override fun onSuccess(data: List<DFEPlayerModel>?) {
                it.resume(data ?: arrayListOf())
            }

            override fun onFailure(error: Error) {
                it.resumeWithException(error)
            }
        }
        DFEManager.getInst().queryManager.getPlayers(
            request.fields,
            request.teamId,
            request.year,
            request.leagueId,
            request.seasonId,
            request.sortBy,
            request.sortOrder,
            request.skip,
            request.limit,
            requestType,
            DFEResponseCallback(responseListener)
        )
    }

    suspend fun fetchGameLeaders(
        fields: String? = null, teamId: String?, gameId: String?, requestType: RequestType
    ): List<DFEGameLeaderModel> = suspendCancellableCoroutine {
        val responseListener = object : IAPIResponseListener<DFEGameLeaderModel> {
            override fun onSuccess(data: List<DFEGameLeaderModel>?) {
                it.resume(data ?: arrayListOf())
            }

            override fun onFailure(error: Error) {
                it.resumeWithException(error)
            }
        }
        DFEManager.getInst().queryManager.getGameLeaders(
            fields, gameId, teamId, requestType, DFEResponseCallback(responseListener)
        )
    }

    suspend fun fetchWSCVideos(
        requestType: RequestType,
        gid: String,
        limit: Int,
    ): List<WSCFeeds> = suspendCancellableCoroutine {
        val responseListener = object : IAPIResponseListener<WSCFeeds> {
            override fun onSuccess(data: List<WSCFeeds>?) {
                it.resume(data ?: arrayListOf())
            }

            override fun onFailure(error: Error) {
                it.resumeWithException(error)
            }
        }
        val jsonObject = JsonObject()
        jsonObject.addProperty("gid", gid)
        DFEManager.getInst().queryManager.getWSCFeeds(
            null,
            WSCType.PBP,
            jsonObject,
            "publish_date",
            "DESC",
            0,
            limit,
            requestType,
            DFEResponseCallback(responseListener)
        )
    }

    suspend fun fetchFeedsList(
        requestType: RequestType, params: Map<String, Any>
    ): List<DFEFeedModel> = suspendCancellableCoroutine {
        val responseListener = object : IAPIResponseListener<DFEFeedModel> {
            override fun onSuccess(data: List<DFEFeedModel>?) {
                it.resume(data ?: arrayListOf())
            }

            override fun onFailure(error: Error) {
                it.resumeWithException(error)
            }
        }
        try {
            DFEManager.getInst().queryManager.getFeeds(
                params[KEY_FIELDS] as String?,
                params[KEY_FREE_FROM] as String?,
                if (params[KEY_FEED_TYPE] == null) null else params[KEY_FEED_TYPE] as FeedType?,
                params[KEY_CATEGORY] as String?,
                params[KEY_GAME_ID] as String?,
                params[KEY_PID] as String?,
                params[KEY_TID] as String?,
                params[KEY_CID] as String?,
                params[KEY_FROM_DATE] as String?,
                params[KEY_TO_DATE] as String?,
                params[KEY_SORT_BY] as String?,
                params[KEY_SORT_ORDER] as String?,
                (if (params[KEY_SKIP] == null) -1 else params[KEY_SKIP] as Int?)!!,
                (if (params[KEY_LIMIT] == null) -1 else params[KEY_LIMIT] as Int?)!!,
                requestType,
                DFEResponseCallback(responseListener)
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}