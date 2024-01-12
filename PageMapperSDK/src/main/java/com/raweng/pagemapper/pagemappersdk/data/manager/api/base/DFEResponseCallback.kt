package com.raweng.pagemapper.pagemappersdk.data.manager.api.base

import com.raweng.dfe.modules.callbacks.DFEResultCallback
import com.raweng.dfe.modules.policy.ErrorModel

open class DFEResponseCallback<T>(private val iApiResponseListener: IAPIResponseListener<T>?): DFEResultCallback() {

    override fun onComplete(list: List<*>?, error: ErrorModel?) {
        try {
            if (error != null) {
                iApiResponseListener?.onFailure(Error(ErrorType.API_ERROR, error.errorMessage))
            } else if (list != null && list.isNotEmpty()) {
                iApiResponseListener?.onSuccess(data = list as List<T>)
            } else {
                iApiResponseListener?.onFailure(Error(ErrorType.NO_DATA))
            }
        } catch (exception: Exception) {
            iApiResponseListener?.onFailure(Error(ErrorType.EXCEPTION, exception.message ?: ""))
        }
    }
}