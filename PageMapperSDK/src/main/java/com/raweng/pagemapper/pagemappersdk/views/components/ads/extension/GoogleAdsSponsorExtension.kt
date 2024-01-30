package com.raweng.pagemapper.pagemappersdk.views.components.ads.extension

import com.raweng.dfe_components_android.components.googleads.model.GoogleAdsDataModel
import com.raweng.pagemapper.pagemappersdk.views.components.ads.domain.GoogleAdsSponsor

fun GoogleAdsSponsor?.toGoogleAdsDataModel(deepLink: String): GoogleAdsDataModel? {
    return this?.mData
        ?.find { it?.deeplink == deepLink }
        ?.let { GoogleAdsDataModel(adUnitId = it.adUnitId) }
}