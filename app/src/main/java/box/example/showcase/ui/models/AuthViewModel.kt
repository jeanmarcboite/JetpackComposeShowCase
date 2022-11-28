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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

enum class AuthState {
    NotLoggedIn,
    LoginError,
    LoggedIn
}

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    private val verbose = false
    private val TAG = "boxx [AuthViewModel]"
    val _state: MutableState<AuthState> = mutableStateOf(AuthState.NotLoggedIn)
    val _user: MutableState<FirebaseUser?> = mutableStateOf(null)
    val _googleSignInClient: MutableState<GoogleSignInClient?> = mutableStateOf(null)

    init {
        _googleSignInClient.value = null
        _user.value = FirebaseAuth.getInstance().currentUser
        if (_user.value != null)
            _state.value = AuthState.LoggedIn

        if (verbose)
            Log.v(TAG, "Firebase user: " + FirebaseAuth.getInstance().currentUser?.displayName)
    }

    @Provides
    fun state() = _state

    @Provides
    fun user() = _user

    @Provides
    fun googleSignInClient() = _googleSignInClient
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    val state: MutableState<AuthState>,
    val user: MutableState<FirebaseUser?>,
    val googleSignInClient: MutableState<GoogleSignInClient?>
) : ViewModel() {
    fun setUser(firebaseUser: FirebaseUser?) {
        user.value = firebaseUser
        state.value = if (user.value != null) AuthState.LoggedIn else AuthState.NotLoggedIn

        if (user.value != null) {
            state.value = AuthState.LoggedIn
        } else {
            state.value = AuthState.NotLoggedIn
            googleSignInClient.value?.signOut()
            googleSignInClient.value = null
        }
    }

    fun firebaseAuthWithEmail(context: Context, email: String, password: String) {
        val TAG = "boxxx [firebaseAuthWithEmail]"
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
                }

            }

    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val TAG = "boxx [firebaseAuthWithGoogle]"
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setUser(FirebaseAuth.getInstance().currentUser)
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(
                        TAG,
                        "signInWithCredential:success: user " + user.value?.email
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, task.exception.toString())
                    state.value = AuthState.LoginError
                }
            }
    }

}
