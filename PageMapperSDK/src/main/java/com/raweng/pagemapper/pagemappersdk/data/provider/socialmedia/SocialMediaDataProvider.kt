package com.raweng.pagemapper.pagemappersdk.data.provider.socialmedia

import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.CMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.socialmedia.CMSSocialMediaListUseCase
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.views.components.socialmedia.domain.SocialMediaListResponse

class SocialMediaDataProvider(
    private val item: DynamicScreenResponse.Component,
    private val isNetworkOnly: Boolean
) {

    suspend fun getData(): ResponseDataModel {
        val repository = CMSEntryRepository<SocialMediaListResponse>()
        val useCase = CMSSocialMediaListUseCase(item, repository)
        val mFinalData = useCase.execute(
            contentType = item.contentModelUid.orEmpty(),
            entryId = item.contentEntryUid.orEmpty(),
            null,
            isNetworkOnly,
        )
        return mFinalData
    }
}