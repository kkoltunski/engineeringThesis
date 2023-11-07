package com.example.project.ui.screens.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomDialog(
    title: String,
    description: String,
    onAcknowledge: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = title) },
        text = { Text(text = description) },
        modifier = modifier,
        confirmButton = {
            TextButton(onClick = onAcknowledge) {
                Text(text = "Acknowledge")
            }
        },
    )
}