package box.example.showcase.ui.pages.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import box.example.showcase.ui.models.AuthState
import box.example.showcase.ui.models.AuthViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ExclamationCircle

@Composable
fun TrailingIcon(error: Boolean) {
    if (error)
        Icon(
            FontAwesomeIcons.Solid.ExclamationCircle,
            "error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(32.dp)
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailSignInScreen(authViewModel: AuthViewModel) {
    val context = LocalContext.current

    var email by rememberSaveable {
        mutableStateOf("jeanmarcboite@gmail.com")
    }
    var password by rememberSaveable { mutableStateOf("firebase") }

    val color = if (authViewModel.state.value == AuthState.LoginError) Red else Green
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = color,
        unfocusedBorderColor = color
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            value = email,
            label = { Text("User") },
            colors = colors,
            trailingIcon = {
                TrailingIcon(error = authViewModel.state.value == AuthState.LoginError)
            },
            onValueChange = {
                email = it
            })

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            value = password,
            label = { Text("Password") },
            colors = colors,
            trailingIcon = {
                TrailingIcon(error = authViewModel.state.value == AuthState.LoginError)
            },
            onValueChange = {
                password = it
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = {
                authViewModel.firebaseAuthWithEmail(context, email, password)
            },
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Sign in")
        }
    }
}
