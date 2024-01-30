package com.raweng.pagemapper.pagemappersdk.views.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.domain.uistate.UiStateComponentModel
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel

@Composable
internal fun CommonLaunchedEffect(
    viewModel: PageMapperViewModel,
    dependency: InternalComponentDependency,
    uiState: UiStateComponentModel?
) {
    LaunchedEffect(uiState) {
        if (uiState?.data != null) {
            viewModel.updateAllComponentFetchedStatus(dependency.item)
        }
    }
}