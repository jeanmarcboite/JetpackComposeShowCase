package box.example.showcase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.ui.models.NavViewModel
import box.example.showcase.ui.navigation.Tab

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

    @Composable
    open fun BottomAppBar() {
        val navViewModel = hiltViewModel<NavViewModel>()
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Text(
                "Route: ${navViewModel.navController.currentDestination?.route}",
                modifier = Modifier.padding(start = 32.dp)
            )
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    open fun floatingActionButton() {
    }

    @OptIn(ExperimentalMaterial3Api::class)
    open suspend fun onButtonClicked(navViewModel: NavViewModel) {
        navViewModel.drawerState.open()
    }

    open val tabs: List<Tab> = listOf()

}
