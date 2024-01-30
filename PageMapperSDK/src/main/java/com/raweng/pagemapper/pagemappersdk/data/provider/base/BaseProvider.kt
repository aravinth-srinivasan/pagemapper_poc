package com.raweng.pagemapper.pagemappersdk.data.provider.base

import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.type.ContentType
abstract class BaseProvider(private val componentDependency: InternalComponentDependency) : IBaseProvider {
    fun getContentType(): ContentType? {
        return ContentType.fromValue(componentDependency.item.contentType.orEmpty())
    }

    fun getEmptyResponseDataModel(): ResponseDataModel {
        return ResponseDataModel(apiResponse = null, convertedData = null, componentDependency.item)
    }

}