package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.*
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.components.data.Rating

@Composable
fun List<CalibreEntity>?.View(title: String) {
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
            CalibreRating::class.java -> (this as CalibreRating).rating.toFloat().Rating()
            CalibrePublishers::class.java -> (this as CalibrePublishers).Text()
            CalibreSeries::class.java -> (this as CalibreSeries).Text()
            CalibreTag::class.java -> (this as CalibreTag).Badge()
            CalibreData::class.java -> (this as CalibreData).View()
            else -> Text(this.toString())
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreNamedEntity.Text() {
    Text(name ?: "null")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreNamedEntity.Badge() {
    Badge(modifier = Modifier.padding(4.dp)) { Text(name ?: "null") }
}
