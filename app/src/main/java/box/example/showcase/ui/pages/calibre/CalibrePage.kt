package box.example.showcase.ui.pages.calibre

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.FileArchive
import compose.icons.fontawesomeicons.solid.FilePdf

class CalibrePage :
    Page(
        FontAwesomeIcons.Solid.FilePdf,
        R.string.calibre_page_route,
        R.string.calibre_page_title
    ) {
    @Composable
    override fun Content() {
        val result = remember { mutableStateOf<Uri?>(null) }
        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
                result.value = it
            }

        Column {
            Text(result.value.toString())

            FloatingActionButton(
                modifier = Modifier
                    //.align(Alignment.BottomEnd)
                    .padding(12.dp),
                onClick = {
                    launcher.launch(null)
                },
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(FontAwesomeIcons.Solid.FileArchive, "")
            }
        }
    }
}