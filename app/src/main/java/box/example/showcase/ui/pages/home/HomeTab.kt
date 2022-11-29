package box.example.showcase.ui.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import box.example.showcase.R
import box.example.showcase.ui.Tab
import compose.icons.TablerIcons
import compose.icons.tablericons.Home2


object HomeTab : Tab(
    TablerIcons.Home2,
    R.string.home_tab_route,
    R.string.home_tab_title
) {
    @Composable
    override fun Content() {
        val model: HomeViewModel = viewModel()
        val hiltModel: HiltHomeViewModel = hiltViewModel()
        Column() {
            Text("viewModel[${model.id}] = $model")
            Text("hiltViewModel[${model.id}] = $hiltModel")
        }
    }
}
