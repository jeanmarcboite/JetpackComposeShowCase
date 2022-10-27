package box.example.showcase.ui.screens.bored

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import box.example.showcase.ui.Route
import box.example.showcase.ui.components.LabeledField
import kotlinx.coroutines.launch

object Bored : Route {
    override val icon = Icons.Filled.Person
    override val route = "bored"
    override val screen: @Composable () -> Unit = { BoredScreen() }
}

@Composable
fun BoredScreen() {
    val scope = rememberCoroutineScope()
    val activity: MutableState<Activity?> = remember { mutableStateOf<Activity?>(null) }
    LaunchedEffect(true) {
        activity.value = nextActivity()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ActivityCard(activity) {
            scope.launch {
                activity.value = nextActivity()
            }
        }
    }
}

@Composable
fun ActivityCard(activity: MutableState<Activity?>, onClick: () -> Unit) {
    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { }
        ) {
            //Text(activity.value?.activity.toString())
            activity.value?.apply {
                LabeledField("activity", this.activity)
                LabeledField("type", type)
                LabeledField("participants", participants.toString())
                LabeledField("price", price.toString())
                LabeledField("key", key)
                LabeledField("accessibility", accessibility.toString())
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            // on below line we are
            // adding on click for our fab
            onClick = onClick,
            // on below line we are
            // specifying shape for our button
            //shape = RectangleShape,
            shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
            // on below line we are
            // adding background color.
            containerColor = Color.Blue,
            // on below line we are
            // adding content color.
            contentColor = Color.White
        ) {
            // on below line we are
            // adding icon for fab.
            Icon(Icons.Filled.KeyboardArrowRight, "")
        }
    }
}
