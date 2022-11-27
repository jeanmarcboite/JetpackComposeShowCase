/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package box.example.showcase.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import box.example.showcase.ui.navigation.Tab

private val TabHeight = 56.dp

@Composable
fun TabBar(
    tabs: List<Tab>,
    currentTab: Tab,
    onScreenSelected: (Tab) -> Unit,
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
                    onClick = { onScreenSelected(tab) },
                    selected = currentTab == tab
                )
            }
        }
    }
}
