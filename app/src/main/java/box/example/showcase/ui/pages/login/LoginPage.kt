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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.R
import box.example.showcase.ui.Page
import box.example.showcase.ui.components.ProfileImage
import box.example.showcase.ui.models.AuthState
import box.example.showcase.ui.models.AuthViewModel
import box.example.showcase.ui.models.NavViewModel
import com.google.firebase.auth.FirebaseAuth
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Fire

class LoginPage :
    Page(
        FontAwesomeIcons.Solid.Fire,
        R.string.login_page_route,
        R.string.login_page_title,
        Icons.Default.ArrowBack
    ) {
    override fun showInDrawer() = false
    //lateinit var googleSignIn: GSignIn

    @Composable
    override fun Content() {
        val authViewModel = hiltViewModel<AuthViewModel>()
        Column(modifier = Modifier.fillMaxSize()) {
            if (authViewModel.state.value == AuthState.LoggedIn)
                LogoutScreen()
            else
                LoginScreen()
        }
    }

    override suspend fun onButtonClicked(navViewModel: NavViewModel) {
        navViewModel.popBackStack()
    }

    @Composable
    fun LoginScreen() {
        val authViewModel = hiltViewModel<AuthViewModel>()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailSignInScreen(authViewModel)
            GoogleSignInScreen(authViewModel)
        }
    }

    @Composable
    fun LogoutScreen() {
        val authViewModel = hiltViewModel<AuthViewModel>()
        val navViewModel = hiltViewModel<NavViewModel>()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                ProfileImage(
                    Modifier
                        .size(128.dp),
                    RectangleShape
                )
                Text(
                    text = "Username: ${authViewModel.user.value?.email}",
                    modifier = Modifier.padding(8.dp)
                )
            }
            // Rounded Button
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    authViewModel.setUser(null)
                    navViewModel.popBackStack()
                },
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "Logout", modifier = Modifier.padding(16.dp))
            }

        }
    }
}