package com.raweng.pagemapper.poc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.RenderPageMapper
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.viewmodel.PageMapperViewModel
import com.raweng.pagemapper.poc.ui.theme.PagemapperPocTheme

class ClickEventActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PageMapperSDK.setComponentPlaceHolder(
            Components.SOCIAL_MEDIA_LIST,
            R.drawable.mediaplayer_placeholder
        )
        setContent {
            val mViewModel = viewModel<PageMapperViewModel>(key = "MainActivity")
            PagemapperPocTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RenderPageMapper(
                        viewModel = mViewModel,
                        listener = ComponentClickEvent()
                    )
                }
            }


            /*PagemapperPocTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(
                        text = "Hello Hi",
                        modifier = Modifier,
                        fontSize = 50.sp,
                        color = Color.Red
                    )
                }

            }*/


        }
    }
}



