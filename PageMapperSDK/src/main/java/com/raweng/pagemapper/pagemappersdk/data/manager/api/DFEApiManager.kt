package com.raweng.pagemapper.pagemappersdk.data.manager.api

import com.raweng.dfe.DFEManager
import com.raweng.dfe.callback.DFECustomSchemaCallback
import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.dfe.modules.policy.ErrorModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.pagemapper.pagemappersdk.data.manager.api.base.DFEResponseCallback
import com.raweng.pagemapper.pagemappersdk.data.manager.api.base.Error
import com.raweng.pagemapper.pagemappersdk.data.manager.api.base.ErrorType
import com.raweng.pagemapper.pagemappersdk.data.manager.api.base.IAPIResponseListener
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object DFEApiManager {

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


}