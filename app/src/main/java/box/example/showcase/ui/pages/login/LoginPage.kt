package box.example.showcase.ui.pages.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.ProfileImage
import box.example.showcase.ui.models.AuthState
import com.google.firebase.auth.FirebaseAuth
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Fire

class LoginPage() :
    Page(
        FontAwesomeIcons.Solid.Fire,
        R.string.login_page_route,
        R.string.login_page_title,
        Icons.Default.ArrowBack
    ) {
    override fun showInDrawer() = false
    //lateinit var googleSignIn: GSignIn

    @Composable
    override fun Content(openDrawer: () -> Unit) {
        //googleSignIn = GSignIn(LocalContext.current)
        Column(modifier = Modifier.fillMaxSize()) {
            if (mainViewModel.authViewModel.state.value == AuthState.LoggedIn)
                LogoutScreen()
            else
                LoginScreen()
        }
    }

    override suspend fun onButtonClicked() {
        mainViewModel.popBackStack()
    }

    /*
        @Composable
        fun GoogleSignInButton() {
            val context = LocalContext.current
            val googleLoginLauncher =
                rememberLauncherForActivityResult(GoogleSignInContract(googleSignIn.googleSignInClient)) { idToken ->
                    Log.d("boxx [idToken]", idToken.toString())
                    idToken?.let {
                        firebaseAuthWithGoogle(context, it, mainViewModel)
                    }
                }

            Button(
                onClick = {
                    Log.d("boxx", "login")
                },
                modifier = Modifier.padding(1.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                AndroidView({ context: Context ->
                    val button = SignInButton(context)
                    button.setOnClickListener {
                        googleLoginLauncher.launch()
                    }
                    button
                })
            }
        }

        private fun firebaseAuthWithGoogle(
            context: Context,
            idToken: String,
            mainViewModel: MainViewModel
        ) {
            val TAG = "boxx firebaseAuthWithGoogle"
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(context as Activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        mainViewModel.authViewModel.setUser(FirebaseAuth.getInstance().currentUser)
                        Log.d(
                            TAG,
                            "signInWithCredential:success: user " + mainViewModel.authViewModel.user.value
                        )
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                    }
                }
        }
    */
    @Composable
    fun LoginScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailSignInScreen(mainViewModel.authViewModel)
            GoogleSignInScreen(mainViewModel.authViewModel)
        }
    }

    @Composable
    fun LogoutScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                ProfileImage(mainViewModel.authViewModel, 64.dp)
                Text(
                    text = "Username: ${mainViewModel.authViewModel.user.value?.email}",
                    modifier = Modifier.padding(8.dp)
                )
            }
            // Rounded Button
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    //googleSignIn.googleSignInClient.signOut()
                    mainViewModel.authViewModel.setUser(null)
                    mainViewModel.popBackStack()
                },
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "Logout", modifier = Modifier.padding(16.dp))
            }

        }
    }
}