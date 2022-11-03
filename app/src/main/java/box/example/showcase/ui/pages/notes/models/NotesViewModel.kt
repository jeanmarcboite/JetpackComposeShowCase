package box.example.showcase.ui.pages.notes.models

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

class NotesViewModel() : ViewModel() {
    val TAG = "boxx [firebase]"
    private val database: DatabaseReference

    //val notes = mutableStateOf<List<Note>>(listOf())
    val notes = mutableStateListOf<Note>()

    init {
        Log.d("boxx", "init notesViewModel")
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

    private fun clear() {
        notes.clear()
    }

    fun add(root: String, note: Note) {
        database.child(root).child(note.id).setValue(note)
    }

    private fun read(root: String) {
        database.child(root).get().addOnSuccessListener {
            Log.i("boxx [firebase]", "Got value ${it.value}")
            clear()
            for (data in it.children) {
                data.getValue(Note::class.java)?.apply {
                    notes.add(this)
                }
            }
        }.addOnFailureListener {
            Log.e("boxx [firebase]", "Error getting data", it)
        }
    }

    fun setRoot(notesRoot: String) {
        read(notesRoot)
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                read(notesRoot)
            }

            override fun onChildChanged(
                dataSnapshot: DataSnapshot,
                previousChildName: String?
            ) {
                Log.d("boxxx", "onChildChanged: ${dataSnapshot.key}")

                read(notesRoot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                read(notesRoot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                read(notesRoot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addChildEventListener(childEventListener)
    }
}