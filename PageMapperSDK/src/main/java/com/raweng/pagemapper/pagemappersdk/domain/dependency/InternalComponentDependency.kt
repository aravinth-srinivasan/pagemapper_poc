package com.raweng.pagemapper.pagemappersdk.domain.dependency

import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse

data class InternalComponentDependency(
    var item: DynamicScreenResponse.Component,
    var gameId: String? = null,
    var parentScreenName: String? = null,
    var isNetworkOnly: Boolean = true
)