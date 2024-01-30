package com.raweng.pagemapper.pagemappersdk.views.render.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun RenderTextWidgets(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = Color.Yellow
    )
}

@Preview(showBackground = true)
@Composable
fun RenderWidgetsPreview() {
    RenderTextWidgets("Android")
}