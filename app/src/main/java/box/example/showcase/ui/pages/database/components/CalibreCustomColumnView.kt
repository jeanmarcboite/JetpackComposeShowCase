package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreCustomColumn
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.components.data.*

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.View() {
    key.name?.let {
        when (key.datatype) {
            "bool" -> (value.first().toInt() != 0).Bool(it)
            "rating" -> {
                value.first().toFloat().Rating(it)
            }
            "datetime" -> value.Text(it)
            "enumeration" -> key.Enumeration(value)
            else -> value.Badges(it)
        }
    }
}

@Composable
fun CalibreCustomColumn.Enumeration(value: List<String>) {
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
    if (key.datatype == "bool") key.name?.let { (value.first().toInt() != 0).Bool(it) }
}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfComments() {
    if (key.datatype == "comments") key.name?.let { value.HtmlText(it) }
}

