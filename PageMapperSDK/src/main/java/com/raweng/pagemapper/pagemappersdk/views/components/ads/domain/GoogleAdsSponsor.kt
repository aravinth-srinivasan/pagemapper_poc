package com.raweng.pagemapper.pagemappersdk.views.components.ads.domain

import com.google.gson.annotations.SerializedName

data class GoogleAdsSponsor(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("data")
    var mData: List<Data?>?
) {
    data class Data(
        @SerializedName("ad_type")
        var adType: String?,
        @SerializedName("ad_unit_id")
        var adUnitId: String?,
        @SerializedName("created_at")
        var createdAt: Long?,
        @SerializedName("deeplink")
        var deeplink: String?,
        @SerializedName("deleted_at")
        var deletedAt: String?,
        @SerializedName("hide")
        var hide: Boolean?,
        @SerializedName("repeat")
        var repeat: Boolean?,
        @SerializedName("repeat_after")
        var repeatAfter: Int?,
        @SerializedName("screen")
        var screen: String?,
        @SerializedName("uid")
        var uid: String?,
        @SerializedName("updated_at")
        var updatedAt: Long?
    )
}