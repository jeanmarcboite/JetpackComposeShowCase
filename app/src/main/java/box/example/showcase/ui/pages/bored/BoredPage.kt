package box.example.showcase.ui.pages.bored

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import box.example.showcase.R
import box.example.showcase.applib.bored.BoredViewModel
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.LabeledField
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Beer
import kotlinx.coroutines.launch

class BoredPage() :
    Page(
        FontAwesomeIcons.Solid.Beer,
        R.string.bored_page_route,
        R.string.bored_page_title
    ) {
    @Composable
    fun msg(boredViewModel: BoredViewModel = viewModel()): String {
        return boredViewModel.msg()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(openDrawer: () -> Unit) {
        val scope = rememberCoroutineScope()
        val activity: MutableState<Activity?> = remember { mutableStateOf<Activity?>(null) }
        LaunchedEffect(true) {
            activity.value = nextActivity()
        }
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    Text("[${msg()}]")
                }
            },
            content = {
                ActivityCard(activity) {
                    scope.launch {
                        activity.value = nextActivity()
                    }
                }
            }
        )
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
