package com.raweng.pagemapper.pagemappersdk.render

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.render.manager.RenderComponentManager
import com.raweng.pagemapper.pagemappersdk.utils.ComponentClickListener
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.pagemappersdk.widgets.RenderTextWidgets

@Composable
fun RenderPageMapperV2(
    viewModel: PageMapperViewModel,
    liveGameViewModel: LiveGameViewModel? = null,
    liveGameId: String? = null,
    listener: ComponentClickListener? = null,
    renderComponentManager: RenderComponentManager = RenderComponentManager()
) {

    val uiState = viewModel.uiStateLiveData.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.initData()
        if (liveGameViewModel != null && !liveGameId.isNullOrEmpty()) {
            liveGameViewModel.setGameId(liveGameId)
        }
    }


    uiState.value?.data?.let {
        Log.e("TAG", "RenderPageMapper:  recompose")
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            renderComponentManager.RenderCompose(
                viewModel,
                it,
                liveGameViewModel,
                liveGameId,
                listener
            )
        }
    }

    uiState.value?.loading?.let {
        if (it) {
            RenderTextWidgets("Page Mapper Loading....")
        }
    }

}