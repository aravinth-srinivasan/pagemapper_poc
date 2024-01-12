package com.raweng.pagemapper.pagemappersdk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.dfe_components_android.commoncomponents.textview.CustomTextView
import com.raweng.pagemapper.pagemappersdk.domain.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.mapper.extension.toVariant
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import kotlinx.coroutines.delay

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}

@Composable
fun RenderPageMapper() {
    val mViewModel = viewModel<PageMapperViewModel>()
    val uiState = mViewModel.uiStateLiveData.observeAsState()
    LaunchedEffect(Unit) {
        mViewModel.initData()
    }

    uiState.value?.data?.let {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            LocalRenderView(it, mViewModel.componentDataResults)
        }
    }

    uiState.value?.loading?.let {
        if (it) {
            Greeting("Page Mapper Loading....")
        }
    }
}

@Composable
private fun LocalRenderView(
    response: DynamicScreenResponse?,
    componentDataMap: Map<String, ResponseDataModel>
) {

    response?.components?.forEachIndexed { _, item ->

        val dataModel = componentDataMap[item.uid.orEmpty()]
        val componentData = dataModel?.item
        val apiResponse = dataModel?.apiResponse
        val convertedData = dataModel?.convertedData

        when (Components.fromValue(item.value.orEmpty())) {
            Components.NEWS_FEED_VIEW -> TODO()
            Components.HORIZONTAL_LIST -> TODO()
            Components.HERO_CARD_CAROUSAL_VIEW -> TODO()
            Components.FEATURED_FEEDS -> TODO()
            Components.IMAGE_VIEW -> TODO()
            Components.USER_BANNER -> TODO()
            Components.TEXT_VIEW -> {
                val data = (convertedData as String)
                CustomTextView(data = data, style = item.variant.toVariant())
            }
            Components.GAME_STATS_CARD -> TODO()
            Components.SOCIAL_MEDIA_LIST -> TODO()
            Components.GOOGLE_ADS_VIEW -> TODO()
            Components.SCORE_CARD_CAROUSEL -> TODO()
            Components.RECENT_GAMES -> TODO()
            Components.UTILITY_MENU -> TODO()
            else -> {}
        }
    }
}