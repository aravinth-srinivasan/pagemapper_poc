package com.raweng.pagemapper.pagemappersdk.iosmigration.domain

import com.raweng.pagemapper.pagemappersdk.iosmigration.listener.UIComponent
import kotlin.reflect.KClass

data class UIComponentModel(
    val component: KClass<out UIComponent>,
)