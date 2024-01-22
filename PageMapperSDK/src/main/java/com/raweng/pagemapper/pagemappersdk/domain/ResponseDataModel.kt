package com.raweng.pagemapper.pagemappersdk.domain

import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse

data class ResponseDataModel(
    var apiResponse: Any?,
    var convertedData: Any?,
    var item: DynamicScreenResponse.Component? = null,
    var cmsResponse: Any? = null,
    var clickedData: Any? = null
)