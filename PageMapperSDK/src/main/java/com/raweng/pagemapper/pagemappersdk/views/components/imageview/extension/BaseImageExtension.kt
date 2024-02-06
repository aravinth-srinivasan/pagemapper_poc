package com.raweng.pagemapper.pagemappersdk.views.components.imageview.extension

import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewAnalytics
import com.raweng.dfe_components_android.commoncomponents.imageview.model.ImageViewDataModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.domain.BaseImageResponse

fun BaseImageResponse.toImageViewDataModel(dependency: InternalComponentDependency): ImageViewDataModel {
    return ImageViewDataModel(
        imageUrl = image?.url,
        analytics = ImageViewAnalytics(
            contentId = uid,
            parent = dependency.dependency.parentScreenName
        )
    )
}