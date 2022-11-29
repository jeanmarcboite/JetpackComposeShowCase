package box.example.showcase.applib.ui.components

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.PlusCircle

@Composable
fun MultiFloatingActionButton(
    expanded: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    icon: ImageVector = FontAwesomeIcons.Solid.PlusCircle
) {
    val transition = updateTransition(targetState = expanded, label = "")
    val rotation: Float by transition.animateFloat(label = "rotation") {
        if (it.value) 45f else 0f
    }
    FloatingActionButton(
        onClick = {
            //expanded.value = !expanded.value
            transition.currentState.value = !transition.currentState.value
            Log.d("boxx [FAB]", "clicked [expanded = ${transition.currentState.value}]")
        },
        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
    ) {
        Icon(
            icon, "FAB",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = modifier
                .rotate(rotation)
                .size(32.dp)
        )
    }
}
