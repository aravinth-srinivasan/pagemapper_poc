package com.raweng.pagemapper.pagemappersdk.views.textview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.dfe_components_android.commoncomponents.textview.CustomTextView
import com.raweng.pagemapper.pagemappersdk.views.textview.provider.BaseTextDataProvider
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.extension.toVariant
import com.raweng.pagemapper.pagemappersdk.utils.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.base.CommonLaunchedEffect
import com.raweng.pagemapper.pagemappersdk.views.textview.viewmodel.TextViewViewModel

@Composable
internal fun TextViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    item: DynamicScreenResponse.Component
) {
    val factory = ViewModelFactory {
        TextViewViewModel(BaseTextDataProvider(item))
    }
    val viewModel: TextViewViewModel = viewModel(factory = factory, key = item.uid.orEmpty())
    LaunchedEffect(Unit) {
        viewModel.fetchResponse()
    }
    RenderTextViewComponent(pageMapperViewModel, viewModel, item)
}

@Composable
private fun RenderTextViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    viewModel: TextViewViewModel,
    item: DynamicScreenResponse.Component
) {
    val uiState = viewModel.uiStateLiveData.observeAsState()

    CommonLaunchedEffect(
        pageMapperViewModel,
        item,
        uiState.value
    )

    uiState.value?.loading?.let {
        if (it) {
            Text(text = "Loading...")
        }
    }
    uiState.value?.data?.let {
        val data = (it.convertedData as String)
        CustomTextView(data = data, style = item.variant.toVariant())
    }
}