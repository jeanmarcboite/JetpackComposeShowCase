package box.example.showcase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import box.example.showcase.ui.models.NavViewModel

abstract class Page(
    val icon: ImageVector,
    @StringRes val route: Int,
    @StringRes val title: Int,
    val buttonIcon: ImageVector = Icons.Default.Menu,
) {
    open fun showInDrawer() = true

    open fun parseArguments(arguments: Bundle?) {}

    @Composable
    abstract fun Content()

    @SuppressLint("ComposableNaming")
    @Composable
    open fun floatingActionButton() {
    }

    @OptIn(ExperimentalMaterial3Api::class)
    open suspend fun onButtonClicked(navViewModel: NavViewModel) {
        navViewModel.drawerState.open()
    }
}
