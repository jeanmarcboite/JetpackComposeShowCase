package box.example.showcase.ui.models

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import box.example.showcase.ui.Page
import box.example.showcase.ui.navigation.navigateSingleTopTo

@OptIn(ExperimentalMaterial3Api::class)
class NavViewModel : ViewModel() {
    lateinit var pages: Map<String, Page>
    lateinit var drawerState: DrawerState
    lateinit var selectedItem: MutableState<Page?>
    lateinit var navController: NavHostController

    fun navigate(route: String) {
        navController.apply {
            try {
                navigate(route) {
                    //popUpTo = navController.graph.startDestination
                    launchSingleTop = true
                }
                navigateSingleTopTo(route)
                selectedItem.value =
                    pages[navController.currentDestination?.route]!!
            } catch (e: Exception) {
                Log.e("boxx", "Cannot navigate to ${route}: ${e.message}"/*, e*/)
            }
        }
    }

    fun popBackStack() {
        navController.popBackStack()
        Log.w(
            "boxxx",
            navController.currentDestination.toString()
        )
    }

    suspend fun buttonClick() {
        selectedItem.value?.onButtonClicked()
        selectedItem.value = pages[navController.currentDestination?.route]!!

    }
}