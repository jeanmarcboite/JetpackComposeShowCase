package box.example.showcase.ui.pages.todo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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

    fun clear() {
        tasks.clear()
    }

    fun add(root: String, task: Task) {
        database.child(root).child(task.id).setValue(task)
    }

    private fun read(root: String) {
        database.child(root).get().addOnSuccessListener {
            Log.i("boxx [firebase]", "Got value ${it.value}")
            clear()
            for (data in it.children) {
                data.getValue(Task::class.java)?.apply {
                    tasks.add(this)
                }
            }
        }.addOnFailureListener {
            Log.e("boxx [firebase]", "Error getting data", it)
        }
    }

    fun setRoot(todoRoot: String) {
        read(todoRoot)
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                read(todoRoot)
            }

            override fun onChildChanged(
                dataSnapshot: DataSnapshot,
                previousChildName: String?
            ) {
                Log.d("boxxx", "onChildChanged: ${dataSnapshot.key}")

                read(todoRoot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                read(todoRoot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                read(todoRoot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addChildEventListener(childEventListener)
    }
}