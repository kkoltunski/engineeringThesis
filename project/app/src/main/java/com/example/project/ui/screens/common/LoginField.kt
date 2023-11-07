package com.example.project.ui.screens.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginField(
    userLogin: String,
    onUpdateUserLogin: (String) -> Unit,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        value = userLogin,
        label = { Text(stringResource(id = R.string.login)) },
        singleLine = true,
        onValueChange = onUpdateUserLogin,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { localFocusManager.clearFocus() }
        ),
        interactionSource = interactionSource,
        modifier = modifier
    )
}