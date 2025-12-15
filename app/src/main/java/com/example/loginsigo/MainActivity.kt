package com.example.loginsigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.loginsigo.data.model.UserResponse
import com.example.loginsigo.di.LoginViewModelFactory
import com.example.loginsigo.ui.login.LoginViewModel
import com.example.loginsigo.ui.login.LoginScreen
import com.example.loginsigo.ui.login.WelcomeScreen // <--- Importamos WelcomeScreen
import com.example.loginsigo.ui.login.HistoryScreen
import com.example.loginsigo.ui.login.ProfileScreen
import com.google.gson.Gson

object Routes {
    const val LOGIN = "login_screen"
    const val WELCOME = "welcome_screen/{userJson}" // Usamos tu nombre "WELCOME"
    const val HISTORY = "history_screen"
    const val PROFILE = "profile_screen/{userJson}"

    fun welcome(userJson: String) = "welcome_screen/$userJson"
    fun profile(userJson: String) = "profile_screen/$userJson"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.loginsigo.ui.theme.LoginSIGOTheme {
                AppScreenEntry()
            }
        }
    }
}

@Composable
fun AppScreenEntry() {
    val appContext = LocalContext.current.applicationContext
    val application = appContext as SigoLoginApplication
    val authRepository = application.container.authRepository
    val factory = LoginViewModelFactory(authRepository)
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        // 1. PANTALLA LOGIN
        composable(Routes.LOGIN) {
            val viewModel: LoginViewModel = viewModel(factory = factory)
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { user ->
                    val userJson = Gson().toJson(user)
                    // Navegamos a WELCOME
                    navController.navigate(Routes.welcome(userJson)) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // 2. PANTALLA DE BIENVENIDA (MENU PRINCIPAL)
        composable(
            route = Routes.WELCOME,
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson")
            val user = Gson().fromJson(userJson, UserResponse::class.java)

            if (user != null) {
                WelcomeScreen(  // <--- Llamamos a WelcomeScreen
                    user = user,
                    onHistoryClick = { navController.navigate(Routes.HISTORY) },
                    onProfileClick = { navController.navigate(Routes.profile(userJson!!)) },
                    onLogoutClick = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }

        // 3. PANTALLA HISTORIAL
        composable(Routes.HISTORY) {
            HistoryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // 4. PANTALLA PERFIL
        composable(
            route = Routes.PROFILE,
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson")
            val user = Gson().fromJson(userJson, UserResponse::class.java)

            if (user != null) {
                ProfileScreen(
                    user = user,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}