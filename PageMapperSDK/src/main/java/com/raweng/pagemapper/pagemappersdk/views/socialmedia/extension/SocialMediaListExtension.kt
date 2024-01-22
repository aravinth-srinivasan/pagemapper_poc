package com.raweng.pagemapper.pagemappersdk.views.socialmedia.extension

import com.raweng.dfe_components_android.components.socialmedia.model.MediaItem
import com.raweng.dfe_components_android.components.socialmedia.model.SocialMediaListDataModel
import com.raweng.pagemapper.pagemappersdk.views.socialmedia.domain.SocialMediaItem
import com.raweng.pagemapper.pagemappersdk.views.socialmedia.domain.SocialMediaListResponse
import com.raweng.pagemapper.pagemappersdk.placeholder.ComponentPlaceHolder
import com.raweng.pagemapper.pagemappersdk.type.Components
import java.util.ArrayList

fun SocialMediaListResponse.toSocialMediaListDataModel(): SocialMediaListDataModel {
    return SocialMediaListDataModel(
        mediaListItems = this.socialMediaList.toMediaItemsList()
            .toMutableList() as ArrayList<MediaItem>
    )
}

fun List<SocialMediaItem>.toMediaItemsList(): List<MediaItem> {
    return map {
        it.toMediaItem()
    }
}

fun SocialMediaItem.toMediaItem(): MediaItem {
    return MediaItem(
        mediaImage = ComponentPlaceHolder.getPlaceholder(Components.SOCIAL_MEDIA_LIST),
        mediaImageUrl = this.image?.url,
        ctaUrl = this.ctaLink,
        openTheLinkOn = this.openTheLinkOn
    )
}