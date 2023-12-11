package com.example.project.ui.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.project.R

@Composable
fun AscentButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = onClick,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
            modifier = modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(bottom = 30.dp, end = 30.dp)
        ) {
            Text(
                text = stringResource(R.string.add_ascent),
            )
        }
    }
}
