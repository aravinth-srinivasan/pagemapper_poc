package com.raweng.pagemapper.pagemappersdk.render.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.render.components.ComponentList
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import kotlin.reflect.full.functions

class RenderComponentManager {
    @Composable
    fun RenderCompose(
        viewModel: PageMapperViewModel,
        response: DynamicScreenResponse?,
        liveGameViewModel: LiveGameViewModel? = null,
        liveGameId: String? = null,
        listener: ComponentClickListener? = null,
    ) {
        val mComponentList = ComponentList(viewModel, liveGameViewModel, liveGameId, listener)
        response?.components?.forEachIndexed { index, component ->
            mComponentList.javaClass.kotlin.functions.firstOrNull {
                it.name.lowercase() == component.value?.lowercase()
            }?.call(mComponentList,component, index, currentComposer, 0)
        }
    }
}

