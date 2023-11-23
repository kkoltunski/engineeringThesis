package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.project.data.currentsession.ClimbingTypeData
import com.example.project.database.GradeMapper
import com.example.project.data.currentsession.StyleData
import com.example.project.ui.theme.ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GradeMapper.initialize()
                    StyleData.initialize()
                    ClimbingTypeData.initialize()
                    ClimbingApp()
                }
            }
        }
    }
}