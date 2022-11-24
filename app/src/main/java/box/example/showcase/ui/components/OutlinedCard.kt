package box.example.showcase.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = CardDefaults.outlinedShape,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    elevation: CardElevation = CardDefaults.outlinedCardElevation(),
    border: BorderStroke = CardDefaults.outlinedCardBorder(),
    label: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    var labelSize by remember {
        mutableStateOf(0f)
    }
    //val brush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer)
    Column(modifier = modifier
        .drawWithContent {
            drawContent()
            clipRect { // Not needed if you do not care about painting half stroke outside
                val strokeWidth = Stroke.DefaultMiter
                val y = size.height // - strokeWidth
                // if the whole line should be inside component

                //Log.i(Tag.Temp, "ClipRect ${size.width} ${size.height}")

                drawLine(
                    brush = border.brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset(x = 0f, y = 12 * density),
                    end = Offset(x = 8 * density, y = 12 * density)
                )

                drawLine(
                    brush = border.brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset(x = labelSize + 24 * density, y = 12 * density),
                    end = Offset(x = size.width, y = 12 * density)
                )

                drawLine(
                    brush = border.brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset.Zero.copy(y = 12 * density),
                    end = Offset.Zero.copy(y = size.height)
                )

                drawLine(
                    brush = border.brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset.Zero.copy(y = y),
                    end = Offset(x = size.width, y = y)
                )

                drawLine(
                    brush = border.brush,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset(x = size.width, y = 12 * density),
                    end = Offset(x = size.width, y = size.height)
                )


            }
        }
    ) {
        Card(
            onClick = onClick,
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation
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

}