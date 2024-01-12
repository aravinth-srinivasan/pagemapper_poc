package com.raweng.pagemapper.poc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.raweng.pagemapper.pagemappersdk.Greeting
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.RenderPageMapper
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.poc.ui.theme.PagemapperPocTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PageMapperSDK.setPlaceHolder(
            Components.SOCIAL_MEDIA_LIST,
            R.drawable.mediaplayer_placeholder
        )
        setContent {
            PagemapperPocTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RenderPageMapper()
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



