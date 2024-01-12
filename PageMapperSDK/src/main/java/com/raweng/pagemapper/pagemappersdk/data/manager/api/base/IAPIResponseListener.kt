package com.raweng.pagemapper.pagemappersdk.data.manager.api.base

interface IAPIResponseListener<T> {
    fun onSuccess(data : List<T>?)
    fun onFailure(error: Error)
}