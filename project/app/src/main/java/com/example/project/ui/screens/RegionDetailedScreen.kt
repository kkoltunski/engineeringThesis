package com.example.project.ui.screens

import RegionViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.data.GradeData
import com.example.project.data.RockData
import com.example.project.data.currentsession.CurrentSessionData
import com.example.project.ui.screens.common.ExpandableSection
import com.example.project.ui.screens.common.TopologySection
import com.example.project.ui.screens.common.table.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RegionDetailedScreen(
    regionViewModel: RegionViewModel = viewModel(),
    proceedToRockDetailedScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val regionUiState by regionViewModel.harvesterUiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if(regionUiState.gatheringData) {
            LaunchedEffect(key1 = null) {
                withContext(Dispatchers.IO) {
                    regionViewModel.gatherData(CurrentSessionData.selectedDetailedId)
                }
            }
        } else {
            Column(
                modifier = modifier
                    .align(Alignment.TopStart)
                    .fillMaxSize()
            ) {
                TopologySection(
                    topology = regionViewModel.topology.toList()
                )

                GradesSection(
                    grades = regionViewModel.grades
                )

                RocksSection(
                    rocks = regionViewModel.rocks,
                    onItemClicked = {
                        CurrentSessionData.selectedDetailedId = it.toString()
                        proceedToRockDetailedScreen()
                    },
                )
            }
        }
    }
}

@Composable
fun GradesSection(
    grades: List<GradeData>
) {
    ExpandableSection(
        modifier = Modifier,
        title = stringResource(id = R.string.grades),
        isExpandedByDefault = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Table(
                list = grades,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun RocksSection(
    rocks: List<RockData>,
    onItemClicked: (Int) -> Unit
) {
    ExpandableSection(
        modifier = Modifier,
        title = stringResource(id = R.string.rocks),
        isExpandedByDefault = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Table(
                list = rocks,
                onItemClicked = onItemClicked,
                modifier = Modifier
            )
        }
    }
}