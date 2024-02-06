package com.raweng.pagemapper.pagemappersdk.views.components.textview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.pagemapper.pagemappersdk.views.components.textview.provider.BaseTextDataProvider
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.viewmodel.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.textview.viewmodel.TextViewViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.textview.widgets.RenderTextViewComponent

@Composable
internal fun TextViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    dependency: InternalComponentDependency
) {
    val factory = ViewModelFactory {
        TextViewViewModel(BaseTextDataProvider(dependency))
    }
    val viewModel: TextViewViewModel = viewModel(factory = factory, key = dependency.item.uid.orEmpty())
    LaunchedEffect(Unit) {
        viewModel.fetchResponse()
    }
    RenderTextViewComponent(pageMapperViewModel, viewModel, dependency)
}

