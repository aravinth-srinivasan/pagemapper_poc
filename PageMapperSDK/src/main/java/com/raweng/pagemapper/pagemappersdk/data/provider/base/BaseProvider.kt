package com.raweng.pagemapper.pagemappersdk.data.provider.base

import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.type.ContentType
abstract class BaseProvider(private val item: DynamicScreenResponse.Component) : IBaseProvider {
    fun getContentType(): ContentType? {
        return ContentType.fromValue(item.contentType.orEmpty())
    }

    fun getEmptyResponseDataModel(): ResponseDataModel {
        return ResponseDataModel(apiResponse = null, convertedData = null, item)
    }

}