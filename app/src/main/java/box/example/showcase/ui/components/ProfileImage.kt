package box.example.showcase.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import box.example.showcase.R
import box.example.showcase.ui.models.AuthViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun ProfileImage(
    modifier: Modifier = Modifier
        .clip(CircleShape)
        .padding(8.dp),
    shape: Shape = CircleShape,
    viewModel: AuthViewModel
) {
    val user = viewModel.user.value
    val imageUri = user?.photoUrl?.toString()?.toUri()

    Card(
        shape = shape,
        modifier = modifier,
        elevation = cardElevation(4.dp)
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.profile_image_description),
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        } else {
            Icon(Icons.Filled.Person, contentDescription = "no user")
        }
    }
}
