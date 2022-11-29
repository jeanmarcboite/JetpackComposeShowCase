package box.example.showcase.ui.pages.color

import box.example.showcase.R
import box.example.showcase.ui.TabbedPage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cocktail

class ColorPage : TabbedPage(
    listOf(ColorMapTab, ColorThemeTab),
    FontAwesomeIcons.Solid.Cocktail,
    R.string.color_page_route,
    R.string.color_page_title
)