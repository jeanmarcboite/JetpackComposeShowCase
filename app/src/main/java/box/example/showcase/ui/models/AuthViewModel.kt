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

class AuthViewModel() : ViewModel() {
    val state: MutableState<AuthState> = mutableStateOf(AuthState.NotLoggedIn)
    val user: MutableState<FirebaseUser?> = mutableStateOf(null)

    var googleSignInClient: GoogleSignInClient? = null

    init {
        Log.d("boxx [Firebase.auth]", Firebase.auth.toString())
        Log.d("boxx [Firebase.auth]", Firebase.auth.currentUser.toString())
        user.value = FirebaseAuth.getInstance().currentUser
        if (user.value != null)
            state.value = AuthState.LoggedIn
        //FirebaseApp.initializeApp(application.applicationContext)
        Log.d("boxx", "Firebase user: " + FirebaseAuth.getInstance().currentUser.toString())
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
        val TAG = "boxxx firebaseAuthWithEmail"
        val auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    setUser(FirebaseAuth.getInstance().currentUser)
                    Log.d(
                        TAG,
                        "signInWithCredential:success: user " + user.value?.email
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    state.value = AuthState.LoginError
                    //Toast.makeText(context, "Authentication failed.",
                    //   Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                    //checkForMultiFactorFailure(task.exception!!)
                }

            }

    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val TAG = "boxxx firebaseAuthWithGoogle"
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    setUser(FirebaseAuth.getInstance().currentUser)
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(
                        TAG,
                        "signInWithCredential:success: user " + user.value?.email
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("boxxx", task.exception.toString())
                    state.value = AuthState.LoginError
                }
            }
    }

}