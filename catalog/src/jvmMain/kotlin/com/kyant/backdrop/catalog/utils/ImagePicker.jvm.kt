package com.kyant.backdrop.catalog.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
actual fun rememberImagePicker(onResult: (PlatformImage?) -> Unit): ImagePicker {
    return remember { ImagePicker(onResult) }
}

actual class ImagePicker(
    private val onResult: (PlatformImage?) -> Unit
) {
    actual fun launch() {
        val fileDialog = FileDialog(null as Frame?, "Select Image", FileDialog.LOAD)
        fileDialog.setFilenameFilter { _, name ->
            name.lowercase().run {
                endsWith(".png") || endsWith(".jpg") ||
                endsWith(".jpeg") || endsWith(".webp")
            }
        }
        fileDialog.isVisible = true

        val file = fileDialog.file
        val directory = fileDialog.directory

        if (file != null && directory != null) {
            try {
                val selectedFile = File(directory, file)
                val bytes = selectedFile.readBytes()
                onResult(PlatformImage(bytes))
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }
    }
}
