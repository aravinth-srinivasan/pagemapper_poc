package com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain

import com.google.gson.annotations.SerializedName
import com.raweng.pagemapper.pagemappersdk.domain.components.Image
import com.raweng.pagemapper.pagemappersdk.domain.components.SectionHeader
import java.io.Serializable

data class CarouselViewResponse(
    @SerializedName("_version") var Version: Int? = null,
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("auto_scroll") var autoScroll: Boolean? = false,
    @SerializedName("auto_scroll_timer") var autoScrollTimer: Int? = 0,
    @SerializedName("hide_subtitle") var hideSubtitle: Boolean? = false,
    @SerializedName("hide_title") var hideTitle: Boolean? = false,
    @SerializedName("mute_video") var muteVideo: Boolean? = false,
    @SerializedName("repeat_video") var repeatVideo: Boolean? = false,
    @SerializedName("repeat_video_count") var repeatVideoCount: Int? = 0,
    @SerializedName("show_carousel_indicator") var showCarouselIndicator: Boolean? = false,
    @SerializedName("show_date_as_subtitle") var showDateAsSubtitle: Boolean? = false,
    @SerializedName("section_header") var sectionHeader: SectionHeader? = SectionHeader(),
    @SerializedName("feeds") var feedsList: List<FeedType?>? = null,

    ) : Serializable {
    data class FeedType(
        @SerializedName("article")
        val article: Article? = null,
        @SerializedName("gallery")
        val gallery: Gallery? = null,
        @SerializedName("video")
        val video: Video? = null,
        @SerializedName("web_url")
        val webUrl: WebUrl? = null
    ) : Serializable {
        data class Article(
            @SerializedName("title")
            val title: String? = null,
            @SerializedName("subtitle")
            val subtitle: String? = null,
            @SerializedName("_metadata")
            val metadata: Metadata? = null,
            @SerializedName("publish_date")
            val publishDate: String? = null,
            @SerializedName("image")
            val image: Image? = null,
            @SerializedName("spotlight_image")
            val spotlightImage: Image? = null,
            @SerializedName("width_type")
            val widthType: String? = null,
            @SerializedName("content")
            val content: String? = null
        ) : Serializable

        data class Gallery(
            @SerializedName("title")
            val title: String? = null,
            @SerializedName("subtitle")
            val subtitle: String? = null,
            @SerializedName("image")
            val image: Image? = null,
            @SerializedName("images")
            val images: List<CaptionImage?>? = null,
            @SerializedName("_metadata")
            val metadata: Metadata? = null,
            @SerializedName("publish_date")
            val publishDate: String? = null,
            @SerializedName("width_type")
            val widthType: String? = null,
        ) : Serializable {

            data class CaptionImage(
                @SerializedName("caption")
                val caption: String? = null,
                @SerializedName("image")
                val image: Image? = null,
                @SerializedName("_metadata")
                val metadata: Metadata? = null
            ) : Serializable

        }

        data class Video(
            @SerializedName("title")
            val title: String? = null,
            @SerializedName("subtitle")
            val subtitle: String? = null,
            @SerializedName("_metadata")
            val metadata: Metadata? = null,
            @SerializedName("publish_date")
            val publishDate: String? = null,
            @SerializedName("width_type")
            val widthType: String? = null,
            @SerializedName("image")
            val image: Image? = null,
            @SerializedName("video_link")
            val videoLink: String? = null
        ) : Serializable

        data class WebUrl(
            @SerializedName("title")
            val title: String? = null,
            @SerializedName("subtitle")
            val subtitle: String? = null,
            @SerializedName("_metadata")
            val metadata: Metadata? = null,
            @SerializedName("publish_date")
            val publishDate: String? = null,
            @SerializedName("width_type")
            val widthType: String? = null,
            @SerializedName("image")
            val image: Image? = null,
            @SerializedName("click_through_link")
            val clickthroughLink: String? = null
        ) : Serializable
    }

}
