package box.example.showcase.ui.pages.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

open class HomeViewModel : ViewModel() {
    val id: Int = ++objectId
    val uuidVM = UUID.randomUUID()
    override fun toString(): String {
        return "HomeViewModel(uuid=${uuidVM})"
    }

    companion object {
        var objectId = 0
    }
}

@HiltViewModel
class HiltHomeViewModel @Inject constructor(
    val uuid: UUID
) : HomeViewModel() {
    override fun toString(): String {
        return "HiltHomeViewModel(uuid=${uuid} uuidVM=${uuidVM})"
    }
}