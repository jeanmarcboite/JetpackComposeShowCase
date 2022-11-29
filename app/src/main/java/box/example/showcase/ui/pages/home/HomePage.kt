package box.example.showcase.ui.pages.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import box.example.showcase.R
import box.example.showcase.applib.ui.components.MultiFloatingActionButton
import box.example.showcase.ui.TabbedPage
import box.example.showcase.ui.pages.color.ColorMapTab
import box.example.showcase.ui.pages.color.ColorThemeTab
import compose.icons.TablerIcons
import compose.icons.tablericons.Home

object HomePage :
    TabbedPage(
        listOf(HomeTab, TogglesTab, ColorThemeTab, ColorMapTab),
        TablerIcons.Home,
        R.string.home_page_route,
        R.string.home_page_title
    ) {
    override val floatingActionButton: @Composable () -> Unit = {
        val expanded = remember {
            mutableStateOf(false)
        }
        MultiFloatingActionButton(expanded)
    }
}