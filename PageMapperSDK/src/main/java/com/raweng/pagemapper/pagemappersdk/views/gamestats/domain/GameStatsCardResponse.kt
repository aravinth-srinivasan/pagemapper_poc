package com.raweng.pagemapper.pagemappersdk.views.gamestats.domain

import com.google.gson.annotations.SerializedName

data class GameStatsCardResponse(
    @SerializedName("ACL")
    var aCL: ACL?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("created_by")
    var createdBy: String?,
    @SerializedName("cta_button")
    var ctaButton: CtaButton?,
    @SerializedName("_in_progress")
    var inProgress: Boolean?,
    @SerializedName("locale")
    var locale: String?,
    @SerializedName("no_stats_message")
    var noStatsMessage: String?,
    @SerializedName("publish_details")
    var publishDetails: PublishDetails?,
    @SerializedName("tags")
    var tags: List<Any?>?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("uid")
    var uid: String?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("updated_by")
    var updatedBy: String?,
    @SerializedName("_version")
    var version: Int?
) {
    class ACL

    data class CtaButton(
        @SerializedName("clickthrough_link")
        var clickthroughLink: String?,
        @SerializedName("icon")
        var icon: Icon?,
        @SerializedName("open_the_link_on")
        var openTheLinkOn: String?,
        @SerializedName("title")
        var title: String?
    ) {
        data class Icon(
            @SerializedName("ACL")
            var aCL: ACL?,
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
            @SerializedName("tags")
            var tags: List<Any?>?,
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
        ) {
            class ACL

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
    }

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