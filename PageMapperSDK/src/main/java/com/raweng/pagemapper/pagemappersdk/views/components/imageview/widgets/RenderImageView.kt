package com.raweng.pagemapper.pagemappersdk.views.components.imageview.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.raweng.dfe_components_android.commoncomponents.imageview.CustomImageView
import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewDataModel
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.extension.toVariant
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.base.CommonLaunchedEffect
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.domain.BaseImageResponse
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.viewmodel.ImageViewViewModel

@Composable
internal fun RenderImageView(
    pageMapperViewModel: PageMapperViewModel,
    viewModel: ImageViewViewModel, dependency: InternalComponentDependency,
    listener: ComponentClickListener? = null
) {
    val uiState = viewModel.uiStateLiveData.observeAsState()

    CommonLaunchedEffect(
        pageMapperViewModel,
        dependency,
        uiState.value
    )

    uiState.value?.loading?.let {
        if (it) {
            Text(text = "Image Loading...")
        }
    }

    uiState.value?.data?.let {
        val data = (it.convertedData as ImageViewDataModel?)
        val apiData = (it.apiResponse as BaseImageResponse?)
        CustomImageView(
            data = data,
            style = dependency.item.variant.toVariant(),
            onClick = if (apiData?.ctaLink.isNullOrEmpty()) null else onImageClicked(
                listener,
                it
            )
        )
    }
}

private fun onImageClicked(
    listener: ComponentClickListener? = null,
    responseDataModel: ResponseDataModel
): (() -> Unit) {
    return {
        listener?.onClickedComponent(responseDataModel, ClickEventType.ON_CLICK)
    }
}