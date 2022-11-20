package box.example.showcase.ui.pages.home

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Home

class HomePage :
    Page(
        FontAwesomeIcons.Solid.Home,
        R.string.home_page_route,
        R.string.home_page_title
    ) {
    @Composable
    override fun Content() {
        Column(modifier = Modifier.fillMaxSize()) {
            BoxContent()
        }
    }
}


@Composable
fun BoxContent() {
    val result = remember { mutableStateOf<Bitmap?>(null) }
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .border(4.dp, MaterialTheme.colorScheme.inversePrimary),
        ) {
            Toggles()
        }
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .offset(x = 48.dp, y = 2.dp)
                .clip(CircleShape)
        ) {
            Text(
                text = " Toggles ",
            )
        }
        result.value?.let { image ->
            Image(image.asImageBitmap(), null, modifier = Modifier.fillMaxWidth())
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toggles() {
    OutlinedTextField(
        value = "this is the text",
        onValueChange = {},
        label = { Text("Input") },
        readOnly = true,
        modifier = Modifier.padding(32.dp)
    )
    Text(
        text = "Toggles, Switch, Sliders",
        modifier = Modifier.padding(8.dp)
    )

    var checked by remember { mutableStateOf(true) }
    var switched by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                modifier = Modifier.padding(8.dp),
                onCheckedChange = { checked = !checked })
            Text("check")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = switched,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.padding(8.dp),
                onCheckedChange = { switched = it }
            )
            Text("switch")
        }
    }

    var selected by remember { mutableStateOf("Kotlin") }
    Row(modifier = Modifier.padding(16.dp)) {
        RadioButton(selected = selected == "Kotlin", onClick = { selected = "Kotlin" })
        Text(
            text = "Kotlin",
            modifier = Modifier
                .clickable(onClick = { selected = "Kotlin" })
                .padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        RadioButton(selected = selected == "Java", onClick = { selected = "Java" })
        Text(
            text = "Java",
            modifier = Modifier
                .clickable(onClick = { selected = "Java" })
                .padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        RadioButton(selected = selected == "Swift", onClick = { selected = "Swift" })
        Text(
            text = "Swift",
            modifier = Modifier
                .clickable(onClick = { selected = "Swift" })
                .padding(start = 4.dp)
        )
    }


    var sliderState by remember { mutableStateOf(0f) }

    Slider(value = sliderState, modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        onValueChange = { newValue ->
            sliderState = newValue
        }
    )
    val expanded = remember { mutableStateOf(false) }
    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "Localized description"
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            /*
            DropdownMenuItem(onClick = {
                expanded.value = false
            }) {
                Text("First Item")
            }

            DropdownMenuItem(onClick = {
                expanded.value = false

            }) {
                Text("Second item")
            }
*/
            Divider()
        }
    }
}
