package box.example.showcase.ui.pages.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import box.example.showcase.applib.notes.models.Note
import box.example.showcase.applib.ui.components.SwipeableCard

const val ACTION_ITEM_SIZE = 56
const val CARD_HEIGHT = 80
const val CARD_OFFSET = 500f // we have 3 icons in a row, so that's 56 * 3
const val SWIPE_THRESHOLD = 0.2f

@Composable
fun NoteForeground(
    note: Note,
    offset: MutableState<Float>,
    swiped: MutableState<Boolean>,
    onClick: () -> Unit = {},
    onNoteChanged: (Note) -> Unit
) {
    SwipeableCard(
        offset,
        swiped,
        height = CARD_HEIGHT.dp,
        swipeThreshold = SWIPE_THRESHOLD,
        swipeOffset = CARD_OFFSET,
        backgroundColor = if (swiped.value)
            MaterialTheme.colorScheme.surfaceVariant
        else
            MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(Modifier.clickable(onClick = onClick)) {
            Checkbox(
                checked = note.completed,
                onCheckedChange = {
                    onNoteChanged(note.copy(completed = !note.completed))
                },
                modifier = Modifier.padding(8.dp, 0.dp)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                val color = if (note.star) Color.Red else MaterialTheme.colorScheme.onBackground
                val textDecoration =
                    if (note.completed) TextDecoration.LineThrough else TextDecoration.None
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.labelMedium,
                    textDecoration = textDecoration,
                    color = color,
                    fontStyle = FontStyle.Italic
                )
                Text(text = note.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
