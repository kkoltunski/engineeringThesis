package com.example.project

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.project.data.currentsession.CurrentSessionData
import com.example.project.ui.navigation.Navigator
import com.example.project.ui.navigation.Screen
import com.example.project.ui.screens.common.searchbar.MainAppBar
import com.example.project.ui.screens.common.searchbar.SearchBarState
import com.example.project.ui.screens.models.SearchBarViewModel
import com.example.project.ui.theme.ProjectTheme

@Composable
fun ClimbingApp(
    navController: NavHostController = rememberNavController(),
    searchBarViewModel: SearchBarViewModel = viewModel()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.LOGIN.name
    )

    val searchBarState by searchBarViewModel.searchBarState
    val searchBarText by searchBarViewModel.searchTextState

    Scaffold(
        topBar = {
            if((currentScreen.name != Screen.LOGIN.name) and
                (currentScreen.name != Screen.REGISTER.name))
            {
                MainAppBar(
                    currentScreen = currentScreen,
                    searchBarState = searchBarState,
                    searchTextState = searchBarText,
                    onTextChange = { searchBarViewModel.updateSearchBarText(newText = it) },
                    onCloseClicked = {
                        searchBarViewModel.updateSearchBarText("")
                        searchBarViewModel.updateSearchBarState(SearchBarState.CLOSED)
                    },
                    onSearchClicked = {
                        CurrentSessionData.searchedPhrase = it
                        navController.navigate(Screen.SEARCH.name)
                                      },
                    onSearchTriggered = { searchBarViewModel.updateSearchBarState(SearchBarState.OPENED) },
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
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