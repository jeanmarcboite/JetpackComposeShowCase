package box.example.showcase.ui.pages.lab

import androidx.compose.runtime.Composable
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FeatherIcons
import compose.icons.feathericons.Codepen

class LabPage :
    Page(
        FeatherIcons.Codepen,
        R.string.lab_page_route,
        R.string.lab_page_title
    ) {
    @Composable
    override fun Content() {
    }
}
