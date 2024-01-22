package com.raweng.pagemapper.pagemappersdk.views.imageview

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.dfe_components_android.commoncomponents.imageview.CustomImageView
import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewDataModel
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.extension.toVariant
import com.raweng.pagemapper.pagemappersdk.type.ClickEventType
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.utils.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.base.CommonLaunchedEffect
import com.raweng.pagemapper.pagemappersdk.views.imageview.domain.BaseImageResponse
import com.raweng.pagemapper.pagemappersdk.views.imageview.provider.BaseImageDataProvider
import com.raweng.pagemapper.pagemappersdk.views.imageview.viewmodel.ImageViewViewModel

@Composable
internal fun ImageViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    item: DynamicScreenResponse.Component,
    listener: ComponentClickListener? = null
) {
    Log.e("TAG", "ImageViewComponent: ")
    val factory = ViewModelFactory {
        ImageViewViewModel(BaseImageDataProvider(item))
    }
    val viewModel: ImageViewViewModel = viewModel(factory = factory, key = item.uid.orEmpty())
    LaunchedEffect(Unit) {
        viewModel.fetchResponse()
    }

    RenderImageView(pageMapperViewModel, viewModel, item, listener)

}

@Composable
private fun RenderImageView(
    pageMapperViewModel: PageMapperViewModel,
    viewModel: ImageViewViewModel, item: DynamicScreenResponse.Component,
    listener: ComponentClickListener? = null
) {
    val uiState = viewModel.uiStateLiveData.observeAsState()

    CommonLaunchedEffect(
        pageMapperViewModel,
        item,
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
            style = item.variant.toVariant(),
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