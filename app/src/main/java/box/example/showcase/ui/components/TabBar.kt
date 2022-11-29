package box.example.showcase.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import box.example.showcase.ui.Tab

private val TabHeight = 56.dp

@Composable
fun TabBar(
    tabs: List<Tab>,
    currentTab: Tab,
    onTabSelected: (Tab) -> Unit,
) {
    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup()) {
            tabs.forEach { tab: Tab ->
                IconAction(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxHeight(),
                    text = stringResource(id = tab.title),
                    icon = tab.icon,
                    onClick = { onTabSelected(tab) },
                    selected = currentTab == tab
                )
            }
        }
    }
}
