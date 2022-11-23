package box.example.showcase.ui.pages.database.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreEntity
import box.example.showcase.applib.books.models.calibre.CalibreRating
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreEntityView(calibreEntity: CalibreEntity) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        when (calibreEntity.javaClass) {
            CalibreRating::class.java -> (calibreEntity as CalibreRating).View()
            else -> OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                enabled = false,
                label = { Text(calibreEntity.toString()) },
                value = "Entity",
                onValueChange = {})
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreRating.View() {
    RatingBar(
        value = rating.toFloat(),
        config = RatingBarConfig()
            .activeColor(Color.Yellow)
            .hideInactiveStars(false)
            .inactiveColor(Color.LightGray)
            .inactiveBorderColor(Color.Blue)
            .stepSize(StepSize.ONE)
            .numStars(10)
            .isIndicator(true)
            .size(16.dp)
            .padding(2.dp)
            .style(RatingBarStyle.HighLighted),
        onValueChange = {
            //rating = it
        },
        onRatingChanged = {
            Log.d("TAG", "onRatingChanged: $it")
        }
    )
}