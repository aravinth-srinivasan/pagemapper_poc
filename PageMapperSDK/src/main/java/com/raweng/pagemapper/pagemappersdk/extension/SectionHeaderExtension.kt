package com.raweng.pagemapper.pagemappersdk.extension

import com.raweng.dfe_components_android.components.sectionheaderview.model.SectionHeaderDataModel
import com.raweng.dfe_components_android.components.sectionheaderview.type.SubHeaderType
import com.raweng.pagemapper.pagemappersdk.domain.components.SectionHeader

fun SectionHeader.toSectionHeaderDataModelDynamic(): SectionHeaderDataModel {
    return SectionHeaderDataModel(
        headerText = this.title,
        subHeaderText = if (!this.ctaText.isNullOrEmpty()) {
            this.ctaText
        } else {
            this.sponsor?.getOrNull(0)?.sponsorText
        },
        subHeaderPrefixText = this.sponsor?.getOrNull(0)?.sponsorPrefixText,
        subHeaderTrailingImageUrl = this.ctaIcon?.url,
        subHeaderImageUrl = this.sponsor?.getOrNull(0)?.image?.url,
        ctaLink = if (!this.ctaLink.isNullOrEmpty()) {
            this.ctaLink
        } else {
            this.sponsor?.getOrNull(0)?.cta
        },
        subHeaderType = getSubHeaderType(this)
    )
}

fun getSubHeaderType(sectionHeader: SectionHeader): SubHeaderType? {
    return if (!sectionHeader.sponsor?.getOrNull(0)?.sponsorPrefixText.isNullOrEmpty() ||
        (!sectionHeader.sponsor?.getOrNull(
            0
        )?.sponsorText.isNullOrEmpty())
    ) {
        SubHeaderType.TEXT
    } else if ((!sectionHeader.ctaIcon?.url.isNullOrEmpty()) ||
        (!sectionHeader.ctaText.isNullOrEmpty())
    ) {
        SubHeaderType.BUTTON
    } else if ((!sectionHeader.sponsor?.getOrNull(0)?.image?.url.isNullOrEmpty())) {
        SubHeaderType.IMAGE
    } else {
        null
    }
}