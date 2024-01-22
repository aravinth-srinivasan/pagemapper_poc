package com.raweng.pagemapper.pagemappersdk.data.usecase.base

import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.ICMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.utils.convertJsonObjectToModel

class CMSBaseUseCase<T>(
    private val item: DynamicScreenResponse.Component,
    private val repository: ICMSEntryRepository<T>,
    private val clazz: Class<T>,
) {

    suspend fun execute(
        includeReference: Array<String>? = null,
        isNetworkOnly: Boolean = true
    ): T? {
        return fetchApiContent(
            item.contentType.orEmpty(),
            item.contentEntryUid.orEmpty(),
            includeReference,
            isNetworkOnly
        )
    }

    private suspend fun fetchApiContent(
        contentType: String,
        entryId: String,
        includeReference: Array<String>? = null,
        isNetworkOnly: Boolean = true
    ): T? {
        return if (item.contentEntryData == null || item.contentEntryData?.isEmpty == true) {
            repository.fetchCMSEntry(contentType, entryId, includeReference, isNetworkOnly)
        } else {
            convertJsonObjectToModel(item.contentEntryData, clazz)
        }
    }
}