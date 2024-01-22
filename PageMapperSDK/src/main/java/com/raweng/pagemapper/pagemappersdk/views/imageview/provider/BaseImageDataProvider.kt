package com.raweng.pagemapper.pagemappersdk.views.imageview.provider

import com.raweng.pagemapper.pagemappersdk.data.provider.base.BaseProvider
import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.CMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.base.CMSBaseUseCase
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.views.imageview.extension.toImageViewDataModel
import com.raweng.pagemapper.pagemappersdk.type.ContentType
import com.raweng.pagemapper.pagemappersdk.views.imageview.domain.BaseImageResponse

class BaseImageDataProvider(
    private val item: DynamicScreenResponse.Component,
    private val isNetworkOnly: Boolean = true
) : BaseProvider(item) {

    override suspend fun getData(): ResponseDataModel {
        val contentType = getContentType()
        return contentType?.let {
            when (it) {
                ContentType.DFEP -> fetchDFEEntry()
                ContentType.CONTENT_STACK -> fetchCMSEntry()
            }
        } ?: getEmptyResponseDataModel()
    }

    private suspend fun fetchCMSEntry(): ResponseDataModel {
        val repository = CMSEntryRepository<BaseImageResponse>()
        val useCase = CMSBaseUseCase(item, repository, BaseImageResponse::class.java)
        val response = useCase.execute(isNetworkOnly = isNetworkOnly)
        return ResponseDataModel(
            apiResponse = response,
            convertedData = response?.toImageViewDataModel()
        )
    }

    private fun fetchDFEEntry(): ResponseDataModel {
        return getEmptyResponseDataModel()
    }
}