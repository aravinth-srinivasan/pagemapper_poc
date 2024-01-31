package com.raweng.pagemapper.pagemappersdk.domain.dependency

import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse

/**
 * Internal component dependency, This our local dependency for individual component
 *
 * @property item
 * @property dependency
 * @property isNetworkOnly
 * @constructor Create empty Internal component dependency
 */
class InternalComponentDependency(
    var item: DynamicScreenResponse.Component,
    var dependency: RenderPageMapperDependency,
    var isNetworkOnly: Boolean = true,
)