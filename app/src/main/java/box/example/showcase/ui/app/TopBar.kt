package box.example.showcase.ui.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.ApplicationViewModel
import box.example.showcase.ui.components.ProfileImage
import box.example.showcase.ui.models.NavViewModel
import compose.icons.TablerIcons
import compose.icons.tablericons.Moon
import compose.icons.tablericons.Sun
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
) {
    val appViewModel = hiltViewModel<ApplicationViewModel>()

    val navViewModel = hiltViewModel<NavViewModel>()
    val scope = rememberCoroutineScope()
    val expanded = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    navViewModel.onButtonClicked()
                }
            }) {
                Icon(
                    imageVector = navViewModel.selectedPage.value?.buttonIcon ?: Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        actions = {
            IconToggleButton(checked = appViewModel.applicationSettings.darkMode.value,
                onCheckedChange = {

                    appViewModel.setDarkMode(it)
                }) {
                Icon(
                    if (appViewModel.applicationSettings.darkMode.value) TablerIcons.Sun else TablerIcons.Moon,
                    contentDescription = "toggle dark mode",
                    tint = if (appViewModel.applicationSettings.darkMode.value) Color.Yellow else Color.Magenta,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)

                )
            }

            IconButton(onClick = {
                navViewModel.navigate("login")
            }) {
                ProfileImage(
                    Modifier
                        .clip(CircleShape)
                        .size(64.dp),
                    CircleShape
                )
            }

            Box(
                Modifier
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                IconButton(onClick = {
                    expanded.value = true
                }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "Localized description"
                    )
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                ) {
                    DropdownMenuItem(
                        { Text("First Item") },
                        onClick = {
                            expanded.value = false
                        })
                    DropdownMenuItem(
                        { Text("Second Item") },
                        onClick = {
                            expanded.value = false
                        })
                    Divider()
                    DropdownMenuItem(
                        { Text("Settings") },
                        onClick = {
                            expanded.value = false
                            navViewModel.navigate("settings")
                        })

                }
            }
        }
    )
}