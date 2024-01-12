package com.raweng.pagemapper.pagemappersdk.data.manager

import android.app.Application
import com.raweng.pagemapper.pagemappersdk.data.provider.basetext.BaseTextDataProvider
import com.raweng.pagemapper.pagemappersdk.data.provider.customobject.DFECustomObjectDataProvider
import com.raweng.pagemapper.pagemappersdk.data.provider.socialmedia.SocialMediaDataProvider
import com.raweng.pagemapper.pagemappersdk.domain.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.type.Components
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class PagerDataManager(
    private val components: List<DynamicScreenResponse.Component>?,
    private val componentDataMap: MutableMap<String, ResponseDataModel>,
    private val application: Application,
) {

    var onComplete: (() -> Unit)? = null
    private var isNetworkOnly = true

    suspend fun sync() {
        coroutineScope {
            components?.map { item ->
                val mComponent = Components.fromValue(item.value.orEmpty())
                async {
                    when (mComponent) {
                        Components.GOOGLE_ADS_VIEW -> {
                            DFECustomObjectDataProvider(
                                item,
                                componentDataMap,
                                "google_sponsors"
                            ).getData()
                        }

                        Components.SOCIAL_MEDIA_LIST -> {
                            SocialMediaDataProvider(item, componentDataMap, isNetworkOnly).getData()
                        }

                        Components.TEXT_VIEW -> {
                            BaseTextDataProvider(item, componentDataMap, isNetworkOnly).getData()
                        }

                        else -> {}
                    }
                }
            }?.awaitAll()
            onComplete?.invoke()
        }
    }

}