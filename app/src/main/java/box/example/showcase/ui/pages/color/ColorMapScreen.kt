package box.example.showcase.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import box.example.showcase.R
import box.example.showcase.ui.navigation.Screen
import box.example.showcase.ui.pages.color.COLOR_MAP
import box.example.showcase.ui.theme.margin_half
import box.example.showcase.ui.theme.margin_standard
import box.example.showcase.ui.theme.touchpoint_lg
import coil.compose.rememberAsyncImagePainter
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cocktail

// gh repo clone mwolfson/jetpackTemplate

object ColorMapScreen : Screen {
    override val icon = FontAwesomeIcons.Solid.Cocktail
    override val route = R.string.color_page_route
    override val title = R.string.color_page_title
    override val content: @Composable () -> Unit = {
        Log.d("boxx", "ColorMapScreen")
        ColorListBody(
            onItemClicked = {
                // navController.navigate("${AppScreens.Detail.route}${it.name}")
            },
            COLOR_MAP.values.toList(),
        )
    }

}

data class ColorItem(val name: String, val iconUrl: String, val iconUrlLg: String)

/**
 * The Main List Tab
 */
@Composable
fun ColorListBody(
    onItemClicked: (ColorItem) -> Unit,
    colorItems: List<ColorItem>?,
) {
    if (!colorItems.isNullOrEmpty()) {
        val userItems = remember { mutableStateListOf<ColorItem>() }
        userItems.swapList(sortName(false, colorItems))

        Surface(color = MaterialTheme.colorScheme.background) {
            Column {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(top = margin_half)
                ) {
                    items(items = userItems) { pres ->
                        ColorListItem(
                            colorItem = pres,
                            onItemClicked = { onItemClicked(it) },
                            modifier = Modifier.fillParentMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

/**
 * Simple sorting method to return a list sorted by name for display
 */
fun sortName(descending: Boolean, listIn: List<ColorItem>): List<ColorItem> {
    return if (descending) {
        listIn.sortedByDescending { it.name }
    } else {
        listIn.sortedBy { it.name }
    }
}

@OptIn(coil.annotation.ExperimentalCoilApi::class)
@Composable
fun ColorListItem(
    colorItem: ColorItem,
    onItemClicked: (ColorItem) -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .clickable { onItemClicked(colorItem) }
            .fillMaxWidth()
            .padding(horizontal = margin_standard, vertical = margin_half),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = colorItem.iconUrl
            ),
            contentDescription = colorItem.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(touchpoint_lg)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(margin_standard))
        Text(
            text = colorItem.name,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    Divider(color = MaterialTheme.colorScheme.onSurface)
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}