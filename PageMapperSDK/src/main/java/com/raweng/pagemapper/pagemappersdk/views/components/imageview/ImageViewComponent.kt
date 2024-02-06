package com.raweng.pagemapper.pagemappersdk.views.components.imageview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.provider.BaseImageDataProvider
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.viewmodel.ImageViewViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.widgets.RenderImageView

@Composable
internal fun ImageViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    dependency: InternalComponentDependency,
    componentEventListener: ComponentEventListener? = null,
) {
    val factory = ViewModelFactory {
        ImageViewViewModel(BaseImageDataProvider(dependency))
    }
    val viewModel: ImageViewViewModel =
        viewModel(factory = factory, key = dependency.item.uid.orEmpty())
    LaunchedEffect(Unit) {
        viewModel.fetchResponse()
    }

    RenderImageView(pageMapperViewModel, viewModel, dependency, componentEventListener)

}

