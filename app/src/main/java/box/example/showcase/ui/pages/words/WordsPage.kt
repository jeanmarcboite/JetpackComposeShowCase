package box.example.showcase.ui.pages.words

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Eye

class WordsPage :
    Page(
        FontAwesomeIcons.Solid.Eye,
        R.string.words_page_route,
        R.string.words_page_title
    ) {

    @Composable
    override fun Content(openDrawer: () -> Unit) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Words")
        }
    }
}
