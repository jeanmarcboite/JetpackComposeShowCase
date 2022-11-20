package box.example.showcase.ui.pages.database.components

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.pages.database.LanguageMap
import box.example.showcase.ui.theme.touchpoint_lg
import coil.compose.rememberAsyncImagePainter
import com.jsramraj.flags.Flags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreBook.View() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
    }

    OutlinedCard(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        label = {
            Text(authors.joinToString { it.name.toString() })
        }
    ) {
        Row {
            languages.forEach {
                val countryCode = LanguageMap[it]
                if (countryCode != null)
                    Image(
                        painter = rememberAsyncImagePainter(Flags.forCountry(countryCode)),
                        contentDescription = "flag",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(touchpoint_lg)
                            .clip(CircleShape)
                    )
            }
            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = title.toString(),
                    modifier = Modifier
                        .padding(8.dp)
                )
                comment?.apply {
                    HtmlText(this)
                }
            }
        }
    }
}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) }
    ) { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
}
