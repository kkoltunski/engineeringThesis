package com.example.project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.ui.screens.common.CustomDialog
import com.example.project.ui.screens.common.LoginField
import com.example.project.ui.screens.common.PasswordField
import com.example.project.ui.screens.models.RegisterViewModel
import com.example.project.ui.states.RegisterAttemptStatus
import com.example.project.ui.theme.ProjectTheme

@Composable
fun RegisterScreen(
    onSignInTextClicked: (Int) -> Unit,
    registerViewModel: RegisterViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val registerUiState by registerViewModel.uiState.collectAsState()

    FailedRegisterAttemptDialog(
        registerAttemptStatus = registerUiState.registerAttemptStatus,
        onAcknowledge = { registerViewModel.resetView() }
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_design_app_icon),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(20.dp))

        val loginInteractionsource = remember {
            MutableInteractionSource()
        }
        val passwordInteractionsource = remember {
            MutableInteractionSource()
        }
        val passwordRepeatInteractionsource = remember {
            MutableInteractionSource()
        }
        val emailInteractionsource = remember {
            MutableInteractionSource()
        }

        GuideText(
            loginInteractionsource = loginInteractionsource,
            passwordInteractionsource =  passwordInteractionsource,
            passwordRepeatInteractionsource = passwordRepeatInteractionsource,
            emailInteractionsource = emailInteractionsource
        )

        Spacer(modifier = Modifier.height(20.dp))

        LoginField(
            userLogin = registerViewModel.userTypes.login,
            interactionSource = loginInteractionsource,
            onUpdateUserLogin = { registerViewModel.userTypes.updateLogin(it) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PasswordField(
            label = stringResource(id = R.string.password),
            userPassword = registerViewModel.userTypes.password,
            interactionSource = passwordInteractionsource,
            onUpdateUserPassword ={ registerViewModel.userTypes.updatePassword(it) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        PasswordField(
            label = stringResource(id = R.string.password_repeat),
            userPassword = registerViewModel.userTypes.passwordRepeat,
            interactionSource = passwordRepeatInteractionsource,
            onUpdateUserPassword ={ registerViewModel.userTypes.updatePasswordRepeat(it) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        EmailField(
            userEmail = registerViewModel.userTypes.email,
            interactionSource = emailInteractionsource,
            onUpdateUserEmail ={ registerViewModel.userTypes.updateEmail(it) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { registerViewModel.checkRegisterAttempt() }
        ) {
            Text(stringResource(id = R.string.register))
        }

        Spacer(modifier = Modifier.height(10.dp))

        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.sign_in_invitation)),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            onClick = { onSignInTextClicked(it) }
        )
    }
}

@Composable
fun GuideText(
    loginInteractionsource: MutableInteractionSource,
    passwordInteractionsource: MutableInteractionSource,
    passwordRepeatInteractionsource: MutableInteractionSource,
    emailInteractionsource: MutableInteractionSource
) {
    var guideText = remember {
        mutableStateOf("")
    }
    if(loginInteractionsource.collectIsFocusedAsState().value) {
        guideText.value = stringResource(id = R.string.login_requirements)
    } else if (passwordInteractionsource.collectIsFocusedAsState().value) {
        guideText.value = stringResource(id = R.string.password_requirements)
    } else if (passwordRepeatInteractionsource.collectIsFocusedAsState().value) {
        guideText.value = stringResource(id = R.string.password_repeat_requirements)
    } else if (emailInteractionsource.collectIsFocusedAsState().value) {
        guideText.value = stringResource(id = R.string.email_requirements)
    } else {
        guideText.value = ""
    }

    Text(
        text = guideText.value,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .alpha(
                if(loginInteractionsource.collectIsFocusedAsState().value or
                    passwordInteractionsource.collectIsFocusedAsState().value or
                    passwordRepeatInteractionsource.collectIsFocusedAsState().value or
                    emailInteractionsource.collectIsFocusedAsState().value)
                {
                    0.5f
                } else {
                    0.0f
                }
            )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    userEmail: String,
    onUpdateUserEmail: (String) -> Unit,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        value = userEmail,
        label = { Text(stringResource(id = R.string.email)) },
        singleLine = true,
        onValueChange = onUpdateUserEmail,
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

@Composable
fun FailedRegisterAttemptDialog(
    registerAttemptStatus: RegisterAttemptStatus,
    onAcknowledge: () -> Unit,
    modifier: Modifier = Modifier
){
    if (registerAttemptStatus != RegisterAttemptStatus.OK) {
        var dialogTitle = ""
        var dialogDescription = ""

        when(registerAttemptStatus) {
            RegisterAttemptStatus.BAD_CREDENTIALS -> {
                dialogTitle = stringResource(id = R.string.bad_credentials)
                dialogDescription = stringResource(id = R.string.bad_credentials_desc)
            }

            RegisterAttemptStatus.PASSWORDS_DO_NOT_MATCH -> {
                dialogTitle = stringResource(id = R.string.passwords_do_not_match)
                dialogDescription = stringResource(id = R.string.passwords_do_not_match_desc)
            }

            RegisterAttemptStatus.WRONG_EMAIL -> {
                dialogTitle = stringResource(id = R.string.wrong_email)
                dialogDescription = stringResource(id = R.string.wrong_email_desc)
            }

            RegisterAttemptStatus.USER_EXIST -> {
                dialogTitle = stringResource(id = R.string.user_exist)
                dialogDescription = stringResource(id = R.string.user_exist_desc)
            }

            RegisterAttemptStatus.EMAIL_IN_USE -> {
                dialogTitle = stringResource(id = R.string.email_in_use)
                dialogDescription = stringResource(id = R.string.email_in_use_desc)
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
fun RegisterScreenPreview() {
    ProjectTheme {
        RegisterScreen({})
    }
}