package com.example.project

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.navigation.Navigator
import com.example.project.ui.navigation.Screen
import com.example.project.ui.screens.common.CustomAppBar
import com.example.project.ui.theme.ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClimbingApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.LOGIN.name
    )

    Scaffold(
        topBar = {
            if((currentScreen.name != Screen.LOGIN.name) and
                (currentScreen.name != Screen.REGISTER.name))
            {
                CustomAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    ) {innerPadding ->
        Navigator(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClimbingAppPreview(){
    ProjectTheme {
        ClimbingApp()
    }
}