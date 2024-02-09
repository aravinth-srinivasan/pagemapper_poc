package com.raweng.pagemapper.pagemappersdk.views.components.socialmedia.extension

import com.raweng.dfe_components_android.components.socialmedia.model.MediaItem
import com.raweng.dfe_components_android.components.socialmedia.model.SocialMediaListDataModel
import com.raweng.pagemapper.pagemappersdk.views.components.socialmedia.domain.SocialMediaItem
import com.raweng.pagemapper.pagemappersdk.views.components.socialmedia.domain.SocialMediaListResponse
import com.raweng.pagemapper.pagemappersdk.dependency.placeholder.ComponentPlaceHolderDependency
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
        //mediaImage = ComponentPlaceHolderDependency.getPlaceholder(), //TODO handle social meadia
        mediaImageUrl = this.image?.url,
        ctaUrl = this.ctaLink,
        openTheLinkOn = this.openTheLinkOn
    )
}