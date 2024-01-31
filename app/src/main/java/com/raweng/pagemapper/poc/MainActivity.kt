package com.raweng.pagemapper.poc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.raweng.pagemapper.pagemappersdk.RenderPageMapper
import com.raweng.pagemapper.pagemappersdk.domain.dependency.RenderPageMapperDependency
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.poc.ui.theme.PagemapperPocTheme

class MainActivity : ComponentActivity() {
    private val mViewModel: PageMapperViewModel by viewModels()
    private val mLiveGameViewModel: LiveGameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mLiveGameViewModel)
        setContent {
            PagemapperPocTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val mConfig = mViewModel.uiStateDFEConfigLiveData.observeAsState()

                    LaunchedEffect(Unit) {
                        mViewModel.fetchData()
                        mLiveGameViewModel.setGameController("MainActivity")
                    }

                    mConfig.value?.let {
                        RenderPageMapper(
                            viewModel = mViewModel,
                            dependency = RenderPageMapperDependency(gameId = "0022300597", parentScreenName = "Splash"),
                            liveGameViewModel = mLiveGameViewModel,
                            listener = ComponentClickEvent()
                        )
                    } ?: Text(text = "Please wait we are fetching the data...")
                }
            }
        }
    }
}



