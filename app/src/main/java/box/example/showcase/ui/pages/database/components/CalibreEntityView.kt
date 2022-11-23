package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.books.models.calibre.CalibreEntity
import box.example.showcase.applib.books.models.calibre.CalibreRating

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
fun CalibreEntity.View() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            enabled = false,
            label = { Text(id.toString()) },
            value = "Entity",
            onValueChange = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibreRating.View() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            enabled = false,
            label = { Text("rating") },
            value = rating.toString(),
            onValueChange = {})
    }
}