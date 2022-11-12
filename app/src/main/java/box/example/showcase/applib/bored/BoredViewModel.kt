package box.example.showcase.applib.bored

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// @HiltViewModel will make models to be
// created using Hilt's model factory
// @Inject annotation used to inject all
// dependencies to view model class
@HiltViewModel
class BoredViewModel @Inject constructor(
    private val id: Int
) : ViewModel() {
    init {
        Log.d("boxxx [Bored]", "creating viewModel ${++object_id} ${id}")
    }

    fun msg(): String {
        return "BORED $id {$object_id}"
    }

    companion object stamp {
        var object_id = 0
    }
}
