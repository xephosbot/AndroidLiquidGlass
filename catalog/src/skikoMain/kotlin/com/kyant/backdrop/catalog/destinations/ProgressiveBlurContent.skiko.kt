package com.kyant.backdrop.catalog.destinations

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.toArgb
import com.kyant.backdrop.getRuntimeShaderCache
import org.jetbrains.skia.ImageFilter
import org.jetbrains.skia.RuntimeShaderBuilder

internal actual fun createProgressiveBlurRuntimeShaderEffect(
    size: Size,
    tint: Color,
    tintIntensity: Float
): RenderEffect {
    val shaderBuilder = RuntimeShaderBuilder(
        getRuntimeShaderCache().obtainRuntimeShader("AlphaMask", AlphaMaskShaderString)
    ).apply {
        uniform("size", size.width, size.height)
        uniform("tint", tint.red, tint.green, tint.blue, tint.alpha)
        uniform("tintIntensity", tintIntensity)
    }
    return ImageFilter.makeRuntimeShader(
        runtimeShaderBuilder = shaderBuilder,
        shaderNames = arrayOf("content"),
        inputs = arrayOf(null),
    ).asComposeRenderEffect()
}

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