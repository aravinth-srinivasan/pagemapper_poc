package com.raweng.pagemapper.pagemappersdk.views.components.textview.provider

import com.raweng.pagemapper.pagemappersdk.data.provider.base.BaseProvider
import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.CMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.base.CMSBaseUseCase
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.views.components.textview.extension.toTextString
import com.raweng.pagemapper.pagemappersdk.type.ContentType
import com.raweng.pagemapper.pagemappersdk.views.components.textview.domain.BaseTextResponse

class BaseTextDataProvider(
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
        val repository = CMSEntryRepository<BaseTextResponse>()
        val useCase = CMSBaseUseCase(dependency.item, repository, BaseTextResponse::class.java)
        val cmsResponse = useCase.execute(
            null,
            dependency.isNetworkOnly,
        )
        return ResponseDataModel(
            item = dependency.item,
            apiResponse = cmsResponse,
            convertedData = cmsResponse?.toTextString()
        )
    }

    private fun fetchDFEEntry(): ResponseDataModel {
        return getEmptyResponseDataModel()
    }

}