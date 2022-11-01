package box.example.showcase.ui.pages.todo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.DateFormat.getDateTimeInstance
import java.util.*

class TodoViewModel() : ViewModel() {
    val TAG = "boxx [firebase]"
    private val database: DatabaseReference

    //val tasks = mutableStateOf<List<Task>>(listOf())
    val tasks = mutableStateListOf<Task>()

    init {
        Log.d("boxx", "init todoViewModel")
        database = Firebase.database.reference
        Log.d(TAG, "database reference: ${database}")
        val sdf = getDateTimeInstance() //SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        database.child("log").setValue(currentDate)


        database.child("version").get().addOnSuccessListener {
            Log.i("boxx [firebase]", "Got value ${it.value}")
        }.addOnFailureListener {
            Log.e("boxx [firebase]", "Error getting data", it)
        }
    }

    fun add(root: String, task: Task) {
        tasks.add(task)
        database.child(root).child(task.id).setValue(task)
    }

    fun read(root: String) {
        database.child(root).get().addOnSuccessListener {
            Log.i("boxx [firebase]", "Got value ${it.value}")
            tasks.clear()
            for (data in it.children) {
                data.getValue(Task::class.java)?.apply {
                    tasks.add(this)
                }
            }
            Log.i("boxx [firebase]", "Tasks $tasks")

        }.addOnFailureListener {
            Log.e("boxx [firebase]", "Error getting data", it)
        }
    }
}