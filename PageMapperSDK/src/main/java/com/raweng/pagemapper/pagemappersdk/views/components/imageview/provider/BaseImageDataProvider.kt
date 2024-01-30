package com.raweng.pagemapper.pagemappersdk.views.components.imageview.provider

import com.raweng.pagemapper.pagemappersdk.data.provider.base.BaseProvider
import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.CMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.base.CMSBaseUseCase
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.extension.toImageViewDataModel
import com.raweng.pagemapper.pagemappersdk.type.ContentType
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.domain.BaseImageResponse

class BaseImageDataProvider(
    private val dependency: InternalComponentDependency
) : BaseProvider(dependency) {

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
        val useCase = CMSBaseUseCase(dependency.item, repository, BaseImageResponse::class.java)
        val response = useCase.execute(isNetworkOnly = dependency.isNetworkOnly)
        return ResponseDataModel(
            apiResponse = response,
            convertedData = response?.toImageViewDataModel()
        )
    }

    private fun fetchDFEEntry(): ResponseDataModel {
        return getEmptyResponseDataModel()
    }
}