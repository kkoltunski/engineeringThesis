package com.example.project.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project.R
import com.example.project.ui.screens.AscentScreen
import com.example.project.ui.screens.LoginScreen
import com.example.project.ui.screens.PersonalScreen
import com.example.project.ui.screens.RegionDetailedScreen
import com.example.project.ui.screens.RegisterScreen
import com.example.project.ui.screens.RockDetailedScreen
import com.example.project.ui.screens.RouteDetailedScreen
import com.example.project.ui.screens.SearchScreen

enum class Screen(@StringRes val title: Int) {
    LOGIN(title = R.string.login_screen_name),
    REGISTER(title = R.string.register_screen_name),
    PERSONAL(title = R.string.personal_screen_name),
    ASCENT(title = R.string.ascent_screen_name),
    SEARCH(title = R.string.search_screen_name),
    REGION_DETAILS(title = R.string.region_details_screen_name),
    ROCK_DETAILS(title = R.string.rock_details_screen_name),
    ROUTE_DETAILS(title = R.string.route_details_screen_name)

}

@Composable
fun Navigator(
    navController: NavHostController,
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
                    navController.navigate(Screen.PERSONAL.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.REGISTER.name) {
            RegisterScreen(
                proceedToLoginScreen = {
                    navController.navigate(Screen.LOGIN.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.PERSONAL.name) {
            PersonalScreen(
                proceedToAscentScreen = {
                    navController.navigate(Screen.ASCENT.name)
                }
            )
        }

        composable(route = Screen.ASCENT.name) {
            AscentScreen(
                proceedToPersonalScreen = {
                    navController.navigate(Screen.PERSONAL.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.SEARCH.name
        ) {
            SearchScreen(
                proceedToRegionDetailedScreen = {
                    navController.navigate(Screen.REGION_DETAILS.name)
                },
                proceedToRockDetailedScreen = {
                    navController.navigate(Screen.ROCK_DETAILS.name)
                },
                proceedToRouteDetailedScreen = {
                    navController.navigate(Screen.ROUTE_DETAILS.name)
                }
            )
        }

        composable(
            route = Screen.REGION_DETAILS.name
        ) {
            RegionDetailedScreen()
        }

        composable(
            route = Screen.ROCK_DETAILS.name
        ) {
            RockDetailedScreen()
        }

        composable(
            route = Screen.ROUTE_DETAILS.name
        ) {
            RouteDetailedScreen()
        }
    }
}