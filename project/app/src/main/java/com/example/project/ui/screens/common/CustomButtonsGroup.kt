package com.example.project.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project.R
import com.example.project.ui.theme.ProjectTheme

@Composable
fun CustomButtonsGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelectedChanged: (String) -> Unit,
    buttonWidth: Int,
    buttonHeight: Int = 48,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        options.forEach {climbingType ->
            GroupButton(
                selectedOption = selectedOption,
                climbingType = climbingType,
                onClick = { onOptionSelectedChanged(it) },
                buttonWidth = buttonWidth,
                buttonHeight = buttonHeight,
            )
        }
    }
}

@Composable
fun GroupButton(
    selectedOption: String,
    climbingType: String,
    onClick: (String) -> Unit,
    buttonWidth: Int,
    buttonHeight: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {onClick(climbingType)},
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(selectedOption == climbingType) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.errorContainer
            }
        ),
        modifier = modifier
            .width(buttonWidth.dp)
            .height(buttonHeight.dp)
    ) {
        Text(
            text = climbingType,
            color = if(selectedOption == climbingType) {
                colorResource(id = R.color.white)
            } else {
                colorResource(id = R.color.black)
            }
        )
    }
}