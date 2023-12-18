package com.example.project.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.data.currentsession.CurrentSessionData
import com.example.project.ui.screens.common.ExpandableSection
import com.example.project.ui.screens.common.GatheringDataDialog
import com.example.project.ui.screens.common.table.Table
import com.example.project.ui.screens.models.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel(),
    proceedToRegionDetailedScreen: () -> Unit,
    proceedToRockDetailedScreen: () -> Unit,
    proceedToRouteDetailedScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchUiState by searchViewModel.harvesterUiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if(CurrentSessionData.searchedPhrase.isNotEmpty()) {
            if(searchUiState.gatheringData) {
                LaunchedEffect(key1 = null) {
                    withContext(Dispatchers.IO) {
                        searchViewModel.gatherData(CurrentSessionData.searchedPhrase)
                    }
                }
            } else {
                if(searchViewModel.regions.isNotEmpty() or
                    searchViewModel.rocks.isNotEmpty() or
                    searchViewModel.routes.isNotEmpty()
                ) {
                    Column(
                        modifier = modifier
                            .align(Alignment.TopStart)
                            .fillMaxSize()
                    ) {
                        if(searchViewModel.regions.isNotEmpty()) {
                            ExpandableSection(
                                modifier = Modifier,
                                title = stringResource(id = R.string.regions),
                                isExpandedByDefault = true
                            ) {
                                Table(
                                    list = searchViewModel.regions,
                                    onItemClicked = {
                                        CurrentSessionData.selectedDetailedId = it.toString()
                                        proceedToRegionDetailedScreen()
                                    },
                                    modifier = Modifier
                                )
                            }
                        }

                        if(searchViewModel.rocks.isNotEmpty()) {
                            ExpandableSection(
                                modifier = Modifier,
                                title = stringResource(id = R.string.rocks),
                                isExpandedByDefault = true
                            ) {
                                Table(
                                    list = searchViewModel.rocks,
                                    onItemClicked = {
                                        CurrentSessionData.selectedDetailedId = it.toString()
                                        proceedToRockDetailedScreen()
                                    },
                                    modifier = Modifier
                                )
                            }
                        }

                        if(searchViewModel.routes.isNotEmpty()) {
                            ExpandableSection(
                                modifier = Modifier,
                                title = stringResource(id = R.string.routes),
                                isExpandedByDefault = true
                            ) {
                                Table(
                                    list = searchViewModel.routes,
                                    onItemClicked = {
                                        CurrentSessionData.selectedDetailedId = it.toString()
                                        proceedToRouteDetailedScreen()
                                    },
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.no_data),
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }

            GatheringDataDialog(
                isDataGathering = searchUiState.gatheringData
            )
        } else {
            Text(
                text = stringResource(id = R.string.no_data),
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}