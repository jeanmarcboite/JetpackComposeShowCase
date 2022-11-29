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
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.applib.bored.BoredViewModel
import box.example.showcase.applib.bored.MovieViewModel
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.LabeledField
import compose.icons.TablerIcons
import compose.icons.tablericons.Cloud
import kotlinx.coroutines.launch

class BoredPage :
    Page(
        TablerIcons.Cloud,
        R.string.bored_page_route,
        R.string.bored_page_title
    ) {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val boredViewModel: BoredViewModel = hiltViewModel()
        val movieViewModel: MovieViewModel = hiltViewModel()
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
                    WithBored()
                    Text(
                        "Content: ${boredViewModel.msg()}",
                        modifier = Modifier.padding(start = 32.dp)
                    )
                    Text("Content movie: ${movieViewModel}")
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
fun WithBored(boredViewModel: BoredViewModel = hiltViewModel()) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    Text("WithBored: ${boredViewModel.msg()}")
    Text("WithBored movie: ${movieViewModel}")
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
