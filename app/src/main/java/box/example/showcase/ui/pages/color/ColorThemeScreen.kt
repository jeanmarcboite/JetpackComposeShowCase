package box.example.showcase.ui.pages.color

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import box.example.showcase.R
import box.example.showcase.ui.navigation.Screen
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.PaintBrush

object ColorThemeScreen : Screen {
    override val icon = FontAwesomeIcons.Solid.PaintBrush
    override val route = R.string.color_theme_route
    override val title = R.string.color_theme_title
    override val content: @Composable () -> Unit = {
        Log.d("boxx", "ColorThemeScreen")
        Text("Color App Theme")
    }
}