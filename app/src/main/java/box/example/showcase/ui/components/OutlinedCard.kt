package box.example.showcase.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedCard(
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)
) {
    var labelSize by remember {
        mutableStateOf(0f)
    }

    val brush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer)
    Column(modifier = modifier
        .padding(4.dp)
        .drawWithContent {
            drawContent()
            clipRect { // Not needed if you do not care about painting half stroke outside
                val strokeWidth = Stroke.DefaultMiter
                val y = size.height // - strokeWidth
                // if the whole line should be inside component

                //Log.i(Tag.Temp, "ClipRect ${size.width} ${size.height}")

                drawLine(
                    brush = brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset(x = 0f, y = 12 * density),
                    end = Offset(x = 8 * density, y = 12 * density)
                )

                drawLine(
                    brush = brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset(x = labelSize + 24 * density, y = 12 * density),
                    end = Offset(x = size.width, y = 12 * density)
                )

                drawLine(
                    brush = brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset.Zero.copy(y = 12 * density),
                    end = Offset.Zero.copy(y = size.height)
                )

                drawLine(
                    brush = brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset.Zero.copy(y = y),
                    end = Offset(x = size.width, y = y)
                )

                drawLine(
                    brush = brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset(x = size.width, y = 12 * density),
                    end = Offset(x = size.width, y = size.height)
                )


            }
        }
    ) {
        Row(modifier = Modifier
            .padding(start = 16.dp)
            .onGloballyPositioned {
                labelSize = it.size.width.toFloat()
            }) {
            if (label != null) {
                label()
            }
        }
        content()
    }

}