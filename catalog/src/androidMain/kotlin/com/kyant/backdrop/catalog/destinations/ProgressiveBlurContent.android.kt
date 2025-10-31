package com.kyant.backdrop.catalog.destinations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.toArgb
import com.kyant.backdrop.getRuntimeShaderCache
import org.intellij.lang.annotations.Language

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal actual fun createProgressiveBlurRuntimeShaderEffect(
    size: Size,
    tint: Color,
    tintIntensity: Float
): RenderEffect {
    return android.graphics.RenderEffect.createRuntimeShaderEffect(
        getRuntimeShaderCache().obtainRuntimeShader("AlphaMask", AlphaMaskShaderString).apply {
            setFloatUniform("size", size.width, size.height)
            setColorUniform("tint", tint.toArgb())
            setFloatUniform("tintIntensity", tintIntensity)
        },
        "content"
    ).asComposeRenderEffect()
}

@Language("AGSL")
internal const val AlphaMaskShaderString = """
    uniform shader content;

    uniform float2 size;
    layout(color) uniform half4 tint;
    uniform float tintIntensity;
    
    half4 main(float2 coord) {
        float blurAlpha = smoothstep(size.y, size.y * 0.5, coord.y);
        float tintAlpha = smoothstep(size.y, size.y * 0.5, coord.y);
        return mix(content.eval(coord) * blurAlpha, tint * tintAlpha, tintIntensity);
    }
"""
