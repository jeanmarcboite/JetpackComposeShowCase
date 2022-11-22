package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.pages.database.LanguageMap
import box.example.showcase.ui.theme.touchpoint_lg
import coil.compose.rememberAsyncImagePainter
import com.ireward.htmlcompose.HtmlText
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
                custom.forEach {
                    Row() {
                        Text("${it.key}:")
                        it.value.forEach {
                            Badge { Text(it.toString()) }
                        }
                    }

                }
                customColumns.forEach {
                    it.View()
                }
                comment?.apply {
                    HtmlText(
                        text = this,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize
                        )
                    )
                }
            }
        }
    }
}
