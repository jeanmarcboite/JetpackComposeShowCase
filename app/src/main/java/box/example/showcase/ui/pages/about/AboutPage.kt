package box.example.showcase.ui.pages.about

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.TablerIcons
import compose.icons.tablericons.InfoCircle

class AboutPage :
    Page(
        TablerIcons.InfoCircle,
        R.string.about_page_route,
        R.string.about_page_title
    ) {
    @Composable
    override fun Content() {
        Text("Application " + stringResource(id = R.string.app_name))
    }
}