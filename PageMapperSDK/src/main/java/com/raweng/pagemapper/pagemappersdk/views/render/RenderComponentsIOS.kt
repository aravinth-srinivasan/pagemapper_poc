package com.raweng.pagemapper.pagemappersdk.views.render

import android.util.Log
import androidx.compose.runtime.Composable
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.domain.dependency.RenderComponentDependency
import com.raweng.pagemapper.pagemappersdk.iosmigration.BaseView
import com.raweng.pagemapper.pagemappersdk.iosmigration.listener.UIComponent
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.utils.JNIUtils
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import kotlin.reflect.full.createInstance

@Composable
internal fun RenderComponentsIOS(
    viewModel: PageMapperViewModel,
    dependency: RenderComponentDependency,
    liveGameViewModel: LiveGameViewModel? = null,
    componentEventListener: ComponentEventListener? = null,
    componentAnalyticsListener: ComponentAnalyticsListener? = null
) {
    val utils = JNIUtils()
    dependency.response?.components?.forEachIndexed { index, item ->
        var uiComponent: UIComponent? = null
        try {
            uiComponent = createUIComponent(
                viewModel.componentHashMap,
                item.value.orEmpty(),
                utils
            )

            uiComponent?.init(
                index.toString(),
                viewModel,
                InternalComponentDependency(
                    item,
                    dependency.dependency
                ),
                liveGameViewModel
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        uiComponent?.Render(componentEventListener, componentAnalyticsListener)
    }
}


private fun generateClassName(componentHashMap: HashMap<String, String>?, key: String): String? {
    return componentHashMap?.getOrDefault(key, "")?.let {
        "com.raweng.pagemapper.pagemappersdk.iosmigration.components.$it"
    }
}

private fun createUIComponent(
    componentHashMap: HashMap<String, String>?,
    key: String,
    utils: JNIUtils
): UIComponent? {
    val className = generateClassName(componentHashMap, key)
    //val kClass = Class.forName(className.orEmpty()).kotlin
    var kClass: UIComponent? = null
    if (className != null) {
        kClass = utils.createClassInstance(className) as UIComponent
    }
    //return kClass.createInstance() as? UIComponent?
    return kClass
}