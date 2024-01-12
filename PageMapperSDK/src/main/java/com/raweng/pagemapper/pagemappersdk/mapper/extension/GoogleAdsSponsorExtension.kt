package com.raweng.pagemapper.pagemappersdk.mapper.extension

import com.raweng.dfe_components_android.components.googleads.model.GoogleAdsDataModel
import com.raweng.pagemapper.pagemappersdk.domain.components.GoogleAdsSponsor

fun GoogleAdsSponsor?.toGoogleAdsDataModel(deepLink: String): GoogleAdsDataModel? {
    return this?.mData
        ?.find { it?.deeplink == deepLink }
        ?.let { GoogleAdsDataModel(adUnitId = it.adUnitId) }
}