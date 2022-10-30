package box.example.showcase.ui.pages.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import box.example.showcase.MainViewModel
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.ProfileImage
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    lateinit var googleSignIn: GSignIn

    @Composable
    override fun Content(openDrawer: () -> Unit) {
        googleSignIn = GSignIn(LocalContext.current)
        Column(modifier = Modifier.fillMaxSize()) {
            if (mainViewModel.user.value == null)
                LoginScreen()
            else
                LogoutScreen()
        }
    }

    override suspend fun onButtonClicked() {
        mainViewModel.popBackStack()
    }


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
                    mainViewModel.user.value = FirebaseAuth.getInstance().currentUser
                    Log.d(TAG, "signInWithCredential:success: user " + mainViewModel.user.value)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    @Composable
    fun LoginScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailSignInButton()
            GoogleSignInButton()
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
                ProfileImage(mainViewModel, 64.dp)
                Text(
                    text = "Username: ${mainViewModel.user.value?.email}",
                    modifier = Modifier.padding(8.dp)
                )
            }
            // Rounded Button
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    googleSignIn.googleSignInClient.signOut()
                    mainViewModel.user.value = null
                    mainViewModel.popBackStack()
                },
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "Logout", modifier = Modifier.padding(16.dp))
            }

        }
    }


    @Composable
    fun EmailSignInButton() {
        var email by remember {
            mutableStateOf("jeanmarcboite@gmail.com")
        }
        var password by rememberSaveable { mutableStateOf("firebase") }
        val context = LocalContext.current
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            EmailTextField(email) {
                email = it
            }
            PasswordTextField(password) {
                password = it
            }
            Button(
                onClick = {
                    Log.d("boxx", "login with password")
                    firebaseAuthWithEmail(context, email, password)
                },
                modifier = Modifier.padding(1.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Sign in")
            }
        }
    }

    fun firebaseAuthWithEmail(context: Context, email: String, password: String) {
        val TAG = "boxxx firebaseAuthWithEmail"
        val auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    mainViewModel.user.value = FirebaseAuth.getInstance().currentUser
                    Log.d(
                        TAG,
                        "signInWithCredential:success: user " + mainViewModel.user.value?.email
                    )
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.d(TAG, "signInWithEmail:success for ${user?.email}")
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    //Toast.makeText(context, "Authentication failed.",
                    //   Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                    //checkForMultiFactorFailure(task.exception!!)
                }

            }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EmailTextField(email: String, onValueChange: (String) -> Unit) {
        Surface(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                value = email,
                onValueChange = onValueChange,
                label = { Text("User") }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PasswordTextField(password: String, onValueChange: (String) -> Unit) {

        TextField(
            value = password,
            onValueChange = onValueChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }

}