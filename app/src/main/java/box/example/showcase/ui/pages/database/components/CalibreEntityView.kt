package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.*
import box.example.showcase.ui.components.OutlinedCard

@Composable
fun CalibreEntityListView(
    title: String,
    showLabel: Boolean,
    calibreEntityList: List<CalibreEntity>?
) {
    if (calibreEntityList != null) {
        if (!showLabel) {
            calibreEntityList.View()
        } else {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = { Text(title) }) {
                calibreEntityList.View()
            }
        }
    }
}

@Composable
fun List<CalibreEntity>.View() {
    forEach {
        CalibreEntityView(calibreEntity = it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreEntityView(calibreEntity: CalibreEntity) {
    Surface(modifier = Modifier.padding(start = 16.dp)) {
        when (calibreEntity.javaClass) {
            CalibreRating::class.java -> ViewRating((calibreEntity as CalibreRating).rating.toFloat())
            CalibrePublishers::class.java -> (calibreEntity as CalibrePublishers).ViewText()
            CalibreSeries::class.java -> (calibreEntity as CalibreSeries).ViewText()
            CalibreTag::class.java -> (calibreEntity as CalibreTag).ViewBadge()
            CalibreData::class.java -> (calibreEntity as CalibreData).View()
            else -> Text(calibreEntity.toString())
        }
    }
}


@Composable
fun CalibreData.View() {
    Row() {
        OutlinedCard(label = { Text("fmt") }) {
            if (format != null) {
                Text(
                    format, textAlign = TextAlign.Center
                )
            }
        }
        OutlinedCard(label = { Text("size") }) {
            Text(uncompressed_size.toString())
        }
        OutlinedCard(label = { Text("name") }) {
            if (name != null) {
                Text(name)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreNamedEntity.ViewText() {
    Text(name ?: "null")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreNamedEntity.ViewBadge() {
    Badge(modifier = Modifier.padding(4.dp)) { Text(name ?: "null") }
}
