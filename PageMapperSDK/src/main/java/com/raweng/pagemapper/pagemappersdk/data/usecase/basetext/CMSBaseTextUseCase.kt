package com.raweng.pagemapper.pagemappersdk.data.usecase.basetext

import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.ICMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.domain.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.components.BaseTextResponse
import com.raweng.pagemapper.pagemappersdk.mapper.extension.toTextString
import com.raweng.pagemapper.pagemappersdk.utils.convertJsonObjectToModel

class CMSBaseTextUseCase(
    private val item: DynamicScreenResponse.Component,
    private val repository: ICMSEntryRepository<BaseTextResponse>
) {

    suspend fun execute(
        contentType: String,
        entryId: String,
        includeReference: Array<String>? = null,
        isNetworkOnly: Boolean = true
    ): ResponseDataModel {
        val apiResponse = fetchApiContent(contentType, entryId, includeReference, isNetworkOnly)
        return ResponseDataModel(
            apiResponse = apiResponse,
            convertedData = apiResponse?.toTextString()
        )
    }

    private suspend fun fetchApiContent(
        contentType: String,
        entryId: String,
        includeReference: Array<String>? = null,
        isNetworkOnly: Boolean = true
    ): BaseTextResponse? {
        return if (item.contentEntryData == null || item.contentEntryData?.isEmpty == true) {
            repository.fetchCMSEntry(contentType, entryId, includeReference, isNetworkOnly)
        } else {
            convertJsonObjectToModel<BaseTextResponse>(item.contentEntryData)
        }
    }
}