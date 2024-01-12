package com.raweng.pagemapper.pagemappersdk.data.provider.basetext

import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.CMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.basetext.CMSBaseTextUseCase
import com.raweng.pagemapper.pagemappersdk.domain.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.components.BaseTextResponse

class BaseTextDataProvider(
    private val item: DynamicScreenResponse.Component,
    private val componentDataMap: MutableMap<String, ResponseDataModel>? = null,
    private val isNetworkOnly: Boolean = true
) {

    suspend fun getData(): ResponseDataModel {
        val repository = CMSEntryRepository<BaseTextResponse>()
        val useCase = CMSBaseTextUseCase(item, repository)
        val mFinalData = useCase.execute(
            contentType = item.contentModelUid.orEmpty(),
            entryId = item.contentEntryUid.orEmpty(),
            null,
            isNetworkOnly,
        )
        if (componentDataMap != null) {
            componentDataMap[item.uid.orEmpty()] = mFinalData
        }
        return mFinalData
    }
}