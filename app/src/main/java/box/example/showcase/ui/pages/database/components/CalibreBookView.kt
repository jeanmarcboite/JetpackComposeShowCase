package box.example.showcase.ui.pages.database.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreCustomColumn
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.components.data.HtmlText
import box.example.showcase.ui.components.data.Language

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
                listOf(this).HtmlText("Comments")
            }
        }
    }
}

