package com.raweng.pagemapper.pagemappersdk.render.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse

interface IComponentList {
    @Composable
    fun HeroCardCarouselView(item: DynamicScreenResponse.Component, index: Int)

    @Composable
    fun ImageView(item: DynamicScreenResponse.Component, index: Int)

    @Composable
    fun TextView(item: DynamicScreenResponse.Component, index: Int)

    @Composable
    fun GameStatsCard(item: DynamicScreenResponse.Component, index: Int)
}