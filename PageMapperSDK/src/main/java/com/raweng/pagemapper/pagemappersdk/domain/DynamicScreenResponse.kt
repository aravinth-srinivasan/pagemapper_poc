package com.raweng.pagemapper.pagemappersdk.domain

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class DynamicScreenResponse(
    @SerializedName("components")
    var components: List<Component>?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("created_by")
    var createdBy: String?,
    @SerializedName("_in_progress")
    var inProgress: Boolean?,
    @SerializedName("locale")
    var locale: String?,
    @SerializedName("publish_details")
    var publishDetails: PublishDetails?,
    @SerializedName("time_frames")
    var timeFrames: List<TimeFrame>?,
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


    data class TimeFrame(
        @SerializedName("_metadata")
        var metadata: Metadata?,
        @SerializedName("name")
        var name: List<Name>?,
        @SerializedName("segments")
        var segments: List<Segment>?
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


    data class Metadata(
        @SerializedName("uid")
        var uid: String?
    )


    data class Name(
        @SerializedName("label")
        var label: String?,
        @SerializedName("value")
        var value: String?
    )


    data class Segment(
        @SerializedName("components")
        var components: List<Component>?,
        @SerializedName("locations")
        var locations: List<Location>?, //TODO Check this later Roja
        @SerializedName("_metadata")
        var metadata: Metadata?,
        @SerializedName("name")
        var name: List<Name>?
    )

    data class Component(
        @SerializedName("content_entry_label")
        var contentEntryLabel: String?,
        @SerializedName("content_entry_uid")
        var contentEntryUid: String?,
        @SerializedName("content_model_label")
        var contentModelLabel: String?,
        @SerializedName("content_model_uid")
        var contentModelUid: String?,
        @SerializedName("content_type")
        var contentType: String?,
        @SerializedName("label")
        var label: String?,
        @SerializedName("uid")
        var uid: String?,
        @SerializedName("value")
        var value: String?,
        @SerializedName("variant")
        var variant: String?, // pass "default" value if empty style
        @SerializedName("variant_label")
        var variantLabel: String?,
        @SerializedName("dfep_data_source")
        var dfepDataSource: String?,
        @SerializedName("dfep_no_of_items")
        var dfepNoOfItems: String?,
        @SerializedName("content_entry_data")
        var contentEntryData: JsonObject?
    )


    data class Location(
        @SerializedName("label")
        var label: String?,
        @SerializedName("value")
        var value: String?
    )
}











