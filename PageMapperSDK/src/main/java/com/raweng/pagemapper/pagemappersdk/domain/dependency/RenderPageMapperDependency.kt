package com.raweng.pagemapper.pagemappersdk.domain.dependency


/**
 * Render page mapper dependency, This is parent dependency
 * domain get input from parent and shared across
 * the our all child components.
 * In feature we just add more input or dependency params here
 * it will not affect our existing implementation!.
 *
 * @property gameId
 * @property parentScreenName
 * @constructor Create empty Render page mapper dependency
 */
data class RenderPageMapperDependency(
    var gameId: String? = null,
    var parentScreenName: String? = null,
)