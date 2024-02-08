package com.raweng.pagemapper.pagemappersdk.iosmigration.components

import androidx.compose.runtime.Composable
import com.raweng.pagemapper.pagemappersdk.iosmigration.BaseView
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.GameStatsCardViewComponent

class GameStatsCardView : BaseView() {
    @Composable
    override fun Render(
        componentEventListener: ComponentEventListener?,
        componentAnalyticsListener: ComponentAnalyticsListener?
    ) {
        GameStatsCardViewComponent(
            pageMapperViewModel = pageMapperViewModel,
            dependency = dependency
        )
    }
}