package com.raweng.pagemapper.pagemappersdk.domain

data class ResponseDataModel(
    var apiResponse: Any?,
    var convertedData: Any?,
    var item: DynamicScreenResponse.Component? = null,
    var cmsResponse: Any? = null
)