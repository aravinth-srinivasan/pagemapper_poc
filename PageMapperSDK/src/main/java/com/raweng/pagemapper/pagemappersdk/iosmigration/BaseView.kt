package com.raweng.pagemapper.pagemappersdk.iosmigration

import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.iosmigration.listener.UIComponent
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel

abstract class BaseView : UIComponent {
    lateinit var id: String
    lateinit var pageMapperViewModel: PageMapperViewModel
    lateinit var dependency: InternalComponentDependency
    var liveGameViewModel: LiveGameViewModel? = null

    override fun init(
        id: String,
        pageMapperViewModel: PageMapperViewModel,
        dependency: InternalComponentDependency,
        liveGameViewModel: LiveGameViewModel?
    ) {
        this.id = id
        this.pageMapperViewModel = pageMapperViewModel
        this.dependency = dependency
        this.liveGameViewModel = liveGameViewModel
    }
}