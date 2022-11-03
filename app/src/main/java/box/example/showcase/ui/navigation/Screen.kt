package box.example.showcase.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import box.example.showcase.ui.pages.ColorMapScreen
import box.example.showcase.ui.pages.color.ColorThemeScreen

interface Screen {
    val icon: ImageVector
    val route: Int
    val title: Int
    val content: @Composable () -> Unit
}

@Composable
fun ScreenContent(
    navHostController: NavHostController,
    startDestination: Int,
    modifier: Modifier
) {
    Log.d("boxx", "ScreenContent")

    val context = LocalContext.current
    NavHost(
        navController = navHostController,
        startDestination = context.getString(startDestination),
        modifier = modifier
    ) {
        composable(route = context.getString(ColorMapScreen.route)) {
            ColorMapScreen.content()
        }
        composable(route = context.getString(ColorThemeScreen.route)) {
            ColorThemeScreen.content()
        }
    }
}

