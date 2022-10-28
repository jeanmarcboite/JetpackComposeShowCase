package box.example.showcase.ui.pages

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
import box.example.showcase.ui.Page
import box.example.showcase.ui.theme.margin_half
import box.example.showcase.ui.theme.margin_standard
import box.example.showcase.ui.theme.touchpoint_lg
import coil.compose.rememberAsyncImagePainter
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cocktail

val COLOR_MAP: Map<String, ColorItem> = mapOf(
    "amber" to ColorItem(
        "Amber",
        "https://placehold.co/64x64/ffc107/000000.png",
        "https://placehold.co/128x256/ffc107/000000.png"
    ),
    "blue" to ColorItem(
        "Blue",
        "https://placehold.co/64x64/4e6cef/ffffff.png",
        "https://placehold.co/128x256/4e6cef/ffffff.png"
    ),
    "bluegrey" to ColorItem(
        "BlueGrey",
        "https://placehold.co/64x64/607d8b/ffffff.png",
        "https://placehold.co/128x256/607d8b/ffffff.png"
    ),
    "brown" to ColorItem(
        "Brown",
        "https://placehold.co/64x64/795548/ffffff.png",
        "https://placehold.co/128x256/795548/ffffff.png"
    ),
    "cyan" to ColorItem(
        "Cyan",
        "https://placehold.co/64x64/00bcd4/ffffff.png",
        "https://placehold.co/128x256/00bcd4/ffffff.png"
    ),
    "deeppurple" to ColorItem(
        "DeepPurple",
        "https://placehold.co/64x64/673ab7/ffffff.png",
        "https://placehold.co/128x256/673ab7/ffffff.png"
    ),
    "deeporange" to ColorItem(
        "DeepOrange",
        "https://placehold.co/64x64/ff5722/ffffff.png",
        "https://placehold.co/128x256/ff5722/ffffff.png"
    ),
    "green" to ColorItem(
        "Green",
        "https://placehold.co/64x64/259b24/ffffff.png",
        "https://placehold.co/128x256/259b24/ffffff.png"
    ),
    "grey" to ColorItem(
        "Grey",
        "https://placehold.co/64x64/9e9e9e/000000.png",
        "https://placehold.co/128x256/9e9e9e/000000.png"
    ),
    "indigo" to ColorItem(
        "Indigo",
        "https://placehold.co/64x64/3f51b5/ffffff.png",
        "https://placehold.co/128x256/3f51b5/ffffff.png"
    ),
    "lightblue" to ColorItem(
        "LightBlue",
        "https://placehold.co/64x64/03a9f4/ffffff.png",
        "https://placehold.co/128x256/03a9f4/ffffff.png"
    ),
    "lightgreen" to ColorItem(
        "LightGreen",
        "https://placehold.co/64x64/8bc34a/000000.png",
        "https://placehold.co/128x256/8bc34a/000000.png"
    ),
    "lime" to ColorItem(
        "Lime",
        "https://placehold.co/64x64/cddc39/000000.png",
        "https://placehold.co/128x256/cddc39/000000.png"
    ),
    "orange" to ColorItem(
        "Orange",
        "https://placehold.co/64x64/ff9800/000000.png",
        "https://placehold.co/128x256/ff9800/000000.png"
    ),
    "pink" to ColorItem(
        "Pink",
        "https://placehold.co/64x64/e91e63/ffffff.png",
        "https://placehold.co/128x256/e91e63/ffffff.png"
    ),
    "purple" to ColorItem(
        "Purple",
        "https://placehold.co/64x64/9c27b0/ffffff.png",
        "https://placehold.co/128x256/9c27b0/ffffff.png"
    ),
    "red" to ColorItem(
        "Red",
        "https://placehold.co/64x64/e51c23/ffffff.png",
        "https://placehold.co/128x256/e51c23/ffffff.png"
    ),
    "teal" to ColorItem(
        "Teal",
        "https://placehold.co/64x64/009688/ffffff.png",
        "https://placehold.co/128x256/009688/ffffff.png"
    ),
    "yellow" to ColorItem(
        "Yellow",
        "https://placehold.co/64x64/ffeb3b/000000.png",
        "https://placehold.co/128x256/ffeb3b/000000.png"
    ),
)

// gh repo clone mwolfson/jetpackTemplate
class ColorPage() :
    Page(
        FontAwesomeIcons.Solid.Cocktail,
        R.string.color_page_route,
        R.string.color_page_title
    ) {
    @Composable
    override fun Content(openDrawer: () -> Unit) {
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
 * The Main List Screen
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
            style = MaterialTheme.typography.displayMedium,
        )
    }
    Divider(color = MaterialTheme.colorScheme.onSurface)
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}