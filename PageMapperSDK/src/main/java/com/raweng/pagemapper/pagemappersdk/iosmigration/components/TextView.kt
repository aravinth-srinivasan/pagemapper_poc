package com.raweng.pagemapper.pagemappersdk.iosmigration.components

import androidx.compose.runtime.Composable
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.iosmigration.BaseView
import com.raweng.pagemapper.pagemappersdk.iosmigration.listener.UIComponent
import com.raweng.pagemapper.pagemappersdk.iosmigration.listener.UIDelegate
import com.raweng.pagemapper.pagemappersdk.listener.ComponentAnalyticsListener
import com.raweng.pagemapper.pagemappersdk.listener.ComponentEventListener
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.textview.TextViewComponent

class TextView : UIComponent, BaseView() {

    @Composable
    override fun Render(
        componentEventListener: ComponentEventListener?,
        componentAnalyticsListener: ComponentAnalyticsListener?
    ) {
        TextViewComponent(
            pageMapperViewModel, dependency
        )
    }
}