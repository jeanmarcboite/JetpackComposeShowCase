package box.example.showcase.ui.pages.home

import box.example.showcase.R
import box.example.showcase.ui.TabbedPage
import box.example.showcase.ui.pages.color.ColorMapTab
import box.example.showcase.ui.pages.color.ColorThemeTab
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Home

object HomePage :
    TabbedPage(
        listOf(HomeTab, TogglesTab, ColorThemeTab, ColorMapTab),
        FontAwesomeIcons.Solid.Home,
        R.string.home_page_route,
        R.string.home_page_title
    )