package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreCustomColumn
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.pages.database.LanguageMap
import box.example.showcase.ui.theme.touchpoint_lg
import coil.compose.rememberAsyncImagePainter
import com.ireward.htmlcompose.HtmlText
import com.jsramraj.flags.Flags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreBook.View() {
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    customColumns.forEach { entry: Map.Entry<CalibreCustomColumn, MutableList<String>> ->
                        entry.ViewIfBool()
                    }

                    Text(
                        text = title.toString(),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }

                val columnsList = mapOf("ratings" to false, "publishers" to true)
                columnsList.forEach {
                    columns[it.key]?.apply { CalibreEntityListView(it.key, it.value, this) }
                }

                columns.forEach {
                    if (it.key !in (columnsList))
                        OutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("${it.key}:") }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                it.value.forEach {
                                    Badge {
                                        CalibreEntityView(calibreEntity = it)
                                    }
                                }
                            }
                        }
                }
                customColumns.forEach { entry: Map.Entry<CalibreCustomColumn, MutableList<String>> ->
                    entry.ViewIfComments()
                }
                customColumns.forEach {
                    if (it.key.datatype != "bool" && it.key.datatype != "comments") {
                        OutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("${it.key.name}:") }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                it.value.forEach {
                                    Badge { Text(it) }
                                }
                            }
                        }
                    }
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

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfBool() {
    if (key.datatype == "bool") {
        OutlinedCard(
            modifier = Modifier.defaultMinSize(minWidth = 72.dp),
            label = { Text("${key.name}") }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Checkbox(
                    checked = value.first().toInt() != 0,
                    enabled = false, onCheckedChange = {}
                )
            }
        }
    }
}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfComments() {
    if (key.datatype == "comments") {
        OutlinedCard(
            modifier = Modifier.fillMaxSize(),
            label = { Text("${key.name}") }
        ) {
            value?.apply {
                forEach {
                    HtmlText(
                        text = it,
                        modifier = Modifier.padding(start = 8.dp),
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
