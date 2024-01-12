package com.raweng.pagemapper.pagemappersdk.domain.components

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("content_type") var contentType: String? = null,
    @SerializedName("parent_uid") var parentUid: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null
)