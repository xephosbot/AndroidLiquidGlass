package com.kyant.backdrop.catalog.destinations

import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.catalog.BackdropDemoScaffold
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.drawPlainBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.effect
import com.kyant.backdrop.utils.PlatformCapabilities

@Composable
fun ProgressiveBlurContent() {
    val isLightTheme = !isSystemInDarkTheme()
    val contentColor = if (isLightTheme) Color.Black else Color.White
    val tintColor = if (isLightTheme) Color.White else Color(0xFF808080)

    BackdropDemoScaffold { backdrop ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16f.dp)
        ) {
            Box(
                Modifier
                    .drawPlainBackdrop(
                        backdrop = backdrop,
                        shape = { RectangleShape },
                        effects = {
                            blur(4f.dp.toPx())
                            if (PlatformCapabilities.hasAdvancedShaderCapability()) {
                                effect(
                                    createProgressiveBlurRuntimeShaderEffect(
                                        size = size,
                                        tint = tintColor,
                                        tintIntensity = 0.8f,
                                    )
                                )
                            }
                        }
                    )
                    .height(128f.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BasicText("alpha-masked progressive blur", style = TextStyle(contentColor, 16f.sp))
            }
        }
    }
}

internal expect fun createProgressiveBlurRuntimeShaderEffect(
    size: Size,
    tint: Color,
    tintIntensity: Float
) : RenderEffect
