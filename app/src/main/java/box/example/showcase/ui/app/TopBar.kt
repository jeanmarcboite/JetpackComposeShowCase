package box.example.showcase.ui.app

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import box.example.showcase.MainViewModel
import box.example.showcase.ui.components.ProfileImage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Moon
import compose.icons.fontawesomeicons.solid.Sun

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    mainViewModel: MainViewModel,
    buttonIcon: ImageVector = Icons.Default.Menu,
    onButtonClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
        ),
        navigationIcon = {
            IconButton(onClick = onButtonClicked) {
                Icon(
                    imageVector = buttonIcon,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        actions = {
            IconToggleButton(checked = mainViewModel.darkMode.value,
                onCheckedChange = {

                    mainViewModel.setDarkMode(it)
                }) {
                Icon(
                    if (mainViewModel.darkMode.value) FontAwesomeIcons.Solid.Sun else FontAwesomeIcons.Solid.Moon,
                    contentDescription = "toggle dark mode",
                    tint = if (mainViewModel.darkMode.value) Color.Yellow else Color.Magenta,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)

                )
            }

            IconButton(onClick = {
                mainViewModel.navigate("login")
            }) {
                ProfileImage(mainViewModel, 32.dp)
            }
        }
    )
}