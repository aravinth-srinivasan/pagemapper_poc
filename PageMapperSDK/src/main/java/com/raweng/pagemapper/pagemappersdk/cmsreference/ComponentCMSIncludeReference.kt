package com.raweng.pagemapper.pagemappersdk.cmsreference

import com.raweng.pagemapper.pagemappersdk.type.Components
import java.util.EnumMap

internal object ComponentCMSIncludeReference {
    private val componentCMSIncludeRef = EnumMap<Components, Array<String>>(Components::class.java)

    fun config(components: Components, sponsor: Array<String>) {
        componentCMSIncludeRef[components] = sponsor
    }

    fun getComponentCMSIncludeRef(components: Components): Array<String>? {
        return componentCMSIncludeRef[components]
    }
}