package box.example.showcase.ui.pages.database.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreCustomColumn
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.components.data.Bool
import box.example.showcase.ui.components.data.Rating
import box.example.showcase.ui.components.data.ViewHtml

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
                    it.Language()
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
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.View() {
    key.name?.let {
        when (key.datatype) {
            "bool" -> (value.first().toInt() != 0).Bool(it)
            "rating" -> {
                value.first().toFloat().Rating(it)
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
    if (key.datatype == "bool") key.name?.let { (value.first().toInt() != 0).Bool(it) }
}


@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfComments() {
    if (key.datatype == "comments") key.name?.let { value.ViewHtml(it) }
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



