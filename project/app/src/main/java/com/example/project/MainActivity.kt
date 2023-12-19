package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.project.data.currentsession.ClimbingTypeData
import com.example.project.data.currentsession.StyleData
import com.example.project.database.GradeMapper
import com.example.project.network.ConnectivityObserver
import com.example.project.network.NetworkConnectivityObserver
import com.example.project.ui.screens.ConnectionIssueScreen
import com.example.project.ui.theme.ProjectTheme

class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            ProjectTheme {
                val connectionStatus by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.Available
                )

                if(connectionStatus != ConnectivityObserver.Status.Available) {
                    ConnectionIssueScreen()
                } else {
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
}