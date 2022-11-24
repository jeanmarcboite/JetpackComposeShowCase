package box.example.showcase.ui.pages.database.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.*
import box.example.showcase.ui.components.OutlinedCard
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun CalibreEntityListView(
    title: String,
    showLabel: Boolean,
    calibreEntityList: List<CalibreEntity>?
) {
    if (calibreEntityList != null) {
        if (showLabel) {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = { Text(title) }) {
                calibreEntityList.forEach {
                    CalibreEntityView(calibreEntity = it)
                }
            }
        } else {
            calibreEntityList.forEach {
                CalibreEntityView(calibreEntity = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreEntityView(calibreEntity: CalibreEntity) {
    when (calibreEntity.javaClass) {
        CalibreRating::class.java -> (calibreEntity as CalibreRating).View()
        CalibrePublishers::class.java -> (calibreEntity as CalibrePublishers).ViewText()
        CalibreTag::class.java -> (calibreEntity as CalibreTag).ViewBadge()
        else -> Text(calibreEntity.toString())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreRating.View() {
    RatingBar(value = rating.toFloat(),
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
