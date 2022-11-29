package box.example.showcase.ui.pages.color

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.R
import box.example.showcase.ui.Tab
import box.example.showcase.ui.theme.margin_half
import compose.icons.TablerIcons
import compose.icons.tablericons.Palette

object ColorThemeTab : Tab(
    TablerIcons.Palette,
    R.string.color_theme_route,
    R.string.color_theme_title
) {
    @Composable
    override fun Content() {
        val colorScheme: ColorScheme = MaterialTheme.colorScheme
        val colorsMap = mapOf(
            "Primary container" to Pair(
                colorScheme.primaryContainer,
                colorScheme.onPrimaryContainer
            ),
            "Secondary container" to Pair(
                colorScheme.secondaryContainer,
                colorScheme.onSecondaryContainer
            ),
            "Tertiary container" to Pair(
                colorScheme.tertiaryContainer,
                colorScheme.onTertiaryContainer
            ),
            "Error container" to Pair(colorScheme.errorContainer, colorScheme.onErrorContainer),
            "background" to Pair(colorScheme.background, colorScheme.onBackground),
            "surface" to Pair(colorScheme.surface, colorScheme.onSurface),
            "surfaceVariant" to Pair(colorScheme.surfaceVariant, colorScheme.onSurfaceVariant),
            "primary" to Pair(colorScheme.primary, colorScheme.onPrimary),
            "secondary" to Pair(colorScheme.secondary, colorScheme.onSecondary),
            "tertiary" to Pair(colorScheme.tertiary, colorScheme.onTertiary),
            "error" to Pair(colorScheme.error, colorScheme.onError),
            "inverseSurface" to Pair(colorScheme.inverseSurface, colorScheme.inverseOnSurface),
        )
        Column {
            Surface(color = MaterialTheme.colorScheme.background) {
                Column {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(top = margin_half)
                    ) {
                        items(items = colorsMap.keys.toList()) {
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(6.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = colorsMap[it]!!.first
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        it,
                                        color = colorsMap[it]!!.second,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }

                        }
                    }
                }
            }

        }
    }
}