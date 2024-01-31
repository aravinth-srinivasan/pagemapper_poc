package com.raweng.pagemapper.pagemappersdk.domain.dependency

import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse


/**
 * Render component dependency, This our local dependency for to get ready the component list,
 * Once we got screen api data then we will pass api data to this domain.
 * To loop the components to render the component
 *
 * @property response
 * @property dependency
 *
 */
class RenderComponentDependency(
    var response: DynamicScreenResponse? = null,
    var dependency: RenderPageMapperDependency
)