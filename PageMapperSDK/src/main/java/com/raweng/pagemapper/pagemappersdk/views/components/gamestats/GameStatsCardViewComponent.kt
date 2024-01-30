package com.raweng.pagemapper.pagemappersdk.views.components.gamestats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.utils.ViewModelFactory
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.provider.GameStatsDataProvider
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.viewmodel.GameStatsViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.widgets.RenderGameStatsCardView

@Composable
internal fun GameStatsCardViewComponent(
    pageMapperViewModel: PageMapperViewModel,
    dependency: InternalComponentDependency,
    liveGameViewModel: LiveGameViewModel? = null,
    listener: ComponentClickListener? = null
) {

    val factory = ViewModelFactory {
        GameStatsViewModel(
            GameStatsDataProvider(
                dependency,
                PageMapperSDK.getDFERequest(),
            )
        )
    }
    val viewModel: GameStatsViewModel =
        viewModel(factory = factory, key = dependency.item.uid.orEmpty())
    LaunchedEffect(Unit) {
        viewModel.fetchResponse()
    }

    RenderGameStatsCardView(
        pageMapperViewModel,
        viewModel,
        liveGameViewModel,
        dependency,
        listener
    )
}

