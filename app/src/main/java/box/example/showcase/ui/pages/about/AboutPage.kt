package box.example.showcase.ui.pages.about

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import box.example.showcase.MainViewModel
import box.example.showcase.R
import box.example.showcase.ui.Page


class AboutPage(private val _mainViewModel: MainViewModel) :
    Page(_mainViewModel, Icons.Filled.Info, R.string.about_page_route, R.string.about_page_title) {
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        Text("Application " + stringResource(id = R.string.app_name))
    }
}