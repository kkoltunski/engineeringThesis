package com.example.project.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.R
import com.example.project.ui.screens.LoginScreen
import com.example.project.ui.screens.PersonalScreen
import com.example.project.ui.screens.RegisterScreen

enum class Screen(@StringRes val title: Int) {
    LOGIN(title = R.string.login_screen_name),
    REGISTER(title = R.string.register_screen_name),
    PERSONAL(title = R.string.personal_screen_name)
}

@Composable
fun Navigator(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LOGIN.name,
        modifier = modifier
    ) {
        composable(route = Screen.LOGIN.name) {
            LoginScreen(
                onSignUpTextClicked = {
                    navController.navigate(Screen.REGISTER.name)
                },
                proceedToPersonalScreen = {
                    navController.navigate(Screen.PERSONAL.name)
                }
            )
        }

        composable(route = Screen.REGISTER.name) {
            RegisterScreen(
                proceedToLoginScreen = {
                    navController.navigate(Screen.LOGIN.name)
                }
            )
        }

        composable(route = Screen.PERSONAL.name) {
            PersonalScreen()
        }
    }
}