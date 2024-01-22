package com.raweng.pagemapper.pagemappersdk.domain.components

import com.google.gson.annotations.SerializedName

data class SectionHeader(
    @SerializedName("title") var title: String? = null,
    @SerializedName("subtitle") var subtitle: String? = null,
    @SerializedName("cta_text") var ctaText: String? = null,
    @SerializedName("cta_icon") var ctaIcon: Image? = Image(),
    @SerializedName("cta_link") var ctaLink: String? = null,
    @SerializedName("sponsor") var sponsor: List<SponsorModel>? = listOf(),
)