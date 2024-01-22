package com.raweng.pagemapper.pagemappersdk.views.imageview.domain

import com.google.gson.annotations.SerializedName
import com.raweng.pagemapper.pagemappersdk.domain.components.Image

data class BaseImageResponse(
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("cta_link") var ctaLink: String? = null,
    @SerializedName("open_the_link_on") var openTheLinkOn: String?,
    @SerializedName("image") var image: Image? = Image(),
    @SerializedName("title") var title: String? = null
)