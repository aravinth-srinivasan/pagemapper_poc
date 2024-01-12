package com.raweng.pagemapper.pagemappersdk.domain.components

import com.google.gson.annotations.SerializedName

data class SocialMediaListResponse(
    @SerializedName("_version") var Version: Int? = null,
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("social_media_list") var socialMediaList: ArrayList<SocialMediaItem> = arrayListOf(),
)

data class SocialMediaItem(
    @SerializedName("image") var image: Image? = Image(),
    @SerializedName("cta_link") var ctaLink: String? = null,
    @SerializedName("open_the_link_on") var openTheLinkOn: String? = null,
    @SerializedName("_metadata") var Metadata: Metadata? = Metadata()
)