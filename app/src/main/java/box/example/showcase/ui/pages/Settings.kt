package box.example.showcase.ui.pages

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.notes.NoteAction

class Settings() :
    Page(
        Icons.Filled.Settings,
        R.string.settings_page_route, R.string.settings_page_title,
        Icons.Default.ArrowBack
    ) {
    init {
    }

    override fun showInDrawer() = false

    @Composable
    override fun Content(openDrawer: () -> Unit) {
        Log.w("boxxxw", "Settings")
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SwipeActions()
        }
    }

    @Composable
    fun OutlinedBox(
        label: String,
        Content: @Composable (() -> Unit)
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .border(4.dp, MaterialTheme.colorScheme.primary),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Content()
                }
            }
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .offset(x = 48.dp, y = 2.dp)
                    .clip(CircleShape)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Cursive),
                )
            }
        }
    }

    @Composable
    fun SwipeActions() {
        Column() {
            SwipeActions(
                "Left Swipe",
                mainViewModel.leftSwipeToReveal,
                mainViewModel.leftSwipeAction,
                mainViewModel.leftSwipeActions,
            )
            SwipeActions(
                "Right Swipe",
                mainViewModel.rightSwipeToReveal,
                mainViewModel.rightSwipeAction,
                mainViewModel.rightSwipeActions,
            )
        }
    }

    @Composable
    fun SwipeActions(
        label: String,
        swipeToReveal: MutableState<Boolean>,
        swipeAction: MutableState<NoteAction>,
        swipeActions: List<MutableState<NoteAction>>,
    ) {
        OutlinedBox(label) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("swipe to")
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LabeledRadioButton(text = "run action", selected = !swipeToReveal.value) {
                        swipeToReveal.value = false
                    }
                    LabeledRadioButton(text = "reveal", selected = swipeToReveal.value) {
                        swipeToReveal.value = true
                    }
                }
                if (!swipeToReveal.value) {
                    SelectButton(
                        "Action: ",
                        noteAction = swipeAction
                    )
                }
                if (swipeToReveal.value) {
                    (1..swipeActions.size).forEach {
                        SelectButton(
                            "Action $it:",
                            noteAction = swipeActions[it - 1]
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun LabeledRadioButton(text: String, selected: Boolean, onClick: () -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = selected, onClick = onClick)
            Text(
                text = text,
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .padding(start = 4.dp)
            )
        }

    }

    @Composable
    fun SelectButton(label: String, noteAction: MutableState<NoteAction>) {
        val expanded = remember { mutableStateOf(false) }

        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    expanded.value = true
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("$label [${noteAction.value}]")
            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = "Localized description"
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                val actions = NoteAction.values().map { it -> it.toString() }.toList()
                actions.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(it)
                        },
                        onClick = {
                            expanded.value = false
                            noteAction.value = NoteAction.valueOf(it)
                        })
                }
            }
        }
    }

    @Composable
    fun RadioButtons(label: String, noteAction: MutableState<NoteAction>) {
        val buttons = NoteAction.values().map { it -> it.toString() }.toList()
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label)
            buttons.forEach {
                Row() {
                    RadioButton(selected = noteAction.value.toString() == it,
                        onClick = { noteAction.value = NoteAction.valueOf(it) })
                    Text(
                        text = it,
                        modifier = Modifier
                            .clickable(onClick = { noteAction.value = NoteAction.valueOf(it) })
                            .padding(start = 4.dp)
                    )
                }
            }
        }
    }
}