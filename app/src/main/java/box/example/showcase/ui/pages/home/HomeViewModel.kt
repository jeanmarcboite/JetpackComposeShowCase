package box.example.showcase.ui.pages.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    val id: Int = ++objectId

    companion object {
        var objectId = 0
    }
}


@HiltViewModel
class HiltHomeViewModel @Inject constructor(
    val uuid: UUID
) : ViewModel() {
    val id: Int = ++objectId
    override fun toString(): String {
        return "HiltHomeViewModel(uuid=${uuid})"
    }

    companion object {
        var objectId = 0
    }
}