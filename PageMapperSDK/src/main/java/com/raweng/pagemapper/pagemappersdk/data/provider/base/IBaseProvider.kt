package com.raweng.pagemapper.pagemappersdk.data.provider.base

import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel

interface IBaseProvider {
    suspend fun getData(): ResponseDataModel
}