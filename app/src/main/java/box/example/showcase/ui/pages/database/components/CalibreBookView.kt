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
                    it.LanguageView()
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
                    columns[it.key]?.apply {
                        if (it.value)
                            View(it.key)
                        else
                            View()
                    }
                }
            }
            Log.d("boxxx [columns]", "${columns.keys}")
            columns.forEach {
                if (it.key !in (columnsList.keys)) {
                    it.value.View(it.key)
                }
            }
            customColumns.forEach { entry: Map.Entry<CalibreCustomColumn, MutableList<String>> ->
                entry.ViewIfComments()
            }
            customColumns.forEach { column: Map.Entry<CalibreCustomColumn, MutableList<String>> ->
                if (column.key.datatype != "bool" && column.key.datatype != "comments") {
                    column.View()
                }
            }
            comment?.apply {
                listOf(this).ViewHtml("Comments")
            }
        }
    }
}

@Composable
fun String.LanguageView() {
    val countryCode = LanguageMap[this]
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
            "bool" -> (value.first().toInt() != 0).View(it)
            "rating" -> {
                value.first().toFloat().ViewRating(it)
            }
            "datetime" -> value.View(it)
            "enumeration" -> key.ViewEnumeration(value)
            else -> value.ViewBadges(it)
        }
    }
}

@Composable
fun CalibreCustomColumn.ViewEnumeration(value: List<String>) {
    OutlinedCard(modifier = Modifier.fillMaxWidth(), label = { Text(name.toString()) }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            value.forEach {
                Text(it, color = colorMap[it] ?: Color.Red)
            }
        }
    }

}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfBool() {
    if (key.datatype == "bool") key.name?.let { (value.first().toInt() != 0).View(it) }
}

@Composable
fun Boolean.View(label: String) {
    OutlinedCard(modifier = Modifier.defaultMinSize(minWidth = 72.dp), label = { Text(label) }) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            Checkbox(checked = this@View, enabled = false, onCheckedChange = {})
        }
    }
}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfComments() {
    if (key.datatype == "comments") key.name?.let { value.ViewHtml(it) }
}

@Composable
fun List<String>.ViewHtml(label: String) {
    OutlinedCard(modifier = Modifier.fillMaxSize(), label = { Text(label) }) {
        forEach {
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
fun List<String>.ViewBadges(label: String) {
    OutlinedCard(modifier = Modifier.fillMaxWidth(), label = { Text(label) }) {
        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            forEach {
                Badge { Text(it) }
            }
        }
    }
}

@Composable
fun List<String>.View(
    label: String,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    OutlinedCard(modifier = Modifier.fillMaxWidth(), label = { Text(label) }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            forEach {
                Text(it, color = color)
            }
        }
    }
}

@Composable
fun Float.ViewRating(label: String) {
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), label = { Text(label) }) {
        ViewRating()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Float.ViewRating() {
    RatingBar(
        value = this,
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

