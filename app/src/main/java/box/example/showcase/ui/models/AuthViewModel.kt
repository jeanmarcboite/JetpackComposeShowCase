package box.example.showcase.ui.models

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

enum class AuthState {
    NotLoggedIn,
    LoginError,
    LoggedIn
}

class AuthViewModel : ViewModel() {
    private val verbose = false
    private val TAG = "boxx [AuthViewModel]"
    val state: MutableState<AuthState> = mutableStateOf(AuthState.NotLoggedIn)
    val user: MutableState<FirebaseUser?> = mutableStateOf(null)

    var googleSignInClient: GoogleSignInClient? = null

    init {
        user.value = FirebaseAuth.getInstance().currentUser
        if (user.value != null)
            state.value = AuthState.LoggedIn

        if (verbose)
            Log.v(TAG, "Firebase user: " + FirebaseAuth.getInstance().currentUser?.displayName)
    }

    fun setUser(firebaseUser: FirebaseUser?) {
        user.value = firebaseUser
        state.value = if (user.value != null) AuthState.LoggedIn else AuthState.NotLoggedIn

        if (user.value != null) {
            state.value = AuthState.LoggedIn
        } else {
            state.value = AuthState.NotLoggedIn
            googleSignInClient?.signOut()
            googleSignInClient = null
        }
    }

    fun firebaseAuthWithEmail(context: Context, email: String, password: String) {
        val TAG = "boxxx [firebaseAuthWithEmail]"
        val auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity) { note ->
                if (note.isSuccessful) {
                    setUser(FirebaseAuth.getInstance().currentUser)
                    Log.d(
                        TAG,
                        "signInWithCredential:success: user " + user.value?.email
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", note.exception)
                    state.value = AuthState.LoginError
                    //Toast.makeText(context, "Authentication failed.",
                    //   Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                    //checkForMultiFactorFailure(note.exception!!)
                }

            }

    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val TAG = "boxx [firebaseAuthWithGoogle]"
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener { note ->
                if (note.isSuccessful) {
                    setUser(FirebaseAuth.getInstance().currentUser)
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(
                        TAG,
                        "signInWithCredential:success: user " + user.value?.email
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, note.exception.toString())
                    state.value = AuthState.LoginError
                }
            }
    }

}
