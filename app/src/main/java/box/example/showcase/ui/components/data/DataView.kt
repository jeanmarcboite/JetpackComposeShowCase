package box.example.showcase.ui.components.data

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import box.example.showcase.ui.components.OutlinedCard
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
