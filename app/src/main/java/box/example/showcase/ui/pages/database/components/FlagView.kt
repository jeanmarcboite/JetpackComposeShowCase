package box.example.showcase.ui.pages.database.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import box.example.showcase.ui.theme.touchpoint_lg
import coil.compose.rememberAsyncImagePainter
import com.jsramraj.flags.Flags

val LanguageMap = mapOf(
    "fra" to "fr",
    "eng" to "uk"
)

@Composable
fun String.LanguageView() {
    val countryCode = LanguageMap[this]
    if (countryCode != null) Image(
        painter = rememberAsyncImagePainter(Flags.forCountry(countryCode)),
        contentDescription = "flag",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(touchpoint_lg)
            .clip(CircleShape)
    )
}
