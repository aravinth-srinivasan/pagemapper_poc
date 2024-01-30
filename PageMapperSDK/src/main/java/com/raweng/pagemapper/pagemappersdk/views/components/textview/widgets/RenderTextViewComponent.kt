package com.raweng.pagemapper.pagemappersdk.views.components.textview.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.raweng.dfe_components_android.commoncomponents.textview.CustomTextView
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.extension.toVariant
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.base.CommonLaunchedEffect
import com.raweng.pagemapper.pagemappersdk.views.components.textview.viewmodel.TextViewViewModel

@Composable
internal fun RenderTextViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    viewModel: TextViewViewModel,
    dependency: InternalComponentDependency
) {
    val uiState = viewModel.uiStateLiveData.observeAsState()

    CommonLaunchedEffect(
        pageMapperViewModel,
        dependency,
        uiState.value
    )

    uiState.value?.loading?.let {
        if (it) {
            Text(text = "Loading...")
        }
    }
    uiState.value?.data?.let {
        val data = (it.convertedData as String)
        CustomTextView(data = data, style = dependency.item.variant.toVariant())
    }
}