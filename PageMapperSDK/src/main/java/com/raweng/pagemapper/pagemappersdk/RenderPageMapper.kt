package com.raweng.pagemapper.pagemappersdk

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.dependency.RenderComponentDependency
import com.raweng.pagemapper.pagemappersdk.domain.dependency.RenderPageMapperDependency
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.render.RenderPagerMapperViews
import com.raweng.pagemapper.pagemappersdk.views.render.widgets.RenderTextWidgets

@Composable
fun RenderPageMapper(
    viewModel: PageMapperViewModel,
    dependency: RenderPageMapperDependency,
    liveGameViewModel: LiveGameViewModel? = null,
    componentEventListener: ComponentEventListener? = null,
    analyticsListener: ComponentAnalyticsListener? = null
) {
    val uiState = viewModel.uiStateLiveData.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.initData()
        if (liveGameViewModel != null && !dependency.gameId.isNullOrEmpty()) {
            liveGameViewModel.setGameId(dependency.gameId.orEmpty())
        }
    }


    uiState.value?.data?.let {
        RenderPagerMapperViews(
            viewModel,
            getRenderComponentDependency(it, dependency),
            liveGameViewModel,
            componentEventListener,
            analyticsListener
        )
    }

    uiState.value?.loading?.let {
        if (it) {
            RenderTextWidgets("Page Mapper Loading....")
        }
    }
}

private fun getRenderComponentDependency(
    response: DynamicScreenResponse,
    dependency: RenderPageMapperDependency
): RenderComponentDependency {
    return RenderComponentDependency(
        response = response,
        dependency = dependency
    )
}

