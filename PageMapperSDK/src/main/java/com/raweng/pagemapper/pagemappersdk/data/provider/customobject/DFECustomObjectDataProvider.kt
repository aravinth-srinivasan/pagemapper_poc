package com.raweng.pagemapper.pagemappersdk.data.provider.customobject

import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.customobject.DFECustomObjectRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.customobject.DFECustomObjectUseCase
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel

class DFECustomObjectDataProvider(
    private val item: DynamicScreenResponse.Component,
    private val componentDataMap: MutableMap<String, ResponseDataModel>? = null,
    private val className: String
) {

    suspend fun getData(): ResponseDataModel {
        val repository = DFECustomObjectRepository()
        val useCase = DFECustomObjectUseCase(repository)
        val mFinalData = useCase.execute(className)
            .copy(item = item) //TODO need to discuss
        if (componentDataMap != null) {
            componentDataMap[item.uid.orEmpty()] = mFinalData
        }
        return mFinalData
    }
}