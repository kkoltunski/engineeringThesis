package com.example.project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project.R
import com.example.project.data.currentsession.UserData
import com.example.project.ui.theme.ProjectTheme

@Composable
fun PersonalScreen(
    proceedToAscentScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "${UserData.userId}",
            modifier = Modifier
        )

        Button(
            onClick = { proceedToAscentScreen() },
            modifier = Modifier
                .padding(end = 30.dp, bottom = 30.dp)
                .align(Alignment.BottomEnd)
            ) {
            Text(stringResource(id = R.string.add_ascent))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalScreenPreview() {
    ProjectTheme {
        PersonalScreen({})
    }
}