package com.kyant.backdrop.catalog.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual class PlatformImage(
    actual val data: ByteArray
) {
    private val bitmap: Bitmap by lazy {
        BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    actual val width: Int get() = bitmap.width
    actual val height: Int get() = bitmap.height

    actual fun toImageBitmap(): ImageBitmap {
        return bitmap.asImageBitmap()
    }
}
