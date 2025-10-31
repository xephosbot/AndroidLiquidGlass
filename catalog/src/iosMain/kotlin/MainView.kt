import androidx.compose.ui.window.ComposeUIViewController
import com.kyant.backdrop.catalog.CatalogApp
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    CatalogApp()
}
