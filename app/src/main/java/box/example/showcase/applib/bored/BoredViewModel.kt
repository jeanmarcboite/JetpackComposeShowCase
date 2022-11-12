package box.example.showcase.applib.bored

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

// @HiltViewModel will make models to be
// created using Hilt's model factory
// @Inject annotation used to inject all
// dependencies to view model class
@HiltViewModel
class BoredViewModel @Inject constructor(
    private val id: Int,
    private val idd: Double,
    @Named("other") private val other_id: Int
) : ViewModel() {
    init {
        Log.d("boxxx [Bored]", "creating viewModel ${++object_id} ${id}")
    }

    fun msg(): String {
        return "BORED $id $other_id $idd {$object_id}"
    }

    companion object stamp {
        var object_id = 0
    }
}
