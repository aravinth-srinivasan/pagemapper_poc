package com.raweng.pagemapper.pagemappersdk.views.components.carousel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.provider.CarouselViewDataProvider
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.viewmodel.CarouselViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.widgets.RenderCarouselView

@Composable
internal fun CarouselViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    dependency: InternalComponentDependency,
    liveGameViewModel: LiveGameViewModel? = null,
    componentEventListener: ComponentEventListener? = null,
    componentAnalyticsListener: ComponentAnalyticsListener? = null
) {
    val factory = ViewModelFactory {
        CarouselViewModel(CarouselViewDataProvider(dependency))
    }
    val viewModel: CarouselViewModel = viewModel(factory = factory, key = dependency.item.uid.orEmpty())
    LaunchedEffect(Unit) {
        viewModel.fetchResponse()
    }

    RenderCarouselView(
        pageMapperViewModel,
        viewModel,
        liveGameViewModel,
        dependency,
        componentEventListener,
        componentAnalyticsListener
    )

}

