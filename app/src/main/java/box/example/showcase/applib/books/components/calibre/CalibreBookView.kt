package box.example.showcase.applib.books.components.calibre

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreCustomColumn
import box.example.showcase.applib.ui.components.data.*
import box.example.showcase.ui.components.OutlinedCard

@Composable
fun CalibreBook.ViewHeader(columnsList: Map<String, Boolean>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        languages.forEach {
            it.Language()
        }

        Text(
            text = title.toString(), modifier = Modifier.padding(8.dp)
        )
    }

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

}

@Composable
fun CalibreBook.ViewSummary(onClick: () -> Unit = {}) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), label = {
            Text(authors.joinToString { it.name.toString() })
        }) {
        Column(modifier = Modifier.padding(8.dp)) {
            ViewHeader(
                mapOf(
                    "tags" to false,
                    "series" to true,
                )
            )
        }
    }
}

@Composable
fun CalibreBook.ViewDetails() {
    val scroll = rememberScrollState(0)
    Column(modifier = Modifier.padding(8.dp)) {
        Text(authors.joinToString { it.name.toString() })
        val columnsList = mapOf(
            "tags" to false,
            "series" to true,
            "ratings" to false,
        )
        ViewHeader(columnsList)
        Column(modifier = Modifier.verticalScroll(scroll)) {
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
            else -> value.Badges(it)
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

