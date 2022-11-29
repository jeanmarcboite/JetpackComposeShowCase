package box.example.showcase.ui.pages.color

import box.example.showcase.R
import box.example.showcase.ui.TabbedPage
import compose.icons.TablerIcons
import compose.icons.tablericons.Palette

class ColorPage : TabbedPage(
    listOf(ColorMapTab, ColorThemeTab),
    TablerIcons.Palette,
    R.string.color_page_route,
    R.string.color_page_title
)