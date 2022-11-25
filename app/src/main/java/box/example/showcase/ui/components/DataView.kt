package box.example.showcase.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
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
fun List<String>.HtmlText(label: String) {
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
fun List<String>.Badges(label: String) {
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
fun List<String>.Text(
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
fun Float.Rating(label: String) {
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), label = { Text(label) }) {
        Rating()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
