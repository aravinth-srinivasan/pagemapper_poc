package com.raweng.pagemapper.pagemappersdk.views.render

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raweng.pagemapper.pagemappersdk.domain.dependency.RenderComponentDependency
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel

@Composable
internal fun RenderPagerMapperViews(
    viewModel: PageMapperViewModel,
    dependency: RenderComponentDependency,
    liveGameViewModel: LiveGameViewModel? = null,
    componentEventListener: ComponentEventListener? = null,
    componentAnalyticsListener: ComponentAnalyticsListener? = null
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        RenderComponents(viewModel, dependency, liveGameViewModel, componentEventListener, componentAnalyticsListener)
    }
}