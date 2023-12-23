package com.example.project.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.data.currentsession.CurrentSessionData
import com.example.project.data.currentsession.CurrentSessionData.routeNameForAscentView
import com.example.project.data.currentsession.StyleData
import com.example.project.ui.screens.common.CustomDialog
import com.example.project.ui.screens.models.AscentViewModel
import com.example.project.ui.states.AscentViewStatus
import com.example.project.ui.theme.ProjectTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AscentScreen(
    proceedToPersonalScreen: () -> Unit,
    ascentViewModel: AscentViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    ascentViewModel.tryToFindRouteOnEntry(CurrentSessionData.routeNameForAscentView)

    val loginUiState by ascentViewModel.uiState.collectAsState()
    val calendar = Calendar.getInstance()
    val year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val month by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val day by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    if(ascentViewModel.userTypes.date.isEmpty()) ascentViewModel.userTypes.date = "$year-${month+1}-$day"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        //dataPicker
        val context = LocalContext.current

        val dataPickerDialog = DatePickerDialog(
            context,
            {d, year, month, day ->
                val month = month +1
                ascentViewModel.userTypes.date = "$year-$month-$day"
            }, year, month, day,
        )
        // end of dataPicker

        val localFocusManager = LocalFocusManager.current

        OutlinedTextField(
            value = ascentViewModel.userTypes.searchedRoute,
            label = { Text(stringResource(id = R.string.route_name)) },
            singleLine = true,
            onValueChange = { ascentViewModel.userTypes.updateSearchedRoute(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    localFocusManager.clearFocus()
                    ascentViewModel.findRoute()
                }
            ),
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
        ) {
            Text(ascentViewModel.userTypes.date)

            Spacer(modifier = Modifier.width(70.dp))

            Button(
                onClick = { dataPickerDialog.show() },
                enabled = loginUiState.ascentViewStatus == AscentViewStatus.ROUTE_FOUND
            ){
                Text(stringResource(id = R.string.select_date))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.select_style),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 70.dp)
        )
        RadioButtonGroup(
            radioOptions = StyleData.styles,
            selectedOption = ascentViewModel.userTypes.style,
            onOptionSelected = { ascentViewModel.userTypes.updateStyle(it) },
            enabled = loginUiState.ascentViewStatus == AscentViewStatus.ROUTE_FOUND,
            modifier = Modifier
        )

        AscentViewStatusDialog(
            ascentViewStatus = loginUiState.ascentViewStatus,
            onAcknowledge = {
                if(loginUiState.ascentViewStatus != AscentViewStatus.ASCENT_SAVED)
                    ascentViewModel.resetView()
                else
                    proceedToPersonalScreen()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = ascentViewModel.userTypes.comment,
            label = { Text(text = stringResource(id = R.string.comment)) },
            enabled = loginUiState.ascentViewStatus == AscentViewStatus.ROUTE_FOUND,
            onValueChange = { ascentViewModel.userTypes.updateComment(it) },
            maxLines = 3,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 60.dp)
                .defaultMinSize(minHeight = 40.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = { ascentViewModel.saveAscent() },
            enabled = loginUiState.ascentViewStatus == AscentViewStatus.ROUTE_FOUND,
        ){
            Text(stringResource(id = R.string.save))
        }
    }
}

@Composable
fun RadioButtonGroup(
    radioOptions: List<String>,
    selectedOption: String,
    enabled: Boolean,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier
) {
    radioOptions.forEach { text ->
        Row(
            modifier = Modifier
                .selectable(
                    selected = (text == selectedOption),
                    enabled = enabled,
                    onClick = {
                        onOptionSelected(text)
                    }
                )
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(start = 145.dp)
            )

            RadioButton(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) },
                enabled = enabled
            )
        }
    }
}

@Composable
fun AscentViewStatusDialog(
    ascentViewStatus: AscentViewStatus,
    onAcknowledge: () -> Unit,
    modifier: Modifier = Modifier
){
    if ((ascentViewStatus == AscentViewStatus.ROUTE_NOT_FOUND) or
        (ascentViewStatus == AscentViewStatus.ASCENT_SAVED))
    {
        var dialogTitle = ""
        var dialogDescription = ""

        when(ascentViewStatus) {
            AscentViewStatus.ROUTE_NOT_FOUND -> {
                dialogTitle = stringResource(id = R.string.route_not_found)
                dialogDescription = stringResource(id = R.string.route_not_found_desc)
            }

            AscentViewStatus.ASCENT_SAVED -> {
                dialogTitle = stringResource(id = R.string.ascent_saved)
                dialogDescription = stringResource(id = R.string.ascent_saved_desc)
            }

            else -> {}
        }

        CustomDialog(
            title = dialogTitle,
            description = dialogDescription,
            onAcknowledge = onAcknowledge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AscentScreenPreview() {
    ProjectTheme {
        AscentScreen({})
    }
}