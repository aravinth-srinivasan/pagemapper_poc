package com.raweng.pagemapper.pagemappersdk.views.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.uistate.UiStateComponentModel
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel

@Composable
internal fun CommonLaunchedEffect(
    viewModel: PageMapperViewModel,
    item: DynamicScreenResponse.Component,
    uiState: UiStateComponentModel?
) {
    LaunchedEffect(uiState) {
        if (uiState?.data != null) {
            viewModel.updateAllComponentFetchedStatus(item)
        }
    }
}