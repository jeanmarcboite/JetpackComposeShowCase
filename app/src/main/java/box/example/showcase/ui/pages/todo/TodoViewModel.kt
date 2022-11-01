package box.example.showcase.ui.pages.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class TodoViewModel() : ViewModel() {
    val TAG = "boxx [firebase]"
    private val database: DatabaseReference

    //val tasks = mutableStateOf<List<Task>>(listOf())
    val tasks = mutableListOf<Task>()

    init {
        Log.d("boxx", "init todoViewModel")
        database = Firebase.database.reference
        Log.d(TAG, "database reference: ${database}")
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        database.child("log").setValue(currentDate)


        database.child("version").get().addOnSuccessListener {
            Log.i("boxx [firebase]", "Got value ${it.value}")
        }.addOnFailureListener {
            Log.e("boxx [firebase]", "Error getting data", it)
        }
    }
}