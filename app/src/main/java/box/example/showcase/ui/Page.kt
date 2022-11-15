package box.example.showcase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import box.example.showcase.MainViewModel
import box.example.showcase.ui.navigation.Screen

abstract class Page(
    val icon: ImageVector,
    @StringRes val route: Int,
    @StringRes val title: Int,
    val buttonIcon: ImageVector = Icons.Default.Menu,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    lateinit var mainViewModel: MainViewModel
    open fun showInDrawer() = true

    open fun parseArguments(arguments: Bundle?) {}

    @Composable
    abstract fun Content()

    @SuppressLint("ComposableNaming")
    @Composable
    open fun floatingActionButton() {
    }

    @OptIn(ExperimentalMaterial3Api::class)
    open suspend fun onButtonClicked() {
        mainViewModel.navViewModel.drawerState.open()
    }

    open val screens: List<Screen> = listOf()

}
