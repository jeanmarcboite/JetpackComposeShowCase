package box.example.showcase.ui.components.data

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import box.example.showcase.ui.components.OutlinedCard
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.ireward.htmlcompose.HtmlText

@Composable
fun Boolean.Bool(label: String) {
    OutlinedCard(modifier = Modifier.defaultMinSize(minWidth = 72.dp), label = { Text(label) }) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            Checkbox(checked = this@Bool, enabled = false, onCheckedChange = {})
        }
    }
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

@Composable
fun Float.Rating() {
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

@Composable
fun Float.Rating(label: String) {
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), label = { Text(label) }) {
        Rating()
    }
}
