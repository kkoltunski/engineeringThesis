package com.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.project.ui.navigation.Navigator
import com.example.project.ui.theme.ProjectTheme

@Composable
fun ClimbingApp(
) {
    Navigator()
}

@Preview(showBackground = true)
@Composable
fun ClimbingAppPreview(){
    ProjectTheme {
        ClimbingApp()
    }
}