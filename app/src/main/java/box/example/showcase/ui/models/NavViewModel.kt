package box.example.showcase.ui.models

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import box.example.showcase.ui.Page
import box.example.showcase.ui.navigation.navigateSingleTopTo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NavModule {
    // we need a HashMap here, Map is only an interface
    lateinit var pages: HashMap<String, Page>

    @OptIn(ExperimentalMaterial3Api::class)
    lateinit var drawerState: DrawerState
    lateinit var selectedPage: MutableState<Page?>
    lateinit var navController: NavHostController

    @Provides
    @Named("pages")
    fun appPages() = pages

    @OptIn(ExperimentalMaterial3Api::class)
    @Provides
    fun appDrawerState() = drawerState

    @Provides
    fun appSelectedPage() = selectedPage

    @Provides
    fun appNavController() = navController
}

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class NavViewModel @Inject constructor(
    @Named("pages") val pages: HashMap<String, Page>,
    val drawerState: DrawerState,
    val selectedPage: MutableState<Page?>,
    val navController: NavHostController
) : ViewModel() {
    //    val pages: Map<String, Page> = mapOf()
    fun navigate(route: String) {
        navController.apply {
            try {
                navigate(route) {
                    //popUpTo = navController.graph.startDestination
                    launchSingleTop = true
                }
                navigateSingleTopTo(route)
                selectedPage.value =
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

    suspend fun onButtonClicked() {
        selectedPage.value?.onButtonClicked(this)
        selectedPage.value = pages[navController.currentDestination?.route]!!
    }
}