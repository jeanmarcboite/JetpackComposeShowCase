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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import box.example.showcase.MainViewModel
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.ProfileImage
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Google
import compose.icons.fontawesomeicons.solid.Fire

class LoginPage(private val _mainViewModel: MainViewModel) :
    Page(
        _mainViewModel,
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
                Log.d("boxx", idToken.toString())
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
                    Log.d(TAG, "signInWithCredential:success")
                    mainViewModel.user.value = FirebaseAuth.getInstance().currentUser
                    //val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    //updateUI(null)
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SimpleOutlinedTextFieldSample()
                PasswordTextField()
            }

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
                    text = "Username: ${mainViewModel.user.value?.displayName}",
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
                Icon(
                    FontAwesomeIcons.Brands.Google, "",
                    modifier = Modifier
                        .padding(4.dp)
                        .size(32.dp)
                )

                Text(text = "Logout", modifier = Modifier.padding(16.dp))
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by remember {
        mutableStateOf("")
    }
    Surface(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("User") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Preview
@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleOutlinedTextFieldSample()
        PasswordTextField()
    }
}
