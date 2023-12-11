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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.ui.screens.chart.AscentChart
import com.example.project.ui.screens.common.AscentButton
import com.example.project.ui.screens.common.Badge
import com.example.project.ui.screens.common.CustomButtonsGroup
import com.example.project.ui.screens.common.SpecialBadge
import com.example.project.ui.screens.common.table.Table
import com.example.project.ui.screens.models.BadgesData
import com.example.project.ui.screens.models.PersonalViewModel
import com.example.project.ui.theme.ProjectTheme

@Composable
fun PersonalScreen(
    proceedToAscentScreen: () -> Unit,
    personalViewModel: PersonalViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val personalUiState by personalViewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomButtonsGroup(
            options = listOf(
                stringResource(id = R.string.climbing_type_sport),
                stringResource(id = R.string.climbing_type_trad),
                stringResource(id = R.string.climbing_type_boulders)
            ),
            selectedOption = personalViewModel.selectedClimbingType,
            onOptionSelectedChanged = { personalViewModel.changeSelectedClimbingType(it) },
            buttonWidth = 120,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.TopCenter)
                .padding(top = 30.dp)
                .selectableGroup()
        )

        if(personalViewModel.isClimbingTypeDataPresent) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .padding(top = 85.dp)
            ) {
                PersonalBadgesGroup(
                    badgesData = personalViewModel.badgesData,
                )

                Spacer(modifier = Modifier.height(50.dp))

                CustomButtonsGroup(
                    options = listOf(
                        stringResource(id = R.string.time_period_all_time),
                        stringResource(id = R.string.time_period_3_months),
                        stringResource(id = R.string.time_period_6_months),
                        stringResource(id = R.string.time_period_12_months)
                    ),
                    selectedOption = personalViewModel.selectedTimeInterval,
                    onOptionSelectedChanged = { personalViewModel.changeSelectedTimeInterval(it) },
                    buttonWidth = 95,
                    buttonHeight = 34,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                )

                Spacer(modifier = Modifier.height(15.dp))

                if(personalViewModel.chartData.isNotEmpty())
                {
                    AscentChart(
                        chartData = personalViewModel.chartData
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Table(
                        list = personalViewModel.ascents,
                        modifier = Modifier
                    )

                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_data_in_time_interval),
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

    AscentButton(
        onClick = { proceedToAscentScreen() }
    )
}

@Composable
fun PersonalBadgesGroup(
    badgesData: BadgesData,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Badge(
            title = stringResource(id = R.string.total_ascents),
            description = badgesData.totalAscents.toString(),
            size = 100,
            shapeColor = MaterialTheme.colorScheme.primary,
            textColor = colorResource(id = R.color.white)
        )

        Badge(
            title = stringResource(id = R.string.avg_grade),
            description = badgesData.avgGrade,
            size = 130,
            shapeColor = MaterialTheme.colorScheme.secondary,
            textColor = colorResource(id = R.color.white),
            modifier = Modifier.padding(top = 40.dp)
        )

        SpecialBadge(
            percentageStyles = badgesData.percentageStyles,
            size = 100,
            shapeColor = MaterialTheme.colorScheme.primary,
            textColor = colorResource(id = R.color.white)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BadgesGroupPreview() {
    PersonalBadgesGroup(BadgesData())
}

@Preview(showBackground = true)
@Composable
fun PersonalScreenPreview() {
    ProjectTheme {
        PersonalScreen({})
    }
}