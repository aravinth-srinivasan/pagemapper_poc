package com.raweng.pagemapper.pagemappersdk.mapper.extension

import com.raweng.dfe_components_android.components.socialmedia.model.MediaItem
import com.raweng.dfe_components_android.components.socialmedia.model.SocialMediaListDataModel
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.domain.components.SocialMediaItem
import com.raweng.pagemapper.pagemappersdk.domain.components.SocialMediaListResponse
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
        mediaImage = PageMapperSDK.getComponentPlaceholder(Components.SOCIAL_MEDIA_LIST),
        mediaImageUrl = this.image?.url,
        ctaUrl = this.ctaLink,
        openTheLinkOn = this.openTheLinkOn
    )
}