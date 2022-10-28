package box.example.showcase.ui.pages.about

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.InfoCircle

class AboutPage() :
    Page(
        FontAwesomeIcons.Solid.InfoCircle,
        R.string.about_page_route,
        R.string.about_page_title
    ) {
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        Text("Application " + stringResource(id = R.string.app_name))
    }
}