package com.example.project.ui.screens

import RockViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.extensions.isNotNull
import com.example.project.R
import com.example.project.data.currentsession.CurrentSessionData
import com.example.project.ui.screens.common.ExpandableSection
import com.example.project.ui.screens.common.table.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RockDetailedScreen(
    rockViewModel: RockViewModel = viewModel(),
    proceedToRouteDetailedScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rockUiState by rockViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (rockUiState.gatheringData) {
            LaunchedEffect(key1 = null) {
                withContext(Dispatchers.IO) {
                    rockViewModel.gatherData(CurrentSessionData.selectedDetailedId)
                }
            }
        } else {
            if(rockViewModel.bitmapImage.isNotNull()) {
                ExpandableSection(
                    modifier = Modifier,
                    title = rockViewModel.rockName,
                    isExpandedByDefault = true
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        ){
//                            Image(
//                                bitmap = rockViewModel.bitmapImage!!.asImageBitmap(),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(600.dp)
//                                    .padding(start = 15.dp, end = 15.dp)
//                            )

                            Image(
                                bitmap = rockViewModel.bitmapImage!!.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 15.dp, end = 15.dp)
                            )

                            ExpandableSection(
                                modifier = Modifier,
                                title = stringResource(id = R.string.routes),
                                isExpandedByDefault = true,
                                headerStyle = MaterialTheme.typography.headlineSmall
                            ) {
                                Table(
                                    list = rockViewModel.routes,
                                    onItemClicked = {
                                        CurrentSessionData.selectedDetailedId = it.toString()
                                        proceedToRouteDetailedScreen()
                                    }
                                )
                            }
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
    }
}