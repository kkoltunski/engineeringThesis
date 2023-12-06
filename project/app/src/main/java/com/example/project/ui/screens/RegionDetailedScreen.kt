package com.example.project.ui.screens

import RegionViewModel
import androidx.compose.foundation.layout.Box
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.data.currentsession.CurrentSessionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RegionDetailedScreen(
    regionViewModel: RegionViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val regionUiState by regionViewModel.uiState.collectAsState()

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
            Text(
                text = regionViewModel.phrase,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}