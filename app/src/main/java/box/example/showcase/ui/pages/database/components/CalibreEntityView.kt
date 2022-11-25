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
fun List<CalibreEntity>?.View(
    title: String
) {
    this?.apply {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            label = { Text(title) }) {
            View()
        }
    }
}

@Composable
fun List<CalibreEntity>?.View() {
    this?.forEach {
        it.View()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreEntity.View() {
    Surface(modifier = Modifier.padding(start = 16.dp)) {
        when (javaClass) {
            CalibreRating::class.java -> (this as CalibreRating).rating.toFloat().ViewRating()
            CalibrePublishers::class.java -> (this as CalibrePublishers).ViewText()
            CalibreSeries::class.java -> (this as CalibreSeries).ViewText()
            CalibreTag::class.java -> (this as CalibreTag).ViewBadge()
            CalibreData::class.java -> (this as CalibreData).View()
            else -> Text(this.toString())
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
