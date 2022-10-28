package box.example.showcase.ui.pages.login


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class GSignIn(context: Context) {
    private val client_id =
        "47788721477-3mvgqpgqe3tf3gj89ip2rvc4moht0k87.apps.googleusercontent.com"
    val client_id2 = "47788721477-l5fqema2vfc8unpn89gufdhptqtbbllk.apps.googleusercontent.com"
    val googleSignInOptions: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(client_id)
            .requestEmail()
            .build()

    val googleSignInClient: GoogleSignInClient =
        GoogleSignIn.getClient(context, googleSignInOptions)
}

class GoogleSignInContract(private val googleSignInClient: GoogleSignInClient) :
    ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return googleSignInClient.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        return try {
            val account = task.getResult(ApiException::class.java)!!
            Log.i("boxx", "Google sign in ${account.displayName} ${account.id} ${account.idToken}")
            account.idToken!!
        } catch (e: Exception) {
            // TODO: display message
            Log.e("boxx", "Google sign in failed ${e.message}", e)
            null
        }
    }
}