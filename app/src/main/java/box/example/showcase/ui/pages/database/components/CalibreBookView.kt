package box.example.showcase.ui.pages.database.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreCustomColumn
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.pages.database.LanguageMap
import box.example.showcase.ui.theme.touchpoint_lg
import coil.compose.rememberAsyncImagePainter
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.ireward.htmlcompose.HtmlText
import com.jsramraj.flags.Flags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreBook.View() {
    OutlinedCard(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), label = {
            Text(authors.joinToString { it.name.toString() })
        }) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                languages.forEach {
                    LanguageView(it)
                }
                customColumns.forEach { entry: Map.Entry<CalibreCustomColumn, MutableList<String>> ->
                    entry.ViewIfBool()
                }

                Text(
                    text = title.toString(), modifier = Modifier.padding(8.dp)
                )
            }
            // Map<Column, ShowLabel>
            val columnsList = mapOf(
                "tags" to false,
                "series" to true,
                "ratings" to false,
            )
            columnsList.forEach {
                Row {
                    columns[it.key]?.apply { CalibreEntityListView(it.key, it.value, this) }
                }
            }
            Log.d("boxxx [columns]", "${columns.keys}")
            columns.forEach {
                if (it.key !in (columnsList.keys)) {
                    CalibreEntityListView(it.key, true, it.value)
                }
            }
            customColumns.forEach { entry: Map.Entry<CalibreCustomColumn, MutableList<String>> ->
                entry.ViewIfComments()
            }
            customColumns.forEach { column ->
                if (column.key.datatype != "bool" && column.key.datatype != "comments") {
                    column.View()
                }
            }
            comment?.apply {
                ViewComment(label = "Comments", value = listOf(this))
            }
        }
    }
}

@Composable
fun LanguageView(language: String) {
    val countryCode = LanguageMap[language]
    if (countryCode != null) Image(
        painter = rememberAsyncImagePainter(Flags.forCountry(countryCode)),
        contentDescription = "flag",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(touchpoint_lg)
            .clip(CircleShape)
    )
}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.View() {
    key.name?.let {
        when (key.datatype) {
            "bool" -> ViewBool(label = it, value = value)
            "rating" -> {
                ViewRating(label = it, value = value.first().toFloat())
            }
            "datetime" -> ViewText(label = it, value = value)
            "enumeration" -> ViewText(label = it, value = value, color = key.color)
            else -> ViewBadges(label = it, value = value)
        }
    }
}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfBool() {
    if (key.datatype == "bool") key.name?.let { ViewBool(label = it, value = value) }
}

@Composable
fun ViewBool(label: String, value: List<String>) {
    OutlinedCard(modifier = Modifier.defaultMinSize(minWidth = 72.dp), label = { Text(label) }) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            Checkbox(checked = value.first().toInt() != 0, enabled = false, onCheckedChange = {})
        }
    }
}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfComments() {
    if (key.datatype == "comments") key.name?.let { ViewComment(label = it, value = value) }
}

@Composable
fun ViewComment(label: String, value: List<String>) {
    OutlinedCard(modifier = Modifier.fillMaxSize(), label = { Text(label) }) {
        value.forEach {
            HtmlText(
                text = it, modifier = Modifier.padding(start = 8.dp), style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewBadges(label: String, value: List<String>) {
    OutlinedCard(modifier = Modifier.fillMaxWidth(), label = { Text(label) }) {
        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            value.forEach {
                Badge { Text(it) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewText(
    label: String,
    value: List<String>,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    OutlinedCard(modifier = Modifier.fillMaxWidth(), label = { Text(label) }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            value.forEach {
                Text(it, color = color)
            }
        }
    }
}

@Composable
fun ViewRating(label: String, value: Float) {
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), label = { Text(label) }) {
        ViewRating(value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewRating(value: Float) {
    RatingBar(
        value = value,
        config = RatingBarConfig().activeColor(Color.Yellow).hideInactiveStars(false)
            .inactiveColor(Color.LightGray).inactiveBorderColor(Color.Blue).stepSize(StepSize.ONE)
            .numStars(10).isIndicator(true).size(16.dp).padding(2.dp)
            .style(RatingBarStyle.HighLighted),
        onValueChange = {
            //rating = it
        },
        onRatingChanged = {
            Log.d("TAG", "onRatingChanged: $it")
        })
}

