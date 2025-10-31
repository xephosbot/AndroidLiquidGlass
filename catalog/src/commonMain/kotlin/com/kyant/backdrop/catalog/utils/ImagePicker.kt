package com.kyant.backdrop.catalog.utils

import androidx.compose.runtime.Composable

@Composable
expect fun rememberImagePicker(onResult: (PlatformImage?) -> Unit): ImagePicker

expect class ImagePicker {
    fun launch()
}