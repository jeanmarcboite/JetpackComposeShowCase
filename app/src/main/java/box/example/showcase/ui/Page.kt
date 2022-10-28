package box.example.showcase.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import box.example.showcase.MainViewModel

abstract class Page(
    val mainViewModel: MainViewModel,
    val icon: ImageVector,
    @StringRes val route: Int,
    @StringRes val title: Int,
    val buttonIcon: ImageVector = Icons.Default.Menu
) {
    open fun showInDrawer() = true

    @Composable
    abstract fun Content(openDrawer: () -> Unit)

    @OptIn(ExperimentalMaterial3Api::class)
    open suspend fun onButtonClicked(): Unit {
        mainViewModel.drawerState.open()
    }
}
