package com.raweng.pagemapper.pagemappersdk.domain.dependency

import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
data class RenderComponentDependency(
    var response: DynamicScreenResponse? = null,
    var gameId: String? = null,
    var parentScreenName: String? = null,
)