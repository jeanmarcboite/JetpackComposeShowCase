package box.example.showcase.applib.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

const val ANIMATION_DURATION = 500

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun SwipeableCard(
    offset: MutableState<Float>,
    swiped: MutableState<Boolean>,
    height: Dp,
    swipeThreshold: Float,
    swipeOffset: Float,
    backgroundColor: Color,
    cardContent: @Composable () -> Unit
) {
    val threshold = swipeOffset * swipeThreshold.coerceIn(0f, 1f)
    val modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp, 8.dp)
        .height(height)
        .offset { IntOffset(offset.value.roundToInt(), 0) }
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    Log.v("boxx", "onDragEnd")
                    if (offset.value > 0) {
                        if (swiped.value) {
                            if (offset.value < swipeOffset - threshold) {
                                offset.value = 0f
                                swiped.value = false
                            }
                        } else {
                            if (offset.value > threshold) {
                                offset.value = swipeOffset
                                swiped.value = true
                            }
                        }
                    } else {
                        if (swiped.value) {
                            if (offset.value > -swipeOffset + threshold) {
                                offset.value = 0f
                                swiped.value = false
                            }
                        } else {
                            if (offset.value < -threshold) {
                                offset.value = -swipeOffset
                                swiped.value = true
                            }
                        }
                    }
                }
            ) { change, dragAmount ->
                change.consumePositionChange()
                offset.value += dragAmount
                offset.value.coerceIn(-swipeOffset, swipeOffset)
            }
        }

    Card(
        modifier,
        shape = RoundedCornerShape(3.dp),
    colors = CardDefaults.cardColors(containerColor = backgroundColor),
    elevation = CardDefaults.cardElevation(if (swiped.value) 40.dp else 2.dp),
    ) {
        Row(horizontalArrangement = if (swiped.value) Arrangement.End else Arrangement.Start) {
            cardContent()
        }
    }
}