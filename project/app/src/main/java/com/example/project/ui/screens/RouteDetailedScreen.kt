package com.example.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.data.CommentData
import com.example.project.data.PercentageStyles
import com.example.project.data.currentsession.CurrentSessionData
import com.example.project.ui.screens.common.AscentButton
import com.example.project.ui.screens.common.Badge
import com.example.project.ui.screens.common.ExpandableSection
import com.example.project.ui.screens.common.SpecialBadge
import com.example.project.ui.screens.common.TopologySection
import com.example.project.ui.screens.common.table.Table
import com.example.project.ui.screens.models.RouteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RouteDetailedScreen(
    routeViewModel: RouteViewModel = viewModel(),
    proceedToAscentScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val routeUiState by routeViewModel.harvesterUiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if(routeUiState.gatheringData) {
            LaunchedEffect(key1 = null) {
                withContext(Dispatchers.IO) {
                    routeViewModel.gatherData(CurrentSessionData.selectedDetailedId)
                }
            }
        } else {
            Text(
                text = "${routeViewModel.routeData.name}, " +
                        "${routeViewModel.routeData.rockName}, " +
                        "${routeViewModel.routeData.regionName}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(15.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 125.dp)
            ) {
                RouteBadgesGroup(
                    grade = routeViewModel.routeData.grade!!,
                    percentageStyles = routeViewModel.percentageStyles
                )

                Spacer(Modifier.height(30.dp))

                TopologySection(
                    topology = routeViewModel.topology.toList()
                )

                Spacer(Modifier.height(30.dp))

                CommentsSection(
                    comments = routeViewModel.comments
                )
            }
        }
    }

    AscentButton(
        onClick = {
            CurrentSessionData.routeNameForAscentView = routeViewModel.routeData.name
            proceedToAscentScreen()
        }
    )
}

@Composable
fun RouteBadgesGroup(
    grade: String,
    percentageStyles: PercentageStyles,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Badge(
            title = stringResource(id = R.string.grade),
            description = grade,
            size = 130,
            shapeColor = MaterialTheme.colorScheme.secondary,
            textColor = colorResource(id = R.color.white)
        )

        SpecialBadge(
            percentageStyles = percentageStyles,
            size = 100,
            shapeColor = MaterialTheme.colorScheme.primary,
            textColor = colorResource(id = R.color.white)
        )
    }
}

@Composable
fun CommentsSection(
    comments: List<CommentData>,
    modifier: Modifier = Modifier
) {
    ExpandableSection(
        modifier = Modifier,
        title = stringResource(id = R.string.comments),
        isExpandedByDefault = true
    ) {
        if(comments.isNotEmpty()) {
            Table(
                list = comments,
                modifier = Modifier
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.no_comments),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 5.dp)
                )
            }
        }
    }
}