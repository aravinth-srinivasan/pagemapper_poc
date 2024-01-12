package com.raweng.pagemapper.pagemappersdk.domain.components

import com.google.gson.annotations.SerializedName

data class BaseTextResponse(
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("cta_link") var ctaLink: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("content") var content: String? = null
)