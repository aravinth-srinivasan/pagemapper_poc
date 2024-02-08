package com.raweng.pagemapper.pagemappersdk.iosmigration.components

import androidx.compose.runtime.Composable
import com.raweng.pagemapper.pagemappersdk.iosmigration.BaseView
import com.raweng.pagemapper.pagemappersdk.iosmigration.listener.UIComponent
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.ImageViewComponent

class ImageView : UIComponent, BaseView() {

    @Composable
    override fun Render(
        componentEventListener: ComponentEventListener?,
        componentAnalyticsListener: ComponentAnalyticsListener?
    ) {
        ImageViewComponent(
            pageMapperViewModel, dependency, componentEventListener
        )
    }
}