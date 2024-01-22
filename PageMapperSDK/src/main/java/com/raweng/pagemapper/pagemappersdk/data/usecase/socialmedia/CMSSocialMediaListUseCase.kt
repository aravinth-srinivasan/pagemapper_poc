package com.raweng.pagemapper.pagemappersdk.data.usecase.socialmedia

import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.ICMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.views.socialmedia.domain.SocialMediaListResponse
import com.raweng.pagemapper.pagemappersdk.views.socialmedia.extension.toSocialMediaListDataModel
import com.raweng.pagemapper.pagemappersdk.utils.convertJsonObjectToModel

class CMSSocialMediaListUseCase(
    private val item: DynamicScreenResponse.Component,
    private val repository: ICMSEntryRepository<SocialMediaListResponse>
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
            convertedData = apiResponse?.toSocialMediaListDataModel()
        )
    }

    private suspend fun fetchApiContent(
        contentType: String,
        entryId: String,
        includeReference: Array<String>? = null,
        isNetworkOnly: Boolean = true
    ): SocialMediaListResponse? {
        return if (item.contentEntryData == null || item.contentEntryData?.isEmpty == true) {
            repository.fetchCMSEntry(contentType, entryId, includeReference, isNetworkOnly)
        } else {
            convertJsonObjectToModel<SocialMediaListResponse>(item.contentEntryData)
        }
    }
}
