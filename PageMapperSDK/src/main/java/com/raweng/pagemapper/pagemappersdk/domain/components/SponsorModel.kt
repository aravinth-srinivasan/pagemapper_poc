package com.raweng.pagemapper.pagemappersdk.domain.components
import com.google.gson.annotations.SerializedName

data class SponsorModel(
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("created_by")
    var createdBy: String?,
    @SerializedName("cta")
    var cta: String?,
    @SerializedName("image")
    var image: Image?,
    @SerializedName("_in_progress")
    var inProgress: Boolean?,
    @SerializedName("locale")
    var locale: String?,
    @SerializedName("open_the_link_on")
    var openTheLinkOn: String?,
    @SerializedName("sponsor_prefix_text")
    var sponsorPrefixText: String?,
    @SerializedName("sponsor_text")
    var sponsorText: String?,
    @SerializedName("publish_details")
    var publishDetails: PublishDetails?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("tracking_id")
    var trackingId: String?,
    @SerializedName("uid")
    var uid: String?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("updated_by")
    var updatedBy: String?,
    @SerializedName("_version")
    var version: Int?
) {

    data class Image(
        @SerializedName("content_type")
        var contentType: String?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("created_by")
        var createdBy: String?,
        @SerializedName("file_size")
        var fileSize: String?,
        @SerializedName("filename")
        var filename: String?,
        @SerializedName("is_dir")
        var isDir: Boolean?,
        @SerializedName("parent_uid")
        var parentUid: String?,
        @SerializedName("publish_details")
        var publishDetails: PublishDetails?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("uid")
        var uid: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("updated_by")
        var updatedBy: String?,
        @SerializedName("url")
        var url: String?,
        @SerializedName("_version")
        var version: Int?
    )

    data class PublishDetails(
        @SerializedName("environment")
        var environment: String?,
        @SerializedName("locale")
        var locale: String?,
        @SerializedName("time")
        var time: String?,
        @SerializedName("user")
        var user: String?
    )
}


