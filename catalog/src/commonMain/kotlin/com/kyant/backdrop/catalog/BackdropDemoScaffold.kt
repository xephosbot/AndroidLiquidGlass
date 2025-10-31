package com.kyant.backdrop.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.catalog.components.LiquidButton
import com.kyant.backdrop.catalog.utils.rememberImagePicker
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BackdropDemoScaffold(
    modifier: Modifier = Modifier,
    initialPainterResId: DrawableResource = Res.drawable.wallpaper_light,
    content: @Composable BoxScope.(backdrop: LayerBackdrop) -> Unit
) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var painter: Painter? by remember { mutableStateOf(null) }
        val pickMedia = rememberImagePicker { image ->
            val imageBitmap = image?.toImageBitmap()
            if (imageBitmap != null) {
                painter = BitmapPainter(imageBitmap)
            }
        }

        val backdrop = rememberLayerBackdrop()

        Image(
            painter = painter ?: painterResource(initialPainterResId),
            contentDescription = null,
            Modifier
                .layerBackdrop(backdrop)
                .then(modifier)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        content(backdrop)

        LiquidButton(
            { pickMedia.launch() },
            backdrop,
            Modifier
                .padding(16f.dp)
                .navigationBarsPadding()
                .height(56f.dp)
                .align(Alignment.BottomCenter),
            tint = Color(0xFF0088FF)
        ) {
            BasicText(
                "Pick an image",
                Modifier.padding(horizontal = 8f.dp),
                style = TextStyle(Color.White, 16f.sp)
            )
        }
    }
}
