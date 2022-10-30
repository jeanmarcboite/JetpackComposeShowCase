package box.example.showcase.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import box.example.showcase.ui.models.AuthViewModel
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage


@Composable
fun ProfileImage(viewModel: AuthViewModel, imgSize: Dp) {
    val user = viewModel.user.value
    val imageUri = user?.photoUrl?.toString()?.toUri()

    Card(
        shape = CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .size(imgSize),
        elevation = cardElevation(4.dp)
    ) {
        if (imageUri != null) {
            CoilImage(
                data = imageUri,
                contentDescription = "User image",
                requestBuilder = {
                    transformations(CircleCropTransformation())
                }
            )
        } else {
            Icon(Icons.Filled.Person, contentDescription = "no user")
        }
    }
}
