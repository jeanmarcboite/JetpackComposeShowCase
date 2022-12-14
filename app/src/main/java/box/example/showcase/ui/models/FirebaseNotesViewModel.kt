package box.example.showcase.ui.models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import box.example.showcase.applib.notes.models.Note
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import java.text.DateFormat.getDateTimeInstance
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object FirebaseNotesModule {
    val verbose = false
    private val TAG = "boxx [firebase]"
    private val notes: SnapshotStateList<Note> = mutableStateListOf()

    @Provides
    @Named("firebase_notes")
    fun notes() = notes

    init {
        val database: DatabaseReference = Firebase.database.reference
        if (verbose)
            Log.v(TAG, "database reference: $database")
        val sdf = getDateTimeInstance() //SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        database.child("log").setValue(currentDate)


        database.child("version").get().addOnSuccessListener {
            if (verbose)
                Log.v(TAG, "Database version ${it.value}")
        }.addOnFailureListener {
            Log.e(TAG, "Error getting data", it)
        }
    }
}

@HiltViewModel
class FirebaseNotesViewModel @Inject constructor(
    @Named("firebase_notes") val notes: SnapshotStateList<Note>
) : ViewModel() {


    private fun clear() {
        notes.clear()
    }

    fun add(root: String, note: Note) {
        Firebase.database.reference.child(root).child(note.id).setValue(note)
    }

    private fun read(root: String) {
        val TAG = "boxxxx [firebase:read]"
        Firebase.database.reference.child(root).get().addOnSuccessListener {
            Log.v(TAG, "Got value ${it.value}")
            clear()
            for (data in it.children) {
                data.getValue(Note::class.java)?.apply {
                    notes.add(this)
                }
            }
        }.addOnFailureListener {
            Log.e(TAG, "Error getting data", it)
        }
    }

    fun setRoot(notesRoot: String) {
        val TAG = "boxxxx [firebase:setRoot]"
        read(notesRoot)
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                read(notesRoot)
            }

            override fun onChildChanged(
                dataSnapshot: DataSnapshot,
                previousChildName: String?
            ) {
                Log.v(TAG, "onChildChanged: ${dataSnapshot.key}")

                read(notesRoot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                read(notesRoot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                read(notesRoot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented: onCancelled")
            }
        }
        Firebase.database.reference.addChildEventListener(childEventListener)
    }
}