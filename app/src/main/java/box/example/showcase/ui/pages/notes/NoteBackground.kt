package box.example.showcase.ui.pages.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.notes.models.Note
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Star
import compose.icons.evaicons.outline.Star
import compose.icons.evaicons.outline.Undo

@Composable
fun ButtonRow(
    note: Note,
    buttons: List<NoteAction>,
    offset: MutableState<Float>,
    swiped: MutableState<Boolean>,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    onNoteChanged: (Note) -> Unit,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)

    fun swipeBack() {
        offset.value = 0f
        swiped.value = false
    }
    Row(
        modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        buttons.forEach { button ->
            when (button) {
                NoteAction.Star -> IconButton(modifier = Modifier.size(ACTION_ITEM_SIZE.dp),
                    onClick = {
                        onNoteChanged(note.copy(star = !note.star))
                        swipeBack()
                    },
                    content = {
                        Icon(
                            if (note.star) EvaIcons.Fill.Star else EvaIcons.Outline.Star,
                            tint = Color.Red,
                            contentDescription = "star action",
                        )
                    }
                )
                NoteAction.Undo -> IconButton(
                    modifier = Modifier.size(ACTION_ITEM_SIZE.dp),
                    onClick = { },
                    content = {
                        Icon(
                            EvaIcons.Outline.Undo,
                            tint = Color.Gray,
                            contentDescription = "edit action",
                        )
                    },
                )
                NoteAction.Delete -> IconButton(
                    modifier = Modifier.size(ACTION_ITEM_SIZE.dp),
                    onClick = {
                        onNoteChanged(note.copy(deleted = !note.deleted))
                        swipeBack()
                    },
                    content = {
                        Icon(
                            Icons.Filled.Delete,
                            tint = Color.Red,
                            contentDescription = "delete action",
                        )
                    }
                )
                NoteAction.Archive -> IconButton(
                    modifier = Modifier.size(ACTION_ITEM_SIZE.dp),
                    onClick = {
                        onNoteChanged(note.copy(archived = !note.archived))
                        swipeBack()
                    },
                    content = {
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            tint = Color.Red,
                            contentDescription = "archive action",
                        )
                    }
                )
                NoteAction.None -> {
                }
            }
        }
    }
}


@Composable
fun NoteBackground(
    note: Note,
    leftButtons: List<NoteAction>,
    rightButtons: List<NoteAction>,
    offset: MutableState<Float>,
    swiped: MutableState<Boolean>,
    onNoteChanged: (Note) -> Unit,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)

    fun swipeBack() {
        offset.value = 0f
        swiped.value = false
    }

    /**
     * Left
     */
    ButtonRow(
        note = note,
        buttons = leftButtons,
        offset = offset,
        swiped = swiped,
        onNoteChanged = onNoteChanged
    )
    /**
     * Right
     */
    ButtonRow(
        note = note,
        buttons = rightButtons,
        offset = offset,
        swiped = swiped,
        horizontalArrangement = Arrangement.End,
        onNoteChanged = onNoteChanged
    )
}
