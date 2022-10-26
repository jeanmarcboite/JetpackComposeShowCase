package box.example.showcase.ui.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import box.example.showcase.R
import box.example.showcase.ui.Route

object About : Route {
    override val icon = Icons.Filled.Info
    override val route = "about"
    override val screen: @Composable () -> Unit = { AboutScreen() }
}


@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "About Screen" }
    )
    {
            Text("Application " + stringResource(id = R.string.app_name))
    }
}