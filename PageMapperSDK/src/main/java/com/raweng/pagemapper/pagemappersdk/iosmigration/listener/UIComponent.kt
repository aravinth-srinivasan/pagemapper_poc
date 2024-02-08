package com.raweng.pagemapper.pagemappersdk.iosmigration.listener

import androidx.compose.runtime.Composable
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel

interface UIComponent {
    @Composable
    fun Render(
        componentEventListener: ComponentEventListener?,
        componentAnalyticsListener: ComponentAnalyticsListener?
    )

    fun init(
        id: String,
        pageMapperViewModel: PageMapperViewModel,
        dependency: InternalComponentDependency,
        liveGameViewModel: LiveGameViewModel?
    )
}