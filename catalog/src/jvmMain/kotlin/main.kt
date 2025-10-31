import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kyant.backdrop.catalog.CatalogApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Backdrop Catalog",
    ) {
        CatalogApp()
    }
}