package com.raweng.pagemapper.pagemappersdk.render.components

import androidx.compose.runtime.Composable
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.views.carousel.CarouselViewComponent
import com.raweng.pagemapper.pagemappersdk.views.gamestats.GameStatsCardViewComponent
import com.raweng.pagemapper.pagemappersdk.views.imageview.ImageViewComponent
import com.raweng.pagemapper.pagemappersdk.views.textview.TextViewComponent

class ComponentList(
    private var viewModel: PageMapperViewModel,
    private var liveGameViewModel: LiveGameViewModel? = null,
    private var liveGameId: String? = null,
    private var listener: ComponentClickListener? = null
) : IComponentList {

    @Composable
    override fun HeroCardCarouselView(item: DynamicScreenResponse.Component, index: Int) {
        CarouselViewComponent(
            pageMapperViewModel = viewModel,
            liveGameViewModel = liveGameViewModel,
            item = item,
            gameId = liveGameId.orEmpty(),
            listener = listener
        )
    }

    @Composable
    override fun ImageView(item: DynamicScreenResponse.Component, index: Int) {
        ImageViewComponent(
            pageMapperViewModel = viewModel,
            item = item,
            listener = listener
        )
    }

    @Composable
    override fun TextView(item: DynamicScreenResponse.Component, index: Int) {
        TextViewComponent(
            viewModel,
            item
        )
    }

    @Composable
    override fun GameStatsCard(item: DynamicScreenResponse.Component, index: Int) {
        GameStatsCardViewComponent(
            viewModel, item, liveGameViewModel, liveGameId, listener
        )
    }
}