package box.example.showcase.ui.pages.login


import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import box.example.showcase.R
import box.example.showcase.ui.models.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun GoogleSignInScreen(authViewModel: AuthViewModel) {
    val context = LocalContext.current
    // R.string.default_web_client_id is created automatically as per google-services.json,
    // though sometimes the IDE might not recognize it
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
    val authResultLauncher = authViewModel.rememberFirebaseAuthLauncher()

    Button(onClick = {
        val signInIntent = googleSignInClient.signInIntent
        authResultLauncher.launch(signInIntent)
        authViewModel.googleSignInClient.value = googleSignInClient
    }) {
        Text("Sign in via Google")
    }
}

@Composable
fun AuthViewModel.rememberFirebaseAuthLauncher(): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val TAG = "boxx [AuthViewModel]"
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        val data: Intent? = activityResult.data
        val note = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = note.getResult(ApiException::class.java)!!
            Log.i(TAG, "firebaseAuthWithGoogle:" + account.email)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Google sign in failed: " + e.message)
        }
    }
}