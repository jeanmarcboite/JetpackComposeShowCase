package box.example.showcase.ui.pages.color

import box.example.showcase.R
import box.example.showcase.ui.TabPage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cocktail

class ColorPage : TabPage(
    FontAwesomeIcons.Solid.Cocktail,
    R.string.color_page_route,
    R.string.color_page_title
) {
    override val tabs = listOf(ColorMapTab, ColorThemeTab)
}
