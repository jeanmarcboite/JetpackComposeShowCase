package box.example.showcase.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

abstract class Page(
    @StringRes val route: Int,
    @StringRes val title: Int,
) {
    open fun showInDrawer() = true

    @Composable
    abstract fun Content(openDrawer: () -> Unit)
}