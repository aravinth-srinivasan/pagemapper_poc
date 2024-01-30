package com.raweng.pagemapper.pagemappersdk.views.render

import androidx.compose.runtime.Composable
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.domain.dependency.RenderComponentDependency
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.CarouselViewComponent
import com.raweng.pagemapper.pagemappersdk.views.components.gamestats.GameStatsCardViewComponent
import com.raweng.pagemapper.pagemappersdk.views.components.imageview.ImageViewComponent
import com.raweng.pagemapper.pagemappersdk.views.components.textview.TextViewComponent

@Composable
internal fun RenderComponents(
    viewModel: PageMapperViewModel,
    dependency: RenderComponentDependency,
    liveGameViewModel: LiveGameViewModel? = null,
    listener: ComponentClickListener? = null
) {
    dependency.response?.components?.forEachIndexed { _, item ->
        when (Components.fromValue(item.value.orEmpty())) {
            Components.HERO_CARD_CAROUSEL_VIEW -> {
                CarouselViewComponent(
                    pageMapperViewModel = viewModel,
                    liveGameViewModel = liveGameViewModel,
                    dependency = InternalComponentDependency(
                        item = item,
                        gameId = dependency.gameId
                    ),
                    listener = listener
                )
            }

            Components.IMAGE_VIEW -> {
                ImageViewComponent(
                    pageMapperViewModel = viewModel,
                    dependency = InternalComponentDependency(item),
                    listener = listener
                )
            }

            Components.TEXT_VIEW -> {
                TextViewComponent(
                    viewModel,
                    dependency = InternalComponentDependency(item)
                )
            }

            Components.GAME_STATS_CARD -> {
                GameStatsCardViewComponent(
                    viewModel,
                    InternalComponentDependency(item, dependency.gameId),
                    liveGameViewModel,
                    listener
                )
            }

            else -> {}
        }
    }
}