package com.raweng.pagemapper.pagemappersdk.views.imageview.extension

import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewDataModel
import com.raweng.pagemapper.pagemappersdk.views.imageview.domain.BaseImageResponse

fun BaseImageResponse.toImageViewDataModel(): ImageViewDataModel {
    return ImageViewDataModel(
        imageUrl = this.image?.url,
    )
}